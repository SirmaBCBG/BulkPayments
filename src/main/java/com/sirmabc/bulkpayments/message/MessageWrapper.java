package com.sirmabc.bulkpayments.message;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Header;
import com.sirmabc.bulkpayments.util.MsgDefIdrs;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import jakarta.xml.bind.JAXBException;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader37;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader33;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.security.KeyStore;

public class MessageWrapper {

    private static final Logger logger = LoggerFactory.getLogger(MessageWrapper.class);

    private Message message;

    private HttpResponse<String> response;

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    public MessageWrapper(Message message, HttpResponse<String> response, BulkMessagesRepository bulkMessagesRepository, ParticipantsRepository participantsRepository, Properties properties, XMLSigner xmlSigner) {
        this.message = message;
        this.response = response;
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    public void buildAppHdr() {
        if (message.getAppHdr() == null) {
            logger.info("Building application header");

            BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();

            // pacs.008
            if (message.getFIToFICstmrCdtTrf() != null) {
                GroupHeader33 grpHdr = message.getFIToFICstmrCdtTrf().getGrpHdr();

                appHdr.setFr(generateParty9Choice(grpHdr.getInstgAgt().getFinInstnId().getBIC()));
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS008.idr);
                appHdr.setCreDt(grpHdr.getCreDtTm());
            } else {
                // pacs.002
                if (message.getFIToFIPmtStsRpt() != null) {
                    GroupHeader37 grpHdr = message.getFIToFIPmtStsRpt().getGrpHdr();

                    appHdr.setFr(generateParty9Choice(grpHdr.getInstgAgt().getFinInstnId().getBIC()));
                    appHdr.setMsgDefIdr(MsgDefIdrs.PACS002.idr);
                    appHdr.setCreDt(grpHdr.getCreDtTm());
                }
            }

            appHdr.setTo(generateParty9Choice(properties.getRtpChannel()));
            appHdr.setBizMsgIdr(properties.getBizMsgIdr());
            appHdr.setSgntr(new SignatureEnvelope());

            message.setAppHdr(appHdr);
        } else { logger.error("Application header already exists"); }
    }

    public CodesPacs002 validate() throws Exception {
        // TODO: Uncomment
        /*CodesPacs002 pacs002Code = isValidAppHdr();
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            logger.error("An error occurred while validating the application header. Error code: " + pacs002Code.errorCode);
            return pacs002Code;
        }*/

        // TODO: Uncomment
        /*pacs002Code = isDuplicate();
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            logger.error("The message is duplicate. Error code: " + pacs002Code.errorCode);
            return pacs002Code;
        }*/

        return CodesPacs002.OK01;
    }

    public String getSignedMessage() throws Exception {
        Document document = xmlSigner.string2XML(XMLHelper.serializeXml(message));

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(properties.getSbcKeyStorePath()), properties.getSbcKeyStorePassword().toCharArray());
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(properties.getSbcKeyPassword().toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(properties.getSbcKeyStoreAlias(), passwordProtection);

        document = xmlSigner.sign(document, keyEntry);
        String signedXml = xmlSigner.xml2String(document);

        return signedXml;
    }

    public CodesPacs002 isValidAppHdr() throws Exception {
        logger.info("Validating the application header");

        Document document = xmlSigner.string2XML(XMLHelper.serializeXml(message));
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        String senderBic = message.getAppHdr().getFr().getFIId().getFinInstnId().getBICFI();
        String receiverBic = message.getAppHdr().getTo().getFIId().getFinInstnId().getBICFI();

        int result = participantsRepository.checkParticipant(senderBic);
        String bic = properties.getRtpChannel();

        // Check if sender bic is valid and check if receiver bic is the same as the institution bic.
        if(result == 0 || !receiverBic.equals(bic)) {
            return CodesPacs002.RC01;
        }

        return CodesPacs002.OK01;
    }

    public void saveMessageToDatabase() throws JAXBException {
        logger.info("Saving message to the database");

        BulkMessagesEntity entity = buildBulkMessagesEntity(message.getAppHdr(),
                XMLHelper.serializeXml(message),
                response != null ? response.headers().map().get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0) : null);

        bulkMessagesRepository.save(entity);
    }

    public void saveMessageToXmlFile(String fileName) throws JAXBException, ParserConfigurationException, IOException, SAXException, TransformerException {
        logger.info("Saving message to an xml file");

        String xmlString = XMLHelper.serializeXml(message);

        // Parse the XML String to prevent encoding issues
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

        // Write the parsed document to a XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result =  new StreamResult(new File(getXmlFilePath() + "\\" + fileName + ".xml"));
        transformer.transform(source, result);
    }

    private CodesPacs002 isDuplicate() {
        logger.info("Checking if the message is duplicate");

        Boolean isDuplicateHeader = response != null ? response.headers().map().containsKey(Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE.header) : null;

        if (Boolean.TRUE.equals(isDuplicateHeader)) {
            BulkMessagesEntity entity = bulkMessagesRepository.findByMessageId(message.getAppHdr().getBizMsgIdr());

            if (entity != null) {
                return CodesPacs002.AM05;
            }
        }

        return CodesPacs002.OK01;
    }

    private Party9Choice generateParty9Choice(String bicfi) {
        FinancialInstitutionIdentification8 financialInstitutionIdentification8 = new FinancialInstitutionIdentification8();
        financialInstitutionIdentification8.setBICFI(bicfi);

        BranchAndFinancialInstitutionIdentification5 branchAndFinancialInstitutionIdentification5 = new BranchAndFinancialInstitutionIdentification5();
        branchAndFinancialInstitutionIdentification5.setFinInstnId(financialInstitutionIdentification8);

        Party9Choice party9Choice = new Party9Choice();
        party9Choice.setFIId(branchAndFinancialInstitutionIdentification5);

        return party9Choice;
    }

    private BulkMessagesEntity buildBulkMessagesEntity(BusinessApplicationHeaderV01 appHdr, String xmlMessage, String messageSeq) {
        BulkMessagesEntity entity = new BulkMessagesEntity();

        entity.setMessageId(appHdr.getBizMsgIdr());
        entity.setMessageXml(xmlMessage);
        entity.setMessageType(appHdr.getMsgDefIdr());
        entity.setMessageSeq(messageSeq);

        return entity;
    }

    private String getXmlFilePath() {
        String filePath = "";

        // pacs.008
        if (message.getFIToFICstmrCdtTrf() != null) filePath = properties.getIncmgBulkPacs008Path();
        else {
            // pacs.002
            if (message.getFIToFIPmtStsRpt() != null) filePath = properties.getIncmgBulkPacs002Path();
        }

        return filePath;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
