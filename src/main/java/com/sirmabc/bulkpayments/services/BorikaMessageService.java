package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.message.MessageWrapper;
import com.sirmabc.bulkpayments.message.MessageWrapperBuilder;
import com.sirmabc.bulkpayments.util.CodesPacs002;
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

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_MESSAGE_SEQ;

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
            acknowledge(response.headers().map());
            MessageWrapper incmgMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), response);

            incmgMsgWrapper.saveMessageToDatabase();
            CodesPacs002 codesPacs002 = incmgMsgWrapper.validate();

            // TODO: Change the name generation of the .xml file
            if (codesPacs002 == CodesPacs002.OK01) {
                incmgMsgWrapper.saveMessageToXmlFile(UUID.randomUUID().toString());
            } else {
                // TODO: Decide what to do if the header wasn't validated successfully
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
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsInProgressDirPath());
            MessageWrapper outgngMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(xmlFile, Message.class), null);

            outgngMsgWrapper.buildAppHdr();
            outgngMsgWrapper.saveMessageToDatabase();

            HttpResponse<String> response = sendMessageToBorika(outgngMsgWrapper);
            boolean inProgressXmlFileDeleted = xmlFile.delete();

            MessageWrapper incmgMsgWrapper = messageWrapperBuilder.build(XMLHelper.deserializeXml(response.body(), Message.class), response);
            incmgMsgWrapper.saveMessageToDatabase();

            CodesPacs002 codesPacs002 = incmgMsgWrapper.validate();

            // TODO: Change the name generation of the .xml file
            if (codesPacs002 == CodesPacs002.OK01) {
                incmgMsgWrapper.saveMessageToXmlFile(UUID.randomUUID().toString());
            } else {
                // TODO: Decide what to do if the header wasn't validated successfully
            }
        } catch (Exception e) {
            // TODO: Decide what to do with the file if an error occurs
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

        String msgSeq = headers.get(X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
        borikaClient.postAcknowledge(msgSeq);
    }

    private HttpResponse<String> sendMessageToBorika(MessageWrapper outgngMsgWrapper) throws Exception {
        logger.info("Sending message to Borika");

        String signedRequestMessageXML = outgngMsgWrapper.getSignedMessage();
        HttpResponse<String> response = borikaClient.postMessage(signedRequestMessageXML);

        return response;
    }
}
