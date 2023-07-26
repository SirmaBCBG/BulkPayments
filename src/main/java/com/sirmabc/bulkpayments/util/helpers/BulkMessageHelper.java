package com.sirmabc.bulkpayments.util.helpers;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Directory;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader37;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader33;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.FileInputStream;
import java.net.http.HttpResponse;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE;

@Component
public class BulkMessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(BulkMessageHelper.class);

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    @Autowired
    public BulkMessageHelper(BulkMessagesRepository bulkMessagesRepository, ParticipantsRepository participantsRepository, Properties properties, XMLSigner xmlSigner) {
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    // TODO: Check if everything in the buildAppHdr method is correct
    public BusinessApplicationHeaderV01 buildAppHdr(Message message) {
        logger.info("Building application header");

        BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();

        // TODO: Add enums for the msgDefIdr field
        // pacs.008
        if (message.getFIToFICstmrCdtTrf() != null) {
            GroupHeader33 grpHdr = message.getFIToFICstmrCdtTrf().getGrpHdr();

            appHdr.setFr(generateParty9Choice(grpHdr.getInstgAgt().getFinInstnId().getBIC()));
            //appHdr.setMsgDefIdr(...);
            appHdr.setCreDt(grpHdr.getCreDtTm());
        } else {
            // pacs.002
            if (message.getFIToFIPmtStsRpt() != null) {
                GroupHeader37 grpHdr = message.getFIToFIPmtStsRpt().getGrpHdr();

                appHdr.setFr(generateParty9Choice(grpHdr.getInstgAgt().getFinInstnId().getBIC()));
                //appHdr.setMsgDefIdr(...);
                appHdr.setCreDt(grpHdr.getCreDtTm());
            }
        }

        appHdr.setTo(generateParty9Choice(properties.getRtpChannel()));
        appHdr.setBizMsgIdr(properties.getBizMsgIdr());
        appHdr.setSgntr(new SignatureEnvelope());

        return appHdr;
    }

    public String signMessage(String xml) throws Exception {
        Document document = xmlSigner.string2XML(xml);

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(properties.getKeyStorePath()), properties.getKeyStorePassword().toCharArray());
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(properties.getKeyStorePassword().toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(properties.getKeyStoreAlias(), passwordProtection);

        document = xmlSigner.sign(document, keyEntry);
        String signedXml = xmlSigner.xml2String(document);

        return signedXml;
    }

    public CodesPacs002 validate(BusinessApplicationHeaderV01 appHdr, HttpResponse<String> response) throws Exception {
        logger.info("Validating application header...");

        CodesPacs002 pacs002Code = isValidAppHdr(appHdr, response.body());
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            return pacs002Code;
        }

        pacs002Code = isDuplicate(appHdr, response.headers().map());
        if(!pacs002Code.equals(CodesPacs002.OK01)){
            return pacs002Code;
        }

        return CodesPacs002.OK01;
    }

    public List<Directory> getAllOutgngBulkMsgsDirs() {
        List<Directory> directories = new ArrayList<>();
        for (String path : properties.getAllOutgngBulkMsgsDirPaths()) directories.add(FileHelper.getDirectoryObject(path, ".xml"));

        return directories;
    }

    private CodesPacs002 isValidAppHdr(BusinessApplicationHeaderV01 appHdr, String xmlMessage) throws Exception {
        Document document = xmlSigner.string2XML(xmlMessage);
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        String senderBic = appHdr.getFr().getFIId().getFinInstnId().getBICFI();
        String receiverBic = appHdr.getTo().getFIId().getFinInstnId().getBICFI();

        int result = participantsRepository.checkParticipant(senderBic);
        String bic = properties.getRtpChannel();

        //check if sender bic is valid and check if receiver bic is the same as the institution bic.
        if(result == 0 || !receiverBic.equals(bic)) {
            return CodesPacs002.RC01;
        }

        return  CodesPacs002.OK01;
    }

    private CodesPacs002 isDuplicate(BusinessApplicationHeaderV01 appHdr, Map<String, List<String>> headers) {
        boolean isDuplicateHeader = headers.containsKey(X_MONTRAN_RTP_POSSIBLE_DUPLICATE.header);

        // this way so there is no call to db if the header present!
        if (isDuplicateHeader) {
            BulkMessagesEntity entity = bulkMessagesRepository.findByMessageId(appHdr.getBizMsgIdr());

            if(entity != null) {
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
}
