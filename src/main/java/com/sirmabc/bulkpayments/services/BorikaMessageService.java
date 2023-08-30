package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.exceptions.PostMessageException;
import com.sirmabc.bulkpayments.message.WrappedMessage;
import com.sirmabc.bulkpayments.message.WrappedMessageBuilder;
import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.enums.CodesPacs002;
import com.sirmabc.bulkpayments.util.enums.Header;
import com.sirmabc.bulkpayments.util.enums.InOut;
import com.sirmabc.bulkpayments.util.enums.ReqSts;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import jakarta.xml.bind.JAXBException;
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

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final WrappedMessageBuilder wrappedMessageBuilder;

    @Autowired
    ParticipantsService participantsService;
    @Autowired
    XMLSigner xmlSigner;

    @Autowired
    public BorikaMessageService(BorikaClient borikaClient, BulkMessagesRepository bulkMessagesRepository, ParticipantsRepository participantsRepository, Properties properties, WrappedMessageBuilder wrappedMessageBuilder) {
        this.borikaClient = borikaClient;
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.wrappedMessageBuilder = wrappedMessageBuilder;
    }

    @Async
    public void asyncProcessIncomingMessage(HttpResponse<String> response) throws AppException {
        logger.info("Asynchronously processing the incoming message " + Thread.currentThread().getName());

        try {
            logger.debug("Incoming response headers: " + response.headers().toString());

            // Get the headers from the response
            Map<String, List<String>> headers = response.headers().map();

            // Don't proceed with the method if there is no message present
            if (headers.get(Header.X_MONTRAN_RTP_REQSTS.header) != null && headers.get(Header.X_MONTRAN_RTP_REQSTS.header).get(0).equalsIgnoreCase(ReqSts.EMPTY.sts)) return;

            // Create a MessageWrapper object for the incoming message
            WrappedMessage incmgMsg = wrappedMessageBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), InOut.IN, response);

            // Save the message to the database
            BulkMessagesEntity entity = incmgMsg.saveToDatabase(response.body());

            // Validate the message
            //TODO: Check validation with Tceci about incoming bic
            CodesPacs002 codesPacs002;
            if(incmgMsg.getMessage().getAppHdr().getMsgDefIdr().equals("pacs.002.001.03")){
                codesPacs002 = incmgMsg.isValidAppHdrParticipants();
            }else {
                codesPacs002 = incmgMsg.validate();
            }

            // Check if the validation was successful
            if (codesPacs002 == CodesPacs002.OK01) {
                // Save the message to an xml file
                incmgMsg.saveToXmlFile();

                // Acknowledge the message
                acknowledge(headers);

                // Update the database entity after acknowledging the message
                updateAcknowledged(entity, String.valueOf(true));
            } else {
                logger.error("The message was not validated successfully");
            }
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + " threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    @Async
    public void asyncProcessOutgoingMessage(File xmlFile) throws AppException {
        logger.info("Asynchronously building the outgoing message: " + Thread.currentThread().getName());

        try {
            logger.info("Processing outgoing file:" + xmlFile.getAbsolutePath());

            // Move the xml file to the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsInProgressPath());

            // Create a MessageWrapper object for the outgoing message
            WrappedMessage outgngMsg = wrappedMessageBuilder.build(XMLHelper.deserializeXml(xmlFile, Message.class), InOut.OUT, null);

            // Build application header for the message
            outgngMsg.buildAppHdr();

            //Signing message;
            String signedMessage = xmlSigner.sign(outgngMsg.getMessage());

            // Save the message to the database
            BulkMessagesEntity entity = outgngMsg.saveToDatabase(signedMessage);

            // Send the message to Borika and get the response
            HttpResponse<String> response = sendToBorika(signedMessage);

            // Get the headers from the response
            Map<String, List<String>> headers = response.headers().map();

            // Get the request status from the header
            String requestStatus = headers.get(Header.X_MONTRAN_RTP_REQSTS.header).get(0);

            // Update the database entity with the received request status
            updateRequestStatus(entity, requestStatus);

            // Delete the xml file from the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsProcessedPath());

            logger.debug("Request status: " + requestStatus);
            // TODO: Decide what to do after checking the request status
        } catch (PostMessageException e) {
            logger.error("Sending message to borika failed with error: " + e.getMessage(), e);

            // If the connection to Borika times out, move the xml file back to its original location
            try {
                FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsPath());
            } catch (IOException ex) {
                logger.error("Moving xml file back to the original location failed with error: " + ex.getMessage(), ex);
            }

            throw new AppException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + " threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    @Async()
    public void asyncProcessParticipantsMessage(HttpResponse<String> response) throws AppException {
        logger.info("asyncStartProcessingParticipantsMessage..." + Thread.currentThread().getName());

        try {
            logger.debug("Incoming response headers: " + response.headers().toString());

            // Create a MessageWrapper object for the incoming message
            WrappedMessage incmgMsg = wrappedMessageBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), InOut.IN, response);

            // Validate the message's application header
            CodesPacs002 pacs002Code = incmgMsg.isValidAppHdrParticipants();

            logger.info("Validation returned code: " + pacs002Code);

            if (pacs002Code == CodesPacs002.OK01) {
                // Update the participants inside the database
                participantsService.updateParticipants(incmgMsg.getMessage().getParticipants());
            } else {
                logger.error("The message's application header was not validated successfully");
            }
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + "threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    public HttpResponse<String> sendToBorika(String signedMessage) throws JAXBException, PostMessageException {
        logger.info("Sending message to Borika");

        logger.debug("Message after building application header: " + signedMessage);
        HttpResponse<String> response = borikaClient.postMessage(signedMessage);

        return response;
    }

    private void acknowledge(Map<String, List<String>> headers) throws IOException, InterruptedException {
        logger.info("Acknowledging the message");

        String msgSeq = headers.get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
        borikaClient.postAcknowledge(msgSeq);
    }

    private void updateRequestStatus(BulkMessagesEntity entity, String reqSts) {
        logger.info("Updating the database entity's request status");

        entity.setReqSts(reqSts);
        bulkMessagesRepository.save(entity);
    }

    private void updateAcknowledged(BulkMessagesEntity entity, String acknowledged) {
        logger.info("Updating the database entity's acknowledged field");

        entity.setAcknowledged(acknowledged);
        bulkMessagesRepository.save(entity);
    }
}
