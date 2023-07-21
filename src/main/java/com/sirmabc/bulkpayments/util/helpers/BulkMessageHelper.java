package com.sirmabc.bulkpayments.util.helpers;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.message.MessageNameIdentifications;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader37;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader33;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE;

public class BulkMessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(BulkMessageHelper.class);

    @Autowired
    private static BulkMessagesRepository bulkMessagesRepository;

    @Autowired
    private static ParticipantsRepository participantsRepository;

    @Autowired
    private static Properties properties;

    @Autowired
    private static XMLSigner xmlSigner;

    public static BusinessApplicationHeaderV01 buildAppHdr(Message message) {
        logger.info("Building application header...");

        BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();

        // pacs.008
        if (message.getFIToFICstmrCdtTrf() != null) {
            GroupHeader33 grpHdr = message.getFIToFICstmrCdtTrf().getGrpHdr();

            appHdr.setFr(generateParty9Choice(grpHdr.getInstgAgt().getFinInstnId().getBIC()));
            appHdr.setMsgDefIdr(MessageNameIdentifications.PACS008.nameIdentification);
            appHdr.setCreDt(grpHdr.getCreDtTm());
        } else {
            // pacs.002
            if (message.getFIToFIPmtStsRpt() != null) {
                GroupHeader37 grpHdr = message.getFIToFIPmtStsRpt().getGrpHdr();

                appHdr.setFr(generateParty9Choice(grpHdr.getInstgAgt().getFinInstnId().getBIC()));
                appHdr.setMsgDefIdr(MessageNameIdentifications.PACS002.nameIdentification);
                appHdr.setCreDt(grpHdr.getCreDtTm());
            }
        }

        appHdr.setTo(generateParty9Choice(properties.getRtpChannel()));
        appHdr.setBizMsgIdr(properties.getBizMsgIdr());
        appHdr.setSgntr(new SignatureEnvelope());

        return appHdr;
    }

    public static CodesPacs002 validate(BusinessApplicationHeaderV01 appHdr, Map<String, List<String>> headers, String xmlMessage) throws Exception {
        logger.info("Validating application header...");

        CodesPacs002 pacs002Code = isValidAppHdr(appHdr, xmlMessage);
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            return pacs002Code;
        }

        pacs002Code = isDuplicate(appHdr, headers);
        if(!pacs002Code.equals(CodesPacs002.OK01)){
            return pacs002Code;
        }

        return CodesPacs002.OK01;
    }

    private static CodesPacs002 isValidAppHdr(BusinessApplicationHeaderV01 appHdr, String xmlMessage) throws Exception {
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

    private static CodesPacs002 isDuplicate(BusinessApplicationHeaderV01 appHdr, Map<String, List<String>> headers) {
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

    private static Party9Choice generateParty9Choice(String bicfi) {
        FinancialInstitutionIdentification8 financialInstitutionIdentification8 = new FinancialInstitutionIdentification8();
        financialInstitutionIdentification8.setBICFI(bicfi);

        BranchAndFinancialInstitutionIdentification5 branchAndFinancialInstitutionIdentification5 = new BranchAndFinancialInstitutionIdentification5();
        branchAndFinancialInstitutionIdentification5.setFinInstnId(financialInstitutionIdentification8);

        Party9Choice party9Choice = new Party9Choice();
        party9Choice.setFIId(branchAndFinancialInstitutionIdentification5);

        return party9Choice;
    }
}
