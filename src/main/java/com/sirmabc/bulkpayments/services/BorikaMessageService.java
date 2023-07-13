package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.messages.DefinedMessage;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.XMLFileHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.montran.message.Message;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

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

    DefinedMessageService definedMessageService;

    BorikaClient borikaClient;

    //FCService fcService;

    XMLSigner xmlSigner;
    //CreditTransferMessageBuilder creditTransferMessageBuilder;

    @Autowired
    public BorikaMessageService(DefinedMessageService definedMessageService,
                                BorikaClient borikaClient,
                                /*FCService fcService,*/ XMLSigner xmlSigner
                                /*CreditTransferMessageBuilder creditTransferMessageBuilder*/) {
        this.borikaClient = borikaClient;
        //this.fcService = fcService;
        this.xmlSigner = xmlSigner;
        this.definedMessageService = definedMessageService;
        //this.creditTransferMessageBuilder = creditTransferMessageBuilder;
    }

    @Async
    public void asyncStartProcessingMessage(HttpResponse<String> response) throws AppException {
        logger.info("Processing message asynchronously..." + Thread.currentThread().getName());

        try {
            Map<String, List<String>> headers = response.headers().map();
            acknowledge(headers);

            Message message = XMLFileHelper.deserializeXml(response.body(), Message.class);
            DefinedMessage definedMessage = definedMessageService.define(headers, message, response.body());

            definedMessage.saveMessage();
            CodesPacs002 codesPacs002 = definedMessage.validate();

            definedMessage.processMessage(codesPacs002, "C:\\Users\\veselin.zinkov\\OneDrive - Sirma Business Consulting\\Desktop");
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + "threw an error: " + e.getMessage(), e);

            throw new AppException(e.getMessage(), e);
        }
    }

    /*public FIToFIPaymentStatusReportMessage answerToBorika(Message answerMessage) throws Exception {
        logger.info("prepareMessage()");

        String requestMessageXml = XMLFileHelper.serializeXml(answerMessage);
        String signedRequestMessageXML = signMessage(requestMessageXml);

        HttpResponse<String> response = borikaClient.postMessage(signedRequestMessageXML);

        //Deserialize response body to java object and get request headers.
        Map<String, List<String>> headers = response.headers().map();
        Message responseMessage = XMLFileHelper.deserializeXml(response.body(), Message.class);

        FIToFIPaymentStatusReportMessage responseDefinedMessage = (FIToFIPaymentStatusReportMessage) definedMessageService.define(headers, responseMessage, response.body());

        return responseDefinedMessage;
    }

    public String signMessage(String xml) throws Exception {

        Document document = xmlSigner.string2XML(xml);

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(keyStorePath), keyStorePassword.toCharArray());
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(keyStoreAlias, passwordProtection);

        document = xmlSigner.sign(document, keyEntry);
        String signedXml = xmlSigner.xml2String(document);

        return signedXml;
    }*/

    public void acknowledge(Map<String, List<String>> headers) throws IOException, InterruptedException {
        logger.info("Acknowledging headers...");

        String msgSeq = headers.get(X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0);
        borikaClient.postAcknowledge(msgSeq);
    }

}
