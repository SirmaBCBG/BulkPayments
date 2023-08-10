package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.exceptions.PostMessageException;
import com.sirmabc.bulkpayments.message.MessageWrapper;
import com.sirmabc.bulkpayments.message.MessageWrapperBuilder;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Header;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
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
import java.util.UUID;

@Service
public class BorikaMessageService {

    private static final Logger logger = LoggerFactory.getLogger(BorikaMessageService.class);

    private final BorikaClient borikaClient;

    private final Properties properties;

    private final MessageWrapperBuilder messageWrapperBuilder;

    @Autowired
    public BorikaMessageService(Properties properties, BorikaClient borikaClient, MessageWrapperBuilder messageWrapperBuilder) {
        this.borikaClient = borikaClient;
        this.properties = properties;
        this.messageWrapperBuilder = messageWrapperBuilder;
    }

    @Async
    public void asyncProcessIncomingMessage(HttpResponse<String> response) throws AppException {
        logger.info("Asynchronously processing the incoming message " + Thread.currentThread().getName());

        try {
            // Acknowledge the headers
            // TODO: Uncomment
            // acknowledge(response.headers().map());

            // Create a MessageWrapper object for the incoming message
            MessageWrapper incmgMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), response);

            // Save the message to the database
            // TODO: Uncomment
            // incmgMsgWrapper.saveMessageToDatabase();

            // Validate the message
            CodesPacs002 codesPacs002 = incmgMsgWrapper.validate();

            // Check if the validation was successful
            // TODO: Change the name generation of the .xml file
            if (codesPacs002 == CodesPacs002.OK01) {
                // Save the message to an xml file
                incmgMsgWrapper.saveMessageToXmlFile(UUID.randomUUID().toString());
            } else {
                // TODO: Decide what to do if the validation wasn't successful
            }
        } catch (Exception e) {
            // TODO: Decide what to do if an error occurs
            logger.error(Thread.currentThread().getName() + " threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    @Async
    public void asyncProcessOutgoingMessage(File xmlFile, String parentDirPath) throws AppException {
        logger.info("Asynchronously building the outgoing message " + Thread.currentThread().getName());

        try {
            // Move the xml file to the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkInProgressPath());
            // Create a MessageWrapper object for the outgoing message
            MessageWrapper outgngMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(xmlFile, Message.class), null);

            // Build application header for the message
            outgngMsgWrapper.buildAppHdr();

            // Save the message to the database
            // TODO: Uncomment
            // outgngMsgWrapper.saveMessageToDatabase();

            // Send the message to Borika and get the response
            HttpResponse<String> response = sendMessageToBorika(outgngMsgWrapper);

            // Delete the xml file from the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkProcessedPath());

            // Create a MessageWrapper object for the incoming message
            MessageWrapper incmgMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), response);

            // Save the message to the database
            // TODO: Uncomment
            // incmgMsgWrapper.saveMessageToDatabase();

            // Validate the message
            CodesPacs002 codesPacs002 = incmgMsgWrapper.validate();

            // Check if the validation was successful
            // TODO: Change the name generation of the .xml file
            if (codesPacs002 == CodesPacs002.OK01) {
                // Save the message to an xml file
                incmgMsgWrapper.saveMessageToXmlFile(UUID.randomUUID().toString());
            } else {
                // TODO: Decide what to do if the validation wasn't successful
            }
        } catch (PostMessageException e) {
            logger.error("Sending message to borika failed with error: " + e.getMessage(), e);

            try {
                xmlFile = FileHelper.moveFile(xmlFile, parentDirPath);
            } catch (IOException ex) {
                logger.error("Moving xml file back to the original location failed with error: " + ex.getMessage(), ex);
            }

            throw new AppException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + " threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    /*@Async()
    public void asyncStartProcessingParticipantsMessage(HttpResponse<String> response) throws AppException {
        logger.info("asyncStartProcessingParticipantsMessage..." + Thread.currentThread().getName());

        try {
            Map<String, List<String>> headers = response.headers().map();
            Message message = XMLFileHelper.deserializeXml(response.body(), Message.class);
            DefinedMessage definedMessage = definedMessageService.define(headers, message, response.body());

            definedMessage.saveMessage();
            CodesPacs002 pacs002Code = definedMessage.isValidAppHdr();

            definedMessage.processMessage(pacs002Code);
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + "threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }*/

    private void acknowledge(Map<String, List<String>> headers) throws IOException, InterruptedException {
        logger.info("Acknowledging headers");

        String msgSeq = headers.get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
        borikaClient.postAcknowledge(msgSeq);
    }

    private HttpResponse<String> sendMessageToBorika(MessageWrapper outgngMsgWrapper) throws Exception {
        logger.info("Sending message to Borika");

        String signedRequestMessageXML = outgngMsgWrapper.getSignedMessage();
        logger.debug("Message after building application header: " + signedRequestMessageXML);
        HttpResponse<String> response = borikaClient.postMessage(signedRequestMessageXML);

        return response;
    }
}
