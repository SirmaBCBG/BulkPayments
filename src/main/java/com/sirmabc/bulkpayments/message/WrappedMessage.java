package com.sirmabc.bulkpayments.message;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.exceptions.PostMessageException;
import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.enums.CodesPacs002;
import com.sirmabc.bulkpayments.util.enums.Header;
import com.sirmabc.bulkpayments.util.enums.InOut;
import com.sirmabc.bulkpayments.util.enums.MsgDefIdrs;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import jakarta.xml.bind.JAXBException;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WrappedMessage {

    private static final Logger logger = LoggerFactory.getLogger(WrappedMessage.class);

    private Message message;

    private final InOut inOut;

    private final HttpResponse<String> response;

    private final String messageId;

    private static LocalDateTime prevSavedMsgDateTime;

    private final BorikaClient borikaClient;

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    public WrappedMessage(Message message, InOut inOut, HttpResponse<String> response,
                          BorikaClient borikaClient,
                          BulkMessagesRepository bulkMessagesRepository,
                          ParticipantsRepository participantsRepository,
                          Properties properties,
                          XMLSigner xmlSigner) {
        this.message = message;
        this.inOut = inOut;
        this.response = response;
        this.borikaClient = borikaClient;
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
        messageId = getMessageId();
    }

    static {
        prevSavedMsgDateTime = LocalDateTime.now();
    }

    public void buildAppHdr() throws Exception {
        if (message.getAppHdr() == null) {
            logger.info("Building application header");

            BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();

            // pacs.008
            if (message.getFIToFICstmrCdtTrf() != null) {
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS008.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFICstmrCdtTrf().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getPmtRtr() != null) {
                // pacs.004
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS004.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getPmtRtr().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getFIToFIPmtCxlReq() != null) {
                // camt.056
                appHdr.setMsgDefIdr(MsgDefIdrs.CAMT056.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFIPmtCxlReq().getAssgnmt().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getRsltnOfInvstgtn() != null) {
                // camt.029.001.03
                appHdr.setMsgDefIdr(MsgDefIdrs.CAMT029_03.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getRsltnOfInvstgtn().getAssgnmt().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getFIToFIPmtStsReq() != null) {
                // pacs.028
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS028.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFIPmtStsReq().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getFIToFIPmtStsRpt() != null) {
                // pacs.002
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS002.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFIPmtStsRpt().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getCdtrPmtActvtnReq() != null) {
                // pain.013
                appHdr.setMsgDefIdr(MsgDefIdrs.PAIN013.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getCdtrPmtActvtnReq().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getCdtrPmtActvtnReqStsRpt() != null) {
                // pain.014
                appHdr.setMsgDefIdr(MsgDefIdrs.PAIN014.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getCdtrPmtActvtnReqStsRpt().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getBkToCstmrStmt() != null) {
                // camt.053
                appHdr.setMsgDefIdr(MsgDefIdrs.CAMT053.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getBkToCstmrStmt().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            }

            appHdr.setFr(generateParty9Choice(properties.getRtpChannel()));
            appHdr.setTo(generateParty9Choice(properties.getBorikaBic()));
            appHdr.setBizMsgIdr(properties.getBizMsgIdr());
            appHdr.setSgntr(new SignatureEnvelope());

            message.setAppHdr(appHdr);
            signMessage();
        } else {
            logger.error("Application header already exists");
        }
    }

    public CodesPacs002 validate() throws Exception {
        CodesPacs002 pacs002Code = isValidAppHdr();
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            logger.error("An error occurred while validating the application header. Error code: " + pacs002Code.errorCode);
            return pacs002Code;
        }

        pacs002Code = isDuplicate();
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            logger.error("The message is duplicate. Error code: " + pacs002Code.errorCode);
            return pacs002Code;
        }

        return CodesPacs002.OK01;
    }

    public CodesPacs002 isValidAppHdr() throws Exception {
        logger.info("Validating the application header");

        Document document = xmlSigner.string2XML(response.body());
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        String senderBic = message.getAppHdr().getFr().getFIId().getFinInstnId().getBICFI();
        String receiverBic = message.getAppHdr().getTo().getFIId().getFinInstnId().getBICFI();

        int result = participantsRepository.checkParticipant(senderBic);
        String bic = properties.getRtpChannel();

        // Check if sender bic is valid and check if receiver bic is the same as the institution bic.
        if (result == 0 || !receiverBic.equals(bic)) {
            return CodesPacs002.RC01;
        }

        return CodesPacs002.OK01;
    }

    public CodesPacs002 isValidAppHdrParticipants() throws Exception {
        logger.info("Validating the application header");

        Document document = xmlSigner.string2XML(response.body());
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        String senderBic = message.getAppHdr().getFr().getFIId().getFinInstnId().getBICFI();
        String receiverBic = message.getAppHdr().getTo().getFIId().getFinInstnId().getBICFI();

        String instBic = properties.getRtpChannel();
        String boricaBic = properties.getBorikaBic();

        // Check if sender bic is valid and check if receiver bic is the same as the institution bic.
        if (!boricaBic.equals(senderBic) || !receiverBic.equals(instBic)) {
            return CodesPacs002.RC01;
        }

        return CodesPacs002.OK01;
    }

    public BulkMessagesEntity saveToDatabase() throws JAXBException {
        logger.info("Saving message to the database");

        BulkMessagesEntity entity = buildBulkMessagesEntity();
        return bulkMessagesRepository.save(entity);
    }

    public void saveToXmlFile() throws JAXBException, ParserConfigurationException, IOException, SAXException, TransformerException {
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

        String xmlFilePath = properties.getIncmgBulkMsgsPath() + "\\" + generateUniqueFileName(message.getAppHdr().getMsgDefIdr()) + ".xml";

        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.transform(source, result);
    }

    public HttpResponse<String> sendToBorika() throws JAXBException, PostMessageException {
        logger.info("Sending message to Borika");

        String signedRequestMessageXML = XMLHelper.serializeXml(message);
        logger.debug("Message after building application header: " + signedRequestMessageXML);
        HttpResponse<String> response = borikaClient.postMessage(signedRequestMessageXML);

        return response;
    }

    private CodesPacs002 isDuplicate() {
        logger.info("Checking if the message is duplicate");

        Boolean isDuplicateHeader = null;
        if (response != null) {
            isDuplicateHeader = response.headers().map().containsKey(Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE.header);
        }

        if (Boolean.TRUE.equals(isDuplicateHeader)) {
            BulkMessagesEntity entity = bulkMessagesRepository.findByMessageId(messageId);

            if (entity != null) {
                return CodesPacs002.AM05;
            }
        }

        return CodesPacs002.OK01;
    }

    private synchronized static String generateUniqueFileName(String msgDefIdr) {
        String shortMessageType = msgDefIdr.substring(msgDefIdr.indexOf('.') + 1, msgDefIdr.indexOf('.', msgDefIdr.indexOf('.') + 1));
        LocalDateTime currentMsgDateTime = LocalDateTime.now();

        while (currentMsgDateTime.equals(prevSavedMsgDateTime)) currentMsgDateTime = LocalDateTime.now();
        prevSavedMsgDateTime = currentMsgDateTime;

        return "IN_"
                + shortMessageType
                + "_"
                + currentMsgDateTime.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))
                + "_"
                + currentMsgDateTime.getNano();
    }

    private void signMessage() throws Exception {
        Document document = xmlSigner.string2XML(XMLHelper.serializeXml(message));

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(properties.getSignSBCKeyStorePath()), properties.getSignSBCKeyStorePassword().toCharArray());
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(properties.getSignSBCKeyStorePassword().toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(properties.getSignSBCKeyStoreAlias(), passwordProtection);

        document = xmlSigner.sign(document, keyEntry);
        String signedXml = xmlSigner.xml2String(document);

        message = XMLHelper.deserializeXml(signedXml, Message.class);
    }

    private XMLGregorianCalendar xmlGregorianCalendarToUTC(XMLGregorianCalendar calendar) throws DatatypeConfigurationException {
        int minuteOffset = calendar.getTimezone();
        if (minuteOffset == DatatypeConstants.FIELD_UNDEFINED || minuteOffset == 0) return calendar;

        calendar.add(DatatypeFactory.newInstance().newDurationDayTime(-minuteOffset * 60000L));
        calendar.setTimezone(0);

        return calendar;
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

    private BulkMessagesEntity buildBulkMessagesEntity() throws JAXBException {
        BulkMessagesEntity entity = new BulkMessagesEntity();

        entity.setMessageId(messageId);
        entity.setMessageXml(XMLHelper.serializeXml(message));
        entity.setMessageType(message.getAppHdr().getMsgDefIdr());

        if (response != null && response.headers().map().get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header) != null) {
            entity.setMessageSeq(response.headers().map().get(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header).get(0));
        } else {
            entity.setMessageSeq(null);
        }

        entity.setInOut(inOut.value);

        return entity;
    }

    private String getMessageId() {
        // pacs.008
        if (message.getFIToFICstmrCdtTrf() != null) {
            return message.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId();
        } else if (message.getPmtRtr() != null) {
            // pacs.004
            return message.getPmtRtr().getGrpHdr().getMsgId();
        } else if (message.getFIToFIPmtCxlReq() != null) {
            // camt.056
            return message.getFIToFIPmtCxlReq().getAssgnmt().getId();
        } else if (message.getRsltnOfInvstgtn() != null) {
            // camt.029.001.03
            return message.getRsltnOfInvstgtn().getAssgnmt().getId();
        } else if (message.getFIToFIPmtStsReq() != null) {
            // pacs.028
            return message.getFIToFIPmtStsReq().getGrpHdr().getMsgId();
        } else if (message.getFIToFIPmtStsRpt() != null) {
            // pacs.002
            return message.getFIToFIPmtStsRpt().getGrpHdr().getMsgId();
        } else if (message.getCdtrPmtActvtnReq() != null) {
            // pain.013
            return message.getCdtrPmtActvtnReq().getGrpHdr().getMsgId();
        } else if (message.getCdtrPmtActvtnReqStsRpt() != null) {
            // pain.014
            return message.getCdtrPmtActvtnReqStsRpt().getGrpHdr().getMsgId();
        } else if (message.getBkToCstmrStmt() != null) {
            // camt.053
            return message.getBkToCstmrStmt().getGrpHdr().getMsgId();
        }

        return "";
    }

    public Message getMessage() {
        return message;
    }
}
