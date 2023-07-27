package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.messages.IncmgBulkMsg;
import com.sirmabc.bulkpayments.messages.OutgngBulkMsg;
import com.sirmabc.bulkpayments.messages.messageBuilder.IncmgBulkMsgBuilder;
import com.sirmabc.bulkpayments.messages.messageBuilder.OutgngBulkMsgBuilder;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
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
import java.util.UUID;

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_MESSAGE_SEQ;

@Service
public class BorikaMessageService {

    private static final Logger logger = LoggerFactory.getLogger(BorikaMessageService.class);

    private final BorikaClient borikaClient;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    private final IncmgBulkMsgBuilder incmgBulkMsgBuilder;

    private final OutgngBulkMsgBuilder outgngBulkMsgBuilder;

    @Autowired
    public BorikaMessageService(Properties properties,
                                BorikaClient borikaClient,
                                XMLSigner xmlSigner,
                                IncmgBulkMsgBuilder incmgBulkMsgBuilder,
                                OutgngBulkMsgBuilder outgngBulkMsgBuilder) {
        this.borikaClient = borikaClient;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
        this.incmgBulkMsgBuilder = incmgBulkMsgBuilder;
        this.outgngBulkMsgBuilder = outgngBulkMsgBuilder;

    }

    @Async
    public void asyncProcessIncomingMessage(HttpResponse<String> response) throws AppException {
        logger.info("Asynchronously processing the incoming message " + Thread.currentThread().getName());

        try {
            acknowledge(response.headers().map());
            IncmgBulkMsg incmgBulkMsg = incmgBulkMsgBuilder.build(FileHelper.deserializeXml(response.body(), Message.class), response);

            // TODO: Save message to the database
            incmgBulkMsg.saveMessage();
            CodesPacs002 codesPacs002 = incmgBulkMsg.validate();

            // TODO: Change the name generation of the .xml file
            if (codesPacs002 == CodesPacs002.OK01) {
                // TODO: Move the objectToXmlFile method to the bulk message classes
                FileHelper.objectToXmlFile(incmgBulkMsg.getMessage(), properties.getIncmgBulkMsgsDirPath() + "\\" + UUID.randomUUID() + ".xml");
            }
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + " threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    @Async
    public void asyncProcessOutgoingMessage(File xmlFile, String parentDirPath) throws AppException {
        logger.info("Asynchronously building the outgoing message " + Thread.currentThread().getName());

        try {
            xmlFile = FileHelper.moveFile(xmlFile, properties.getOutgngBulkMsgsInProgressDirPath());
            OutgngBulkMsg outgngBulkMsg = outgngBulkMsgBuilder.build(FileHelper.deserializeXml(xmlFile, Message.class));

            outgngBulkMsg.buildAppHdr();
            // TODO: Save message to the database
            HttpResponse<String> response = sendMessageToBorika(outgngBulkMsg);

            boolean inProgressXmlFileDeleted = xmlFile.delete();

            IncmgBulkMsg incmgBulkMsg = incmgBulkMsgBuilder.build(FileHelper.deserializeXml(response.body(), Message.class), response);
            // TODO: Save response message to the database
            CodesPacs002 codesPacs002 = incmgBulkMsg.validate();

            if (codesPacs002 == CodesPacs002.OK01) {
                // TODO: Save the pacs.002 response
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

    private HttpResponse<String> sendMessageToBorika(OutgngBulkMsg outgngBulkMsg) throws Exception {
        logger.info("Sending message to Borika");
        String signedRequestMessageXML = outgngBulkMsg.signMessage();

        return borikaClient.postMessage(signedRequestMessageXML);
    }
}
