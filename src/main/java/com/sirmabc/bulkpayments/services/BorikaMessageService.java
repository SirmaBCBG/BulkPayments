package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.exceptions.PostMessageException;
import com.sirmabc.bulkpayments.message.MessageWrapper;
import com.sirmabc.bulkpayments.message.MessageWrapperBuilder;
import com.sirmabc.bulkpayments.persistance.entities.ParticipantsEntity;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Header;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.Util;
import montranMessage.montran.message.Message;
import montranMessage.montran.participants.ParticipantInfo;
import montranMessage.montran.participants.ParticipantsType;
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

    private ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final MessageWrapperBuilder messageWrapperBuilder;

    @Autowired
    public BorikaMessageService(BorikaClient borikaClient, ParticipantsRepository participantsRepository, Properties properties, MessageWrapperBuilder messageWrapperBuilder) {
        this.borikaClient = borikaClient;
        this.participantsRepository = participantsRepository;
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
            if (codesPacs002 == CodesPacs002.OK01) {
                // Save the message to an xml file
                incmgMsgWrapper.saveMessageToXmlFile();
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
        logger.info("Asynchronously building the outgoing message " + Thread.currentThread().getName());

        try {
            // Move the xml file to the "in progress" directory
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsInProgressPath());
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
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsProcessedPath());

            // Create a MessageWrapper object for the incoming message
            MessageWrapper incmgMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), response);

            // Save the message to the database
            // TODO: Uncomment
            // incmgMsgWrapper.saveMessageToDatabase();

            // Validate the message
            CodesPacs002 codesPacs002 = incmgMsgWrapper.validate();

            // Check if the validation was successful
            if (codesPacs002 == CodesPacs002.OK01) {
                // Save the message to an xml file
                incmgMsgWrapper.saveMessageToXmlFile();
            } else {
                logger.error("The message was not validated successfully");
            }
        } catch (PostMessageException e) {
            logger.error("Sending message to borika failed with error: " + e.getMessage(), e);

            // If the connection to Borika times out, move the xml file back to its original location
            try {
                xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsPath());
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
            // Create a MessageWrapper object for the incoming message
            MessageWrapper incmgMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), response);

            // Save the message to the database
            // TODO: Uncomment
            // incmgMsgWrapper.saveMessageToDatabase();

            // Validate the message's application header
            CodesPacs002 pacs002Code = incmgMsgWrapper.isValidAppHdr();

            if (pacs002Code == CodesPacs002.OK01) {
                // Update the participants inside the database
                updateParticipants(incmgMsgWrapper.getMessage().getParticipants());
            } else {
                logger.error("The message's application header was not validated successfully");
            }
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + "threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

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

    private void updateParticipants(ParticipantsType participantsType) {
        logger.info("Updating the participants");

        if (participantsType != null) {
            participantsRepository.archive();
            ParticipantsEntity entity = new ParticipantsEntity();

            for (ParticipantInfo info : participantsType.getParticipant()) {
                entity.setBic(info.getBic());
                entity.setpType(info.getType().toString());
                entity.setValidFrom(Util.toSQLDate(info.getValidFrom()));
                entity.setValidTo(Util.toSQLDate(info.getValidTo()));
                entity.setpStatus(info.getStatus().toString());
                entity.setpOnline(Boolean.toString(info.isOnline()));
                entity.setDirectAgent(info.getDirectAgent());

                participantsRepository.save(entity);
            }
        }
    }
}
