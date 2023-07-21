package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Header;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.BulkMessageHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${keystore.sign.path}")
    private String keyStorePath;

    @Value("${keystore.sign.password}")
    private String keyStorePassword;
    @Value("${keystore.sign.alias}")
    private String keyStoreAlias;

    private final BorikaClient borikaClient;

    private final Properties properties;

    private XMLSigner xmlSigner;

    @Autowired
    public BorikaMessageService(Properties properties, BorikaClient borikaClient, XMLSigner xmlSigner) {
        this.borikaClient = borikaClient;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    @Async
    public void asyncStartProcessingMessage(HttpResponse<String> response) throws AppException {
        logger.info("Processing message asynchronously..." + Thread.currentThread().getName());

        try {
            Map<String, List<String>> headers = response.headers().map();
            acknowledge(headers);

            Message message = XMLHelper.deserializeXml(response.body(), Message.class);

            DatabaseService.saveBulkMessage(message.getAppHdr(), response.body(), headers.get(Header.X_MONTRAN_RTP_MESSAGE_SEQ).get(0));
            CodesPacs002 codesPacs002 = BulkMessageHelper.validate(message.getAppHdr(), headers, response.body());

            // TODO: Change the name generation of the .xml file
            if (codesPacs002 == CodesPacs002.OK01) {
                XMLHelper.objectToXmlFile(message, properties.getBulkMsgsDirPath() + "\\" + UUID.randomUUID() + ".xml");
            }
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + "threw an error: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

    @Async
    public void asyncStartBuildingMessage(File xmlFile) throws AppException {
        logger.info("Building message asynchronously..." + Thread.currentThread().getName());

        try {
            // TODO: Add PropertiesEntity for the file path
            XMLHelper.moveFile(xmlFile, "C:\\Users\\veselin.zinkov\\OneDrive - Sirma Business Consulting\\Desktop\\xml_files\\in_progress");
            Message message = XMLHelper.deserializeXml(xmlFile, Message.class);

            // TODO: Check if everything in the buildAppHdr method is correct
            message.setAppHdr(BulkMessageHelper.buildAppHdr(message));

            // TODO: Send the message to Borika
        } catch (Exception e) {
            // TODO: Decide what to do if an error occurs
            logger.error(Thread.currentThread().getName() + "threw an error: " + e.getMessage(), e);
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
        logger.info("Acknowledging headers...");

        String msgSeq = headers.get(X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
        borikaClient.postAcknowledge(msgSeq);
    }
}
