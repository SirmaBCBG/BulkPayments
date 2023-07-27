package com.sirmabc.bulkpayments.messages;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.MsgDefIdrs;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader37;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader33;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.io.FileInputStream;
import java.security.KeyStore;

public class OutgngBulkMsg implements BulkMsgInt {

    private static final Logger logger = LoggerFactory.getLogger(OutgngBulkMsg.class);

    private Message message;

    protected final BulkMessagesRepository bulkMessagesRepository;

    protected final ParticipantsRepository participantsRepository;

    protected final Properties properties;

    protected final XMLSigner xmlSigner;

    public OutgngBulkMsg(Message message,
                         BulkMessagesRepository bulkMessagesRepository,
                         ParticipantsRepository participantsRepository,
                         Properties properties,
                         XMLSigner xmlSigner) {
        this.message = message;
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    public void buildAppHdr() {
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

        // TODO: Finish for the rest of the messages

        appHdr.setTo(generateParty9Choice(properties.getRtpChannel()));
        appHdr.setBizMsgIdr(properties.getBizMsgIdr());
        appHdr.setSgntr(new SignatureEnvelope());

        message.setAppHdr(appHdr);
    }

    public String signMessage() throws Exception {
        return sign(FileHelper.serializeXml(message));
    }

    @Override
    public void saveMessage() {

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

    private String sign(String xml) throws Exception {
        Document document = xmlSigner.string2XML(xml);

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(properties.getKeyStorePath()), properties.getKeyStorePassword().toCharArray());
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(properties.getKeyStorePassword().toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(properties.getKeyStoreAlias(), passwordProtection);

        document = xmlSigner.sign(document, keyEntry);
        String signedXml = xmlSigner.xml2String(document);

        return signedXml;
    }

    // TODO: Build the entity that will be saved in the database
    @Override
    public BulkMessagesEntity buildEntity() {
        return null;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
