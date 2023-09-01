package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.exceptions.PostMessageException;
import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.enums.CodesPacs002;
import com.sirmabc.bulkpayments.util.enums.Header;
import com.sirmabc.bulkpayments.util.enums.InOut;
import com.sirmabc.bulkpayments.util.enums.ReqSts;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class BorikaMessageService {

    private static final Logger logger = LoggerFactory.getLogger(BorikaMessageService.class);

    private final BorikaClient borikaClient;

    private final DatabaseService databaseService;

    private final MessageService messageService;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    @Autowired
    public BorikaMessageService(BorikaClient borikaClient, DatabaseService databaseService, MessageService messageService, Properties properties, XMLSigner xmlSigner) {
        this.borikaClient = borikaClient;
        this.databaseService = databaseService;
        this.messageService = messageService;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    @Async
    public void asyncProcessIncomingMessage(HttpResponse<String> response) throws AppException {
        logger.info("asyncProcessIncomingMessage()...");

        String error = null;
        BulkMessagesEntity messageEntity = null;

        try {
            if (response.headers() != null) {
                logger.debug("asyncProcessIncomingMessage(): Response headers: " + response.headers().toString());
            }

            String originalMessage = response.body();

            // Get the headers from the response
            Map<String, List<String>> headersMap = response.headers().map();

            // Don't proceed with the method if there is no message present
            if (headersMap.get(Header.X_MONTRAN_RTP_REQSTS.header) != null && headersMap.get(Header.X_MONTRAN_RTP_REQSTS.header).get(0).equalsIgnoreCase(ReqSts.EMPTY.sts)) {
                logger.info("asyncProcessIncomingMessage(): No message to process");
                return;
            }

            // Get the message sequence header
            String messageSequence = null;
            List<String> messageSequenceHeaders = headersMap.get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header);

            if (messageSequenceHeaders != null) {
                messageSequence = headersMap.get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
            }

            // Build the initial bulk message entity
            messageEntity = databaseService.buildBulkMessageEntity(
                    null,
                    null,
                    null,
                    messageSequence,
                    null,
                    null,
                    InOut.IN.value,
                    null,
                    error,
                    originalMessage
            );

            // todo: should discuss if this is OK?!
            if (messageSequence == null) {
                throw new Exception("No " + Header.X_MONTRAN_RTP_MESSAGE_SEQ.header + " header!");
            }

            // Create a message object for the incoming message
            Message message = XMLHelper.deserializeXml(originalMessage);

            messageEntity.setMessageId(message.getAppHdr().getBizMsgIdr());
            messageEntity.setMessageType(message.getAppHdr().getMsgDefIdr());

            logger.info("asyncProcessIncomingMessage(): Processing message " + messageEntity.getMessageId());

            // Save the message to the database
            //messageEntity = databaseService.saveBulkMessageEntity(messageEntity);

            // Validate the message
            //CodesPacs002 codesPacs002 = messageService.validateMessage(message, response);

            // Check if the validation was successful
            //if (codesPacs002 == CodesPacs002.OK01) {
                // Generate a unique file name
                String fileName = FileHelper.generateUniqueFileName(InOut.IN, message.getAppHdr().getMsgDefIdr(), messageEntity.getMessageId());

                // Save the message to an xml file
                messageService.saveMessageToXmlFile(message, fileName);

                // Updating the entity
                messageEntity.setFileName(fileName);

            /*} else {
                logger.error("The message was not validated successfully. Code: " + codesPacs002.errorCode);
                error = "The message was not validated successfully. Code: " + codesPacs002.errorCode;
            }*/

            // Acknowledge the message
            HttpResponse<String> ackResponse = acknowledge(headersMap, messageEntity.getMessageId());

            // Updating the entity
            messageEntity.setAcknowledged(ackResponse.body());
        } catch (Exception e) {
            logger.error("asyncProcessIncomingMessage(): Exception: " + e.getMessage(), e);

            if (e.getMessage() == null) {
                Throwable t = e.getCause();

                if (t != null) {
                    error = t.getMessage();
                }
            } else {
                error = e.getMessage();
            }

            throw new AppException(e.getMessage(), e);
        } finally {
            if (messageEntity != null) {
                try {
                    messageEntity.setError(error);
                    databaseService.saveBulkMessageEntity(messageEntity);
                } catch (Exception e) {
                    logger.error("asyncProcessIncomingMessage(): Finally block exception: " + e.getMessage(), e);
                }
            }
        }
    }

    @Async
    public void asyncProcessOutgoingMessage(File xmlFile) throws AppException {
        logger.info("asyncProcessOutgoingMessage()...");

        String error = null;
        BulkMessagesEntity messageEntity = null;

        try {
            logger.info("asyncProcessOutgoingMessage(): Processing file:" + xmlFile.getName());

            // Move the xml file to the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsInProgressPath());

            // Create a message object for the outgoing message
            Message message = XMLHelper.deserializeXml(xmlFile);

            // Get all content from the xml file as a string
            String originalMessage = FileHelper.readFile(xmlFile);
            logger.info("asyncProcessOutgoingMessage(): Original message from file: " + originalMessage);

            // Build the initial bulk message entity
            messageEntity = databaseService.buildBulkMessageEntity(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    InOut.OUT.value,
                    xmlFile.getName(),
                    null,
                    originalMessage
            );

            // Save the message to the database
            messageEntity = databaseService.saveBulkMessageEntity(messageEntity);

            // Build application header for the message
            message.setAppHdr(messageService.buildMessageAppHdr(message));

            // Signing the message
            String signedMessage = xmlSigner.sign(message);

            // Updating the entity
            messageEntity.setMessageId(message.getAppHdr().getBizMsgIdr());
            messageEntity.setMessageXml(signedMessage);
            messageEntity.setMessageType(message.getAppHdr().getMsgDefIdr());

            // Send the message to Borika and get the response
            HttpResponse<String> response = sendToBorika(signedMessage, messageEntity.getMessageId());

            // Updating the entity
            messageEntity.setReqSts(response.headers().map().get(Header.X_MONTRAN_RTP_REQSTS.header).get(0));

            // Delete the xml file from the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsProcessedPath());

            // TODO: Decide what to do after checking the request status
        } catch (PostMessageException e) {
            logger.error("asyncProcessOutgoingMessage(): PostMessageException: " + e.getMessage(), e);
            error = e.getMessage();

            // If the connection to Borika times out, move the xml file back to its original location
            try {
                FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsPath());
            } catch (IOException ex) {
                logger.error("Error while moving xml file named " + xmlFile.getName() + " back to the original location: " + ex.getMessage(), ex);
            }

            throw new AppException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("asyncProcessOutgoingMessage(): Exception: " + e.getMessage(), e);
            error = e.getMessage();

            // If there are any errors inside the file, move it to the error folder
            try {
                FileHelper.moveFile(xmlFile, properties.getErrorBulkMsgsPath());
            } catch (IOException ex) {
                logger.error("Error while moving xml file named " + xmlFile.getName() + " to the error directory: " + ex.getMessage(), ex);
            }

            throw new AppException(e.getMessage(), e);
        } finally {
            if (messageEntity != null) {
                try {
                    messageEntity.setError(error);
                    databaseService.saveBulkMessageEntity(messageEntity);
                } catch (Exception e) {
                    logger.error("asyncProcessOutgoingMessage(): Finally block exception: " + e.getMessage(), e);
                }
            }
        }
    }

    @Async()
    public void asyncProcessParticipantsMessage(HttpResponse<String> response) throws AppException {
        logger.info("asyncProcessParticipantsMessage()...");

        try {
            if (response.headers() != null) {
                logger.debug("asyncProcessParticipantsMessage(): Response headers: " + response.headers().toString());
            }

            // Create a message object for the incoming message
            Message message = XMLHelper.deserializeXml(response.body());

            // Validate the message's application header
            CodesPacs002 pacs002Code = messageService.validateMessageAppHdr(message, response.body());

            logger.info("Validation returned code: " + pacs002Code);

            if (pacs002Code == CodesPacs002.OK01) {
                // Update the participants inside the database
                databaseService.updateParticipants(message.getParticipants());
            } else {
                logger.error("asyncProcessParticipantsMessage(): The message's application header was not validated successfully. Code: " + pacs002Code.errorCode);
            }
        } catch (Exception e) {
            logger.error("asyncProcessParticipantsMessage(): Exception: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    public HttpResponse<String> sendToBorika(String signedMessage, String messageId) throws PostMessageException {
        logger.info("sendToBorika(): Sending message " + messageId);

        logger.debug("Message after building application header: " + signedMessage);
        HttpResponse<String> response = borikaClient.postMessage(signedMessage);

        return response;
    }

    private HttpResponse<String> acknowledge(Map<String, List<String>> headersMap, String messageId) throws IOException, InterruptedException {
        logger.info("acknowledge(): Acknowledging message " + messageId);

        String msgSeq = headersMap.get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
        HttpResponse<String> response = borikaClient.postAcknowledge(msgSeq);

        return response;
    }
}
