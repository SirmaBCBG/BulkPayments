package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.enums.CodesPacs002;
import com.sirmabc.bulkpayments.util.enums.InOut;
import com.sirmabc.bulkpayments.util.enums.MsgDefIdrs;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import jakarta.xml.bind.JAXBException;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.io.IOException;
import java.io.StringReader;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final Properties properties;

    private final XMLSigner xmlSigner;

    @Autowired
    public MessageService(Properties properties, XMLSigner xmlSigner) {
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    public BusinessApplicationHeaderV01 buildMessageAppHdr(Message message) throws Exception {
        if (message.getAppHdr() == null) {
            logger.info("buildMessageAppHdr(): Building application header");

            BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();

            // pacs.008
            if (message.getFIToFICstmrCdtTrf() != null) {
                appHdr.setBizMsgIdr(message.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS008.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFICstmrCdtTrf().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getPmtRtr() != null) {
                // pacs.004
                appHdr.setBizMsgIdr(message.getPmtRtr().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS004.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getPmtRtr().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getFIToFIPmtCxlReq() != null) {
                // camt.056
                appHdr.setBizMsgIdr(message.getFIToFIPmtCxlReq().getAssgnmt().getId());
                appHdr.setMsgDefIdr(MsgDefIdrs.CAMT056.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFIPmtCxlReq().getAssgnmt().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getRsltnOfInvstgtn() != null) {
                // camt.029.001.03
                appHdr.setBizMsgIdr(message.getRsltnOfInvstgtn().getAssgnmt().getId());
                appHdr.setMsgDefIdr(MsgDefIdrs.CAMT029_03.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getRsltnOfInvstgtn().getAssgnmt().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getFIToFIPmtStsReq() != null) {
                // pacs.028
                appHdr.setBizMsgIdr(message.getFIToFIPmtStsReq().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS028.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFIPmtStsReq().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getFIToFIPmtStsRpt() != null) {
                // pacs.002
                appHdr.setBizMsgIdr(message.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.PACS002.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getFIToFIPmtStsRpt().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getCdtrPmtActvtnReq() != null) {
                // pain.013
                appHdr.setBizMsgIdr(message.getCdtrPmtActvtnReq().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.PAIN013.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getCdtrPmtActvtnReq().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getCdtrPmtActvtnReqStsRpt() != null) {
                // pain.014
                appHdr.setBizMsgIdr(message.getCdtrPmtActvtnReqStsRpt().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.PAIN014.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getCdtrPmtActvtnReqStsRpt().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            } else if (message.getBkToCstmrStmt() != null) {
                // camt.053
                appHdr.setBizMsgIdr(message.getBkToCstmrStmt().getGrpHdr().getMsgId());
                appHdr.setMsgDefIdr(MsgDefIdrs.CAMT053.idr);

                XMLGregorianCalendar calendar = xmlGregorianCalendarToUTC(message.getBkToCstmrStmt().getGrpHdr().getCreDtTm());
                appHdr.setCreDt(calendar);
            }

            appHdr.setFr(generateParty9Choice(properties.getRtpChannel()));
            appHdr.setTo(generateParty9Choice(properties.getBorikaBic()));
            appHdr.setSgntr(new SignatureEnvelope());

            logger.info("buildMessageAppHdr(): Built application header for message " + appHdr.getBizMsgIdr());

            return appHdr;
        } else {
            logger.error("buildMessageAppHdr(): Application header already exists for message " + message.getAppHdr().getBizMsgIdr());
            return message.getAppHdr();
        }
    }

    public CodesPacs002 validateMessage(Message message, String messageXml) throws Exception {
        logger.info("validateMessage(): Validating message " + message.getAppHdr().getBizMsgIdr());

        Document document = xmlSigner.string2XML(messageXml);
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        } else {
            return CodesPacs002.OK01;
        }
    }

    public String saveMessageToXmlFile(Message message) throws JAXBException, ParserConfigurationException, IOException, SAXException, TransformerException {
        logger.info("saveMessageToXmlFile(): Saving message " + message.getAppHdr().getBizMsgIdr() + " to an xml file");

        String xmlString = XMLHelper.serializeXml(message);

        // Parse the XML String to prevent encoding issues
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

        // Write the parsed document to an XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        // Generate a unique file name
        String fileName = FileHelper.generateUniqueFileBaseName(InOut.IN, message.getAppHdr().getMsgDefIdr(), message.getAppHdr().getBizMsgIdr()) + ".xml";
        logger.info("saveMessageToXmlFile(): Generated file name for message " + message.getAppHdr().getBizMsgIdr() + " is " + fileName);

        StreamResult result = new StreamResult(new File(properties.getIncmgBulkMsgsPath() + "/" + fileName));
        transformer.transform(source, result);

        return fileName;
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
}
