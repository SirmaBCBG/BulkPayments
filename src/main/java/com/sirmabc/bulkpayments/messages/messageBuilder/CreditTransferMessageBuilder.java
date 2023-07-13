package com.sirmabc.bulkpayments.messages.messageBuilder;

import com.sirmabc.bulkpayments.messages.CreditTransferMessage;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.Util;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.*;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001.*;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransactionInformation11;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreditTransferMessageBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CreditTransferMessageBuilder.class);

    @Autowired
    Properties properties;

    public Message buildPacs002WithInfo(CodesPacs002 reason, CreditTransferMessage creditTransferMessage, List<String> addInfo) throws DatatypeConfigurationException {
        TransactionGroupStatus3Code status = TransactionGroupStatus3Code.ACCP;

        if(!reason.equals(CodesPacs002.OK01)) {
            status = TransactionGroupStatus3Code.RJCT;
        }

        Message message = buildPacs002(reason, status, creditTransferMessage, addInfo);

        return message;
    }

    public Message buildPacs002(CodesPacs002 reason, CreditTransferMessage creditTransferMessage) throws DatatypeConfigurationException {

        TransactionGroupStatus3Code status = TransactionGroupStatus3Code.ACCP;

        if(!reason.equals(CodesPacs002.OK01)) {
            status = TransactionGroupStatus3Code.RJCT;
        }

        List<String> addInfo = new ArrayList<>();
        Message message = buildPacs002(reason, status, creditTransferMessage, addInfo);

        return message;
    }

    private Message buildPacs002(CodesPacs002 reason, TransactionGroupStatus3Code status, CreditTransferMessage creditTransferMessage, List<String> addInfo) throws DatatypeConfigurationException {
        logger.info("buildPacs002(): reason - " + reason + ", status - " + status + " , creditTransfer message id: " + creditTransferMessage.getFiToFICstmrCdtTrf().getGrpHdr().getMsgId());

        Message message = new Message();

        Instant now = Instant.now();

        String dateTimeString = now.toString();
        XMLGregorianCalendar nowDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);

        String msgId = Util.generateMsgId();

        //add Application Header to the message
        BusinessApplicationHeaderV01 appHdr = buildAPPHeader(creditTransferMessage, nowDate);
        message.setAppHdr(appHdr);

        FIToFIPaymentStatusReportV03EPC12216SCTINSTIB2019V10 pacs002 = new FIToFIPaymentStatusReportV03EPC12216SCTINSTIB2019V10();

        //Create and set Group Header of pacs.002 message
        GroupHeader37 grpHdr = buildGrpHeader(msgId, nowDate);
        pacs002.setGrpHdr(grpHdr);

        //Setting up original information of pacs.002 message
        OriginalGroupInformation20 originalGroupInformation = buildOriginalGroupInformation(status,creditTransferMessage);
        pacs002.setOrgnlGrpInfAndSts(originalGroupInformation);

        //Setting PaymentTransactionInformation of pacs.002
        // This is list because there might be multiple transactions in one payment (Future bulk)
        List<PaymentTransactionInformation26> paymentTransactionInformationList = buildPaymentTransactionInformation(reason, msgId.toString(),creditTransferMessage, addInfo);

        for(PaymentTransactionInformation26 PaymentTransactionInformation : paymentTransactionInformationList){
            pacs002.getTxInfAndSts().add(PaymentTransactionInformation);
        }

        message.setFIToFIPmtStsRpt(pacs002);

        return message;
    }

    private BusinessApplicationHeaderV01 buildAPPHeader(CreditTransferMessage creditTransferMessage, XMLGregorianCalendar nowDate) {

        BusinessApplicationHeaderV01 appHdrCreditTranferMessage = creditTransferMessage.getAppHdr();

        BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();
        appHdr.setBizMsgIdr(properties.getBizMsgIdr());
        appHdr.setCreDt(nowDate);
        appHdr.setTo(appHdrCreditTranferMessage.getFr());

        FinancialInstitutionIdentification8 financialInstitutionIdentification8 = new FinancialInstitutionIdentification8();
        financialInstitutionIdentification8.setBICFI(properties.getRtpChannel());

        BranchAndFinancialInstitutionIdentification5 branchAndFinancialInstitutionIdentification5 = new BranchAndFinancialInstitutionIdentification5();
        branchAndFinancialInstitutionIdentification5.setFinInstnId(financialInstitutionIdentification8);

        Party9Choice party9Choice = new Party9Choice();
        party9Choice.setFIId(branchAndFinancialInstitutionIdentification5);

        appHdr.setFr(party9Choice);

        appHdr.setSgntr(new SignatureEnvelope());

        return appHdr;
    }

    private GroupHeader37 buildGrpHeader(String msgId, XMLGregorianCalendar nowDate) {

        GroupHeader37 grpHdr = new GroupHeader37();
        grpHdr.setMsgId(msgId.toString());
        grpHdr.setCreDtTm(nowDate);

        String bic = properties.getRtpChannel();
        BranchAndFinancialInstitutionIdentification4 finInst = new BranchAndFinancialInstitutionIdentification4();
        FinancialInstitutionIdentification7 identification7 = new FinancialInstitutionIdentification7();
        identification7.setBIC(bic);
        finInst.setFinInstnId(identification7);

        grpHdr.setInstdAgt(finInst);

        return grpHdr;

    }

    private OriginalGroupInformation20 buildOriginalGroupInformation (TransactionGroupStatus3Code status, CreditTransferMessage creditTransferMessage) {

        OriginalGroupInformation20 originalGroupInformation = new OriginalGroupInformation20();
        originalGroupInformation.setOrgnlMsgId(creditTransferMessage.getFiToFICstmrCdtTrf().getGrpHdr().getMsgId());
        originalGroupInformation.setOrgnlMsgNmId(creditTransferMessage.getAppHdr().getMsgDefIdr());
        originalGroupInformation.setGrpSts(status);

        return originalGroupInformation;
    }

    private List<PaymentTransactionInformation26> buildPaymentTransactionInformation(CodesPacs002 reason, String msgId, CreditTransferMessage creditTransferMessage, List<String> addInfo) {
        List<PaymentTransactionInformation26>  paymentTransactionInformationList = new ArrayList<>();
        PaymentTransactionInformation26 paymentTransactionInformation;

        for (CreditTransferTransactionInformation11 creditTransferTransactionInformation : creditTransferMessage.getFiToFICstmrCdtTrf().getCdtTrfTxInf()) {

            for (CreditTransferTransactionInformation11 transferTransactionInformation : creditTransferMessage.getFiToFICstmrCdtTrf().getCdtTrfTxInf()) {

                paymentTransactionInformation = new PaymentTransactionInformation26();
                paymentTransactionInformation.setStsId(msgId);

                //set end to end ID
                String endToEndId = creditTransferTransactionInformation.getPmtId().getEndToEndId();
                paymentTransactionInformation.setOrgnlEndToEndId(endToEndId);

                // set original TX ID
                String txID = creditTransferTransactionInformation.getPmtId().getTxId();
                paymentTransactionInformation.setOrgnlTxId(txID);

                // set Acceptance date
                paymentTransactionInformation.setAccptncDtTm(creditTransferTransactionInformation.getAccptncDtTm());

                //set OriginalTransactionRef
                OriginalTransactionReference13 originalTransactionReference = new OriginalTransactionReference13();
                PaymentTypeInformation22 paymentTypeInformation = new PaymentTypeInformation22();

                // set Service level
                ServiceLevel8Choice serviceLevel8Choice = new ServiceLevel8Choice();
                serviceLevel8Choice.setCd(creditTransferMessage.getFiToFICstmrCdtTrf().getGrpHdr().getPmtTpInf().getSvcLvl().getCd());
                paymentTypeInformation.setSvcLvl(serviceLevel8Choice);

                // set local Instrument
                LocalInstrument2Choice localInstrument2Choice = new LocalInstrument2Choice();
                localInstrument2Choice.setCd(creditTransferMessage.getFiToFICstmrCdtTrf().getGrpHdr().getPmtTpInf().getLclInstrm().getCd());
                paymentTypeInformation.setLclInstrm(localInstrument2Choice);

                // set dbtrAGT
                BranchAndFinancialInstitutionIdentification4 dbtrAgt = new BranchAndFinancialInstitutionIdentification4();
                FinancialInstitutionIdentification7 finInst = new FinancialInstitutionIdentification7();
                finInst.setBIC(transferTransactionInformation.getDbtrAgt().getFinInstnId().getBIC());
                dbtrAgt.setFinInstnId(finInst);
                originalTransactionReference.setDbtrAgt(dbtrAgt);


                //set Status Reason Information
                StatusReasonInformation8 statusReasonInformation = new StatusReasonInformation8();

                StatusReason6Choice reasonChoice = new StatusReason6Choice();
                reasonChoice.setCd(reason.toString());
                statusReasonInformation.setRsn(reasonChoice);

                paymentTransactionInformation.getStsRsnInf().add(statusReasonInformation);

                for (String currentInfo : addInfo) {
                    statusReasonInformation.getAddtlInf().add(currentInfo);
                }

                originalTransactionReference.setPmtTpInf(paymentTypeInformation);
                paymentTransactionInformation.setOrgnlTxRef(originalTransactionReference);

                paymentTransactionInformationList.add(paymentTransactionInformation);

            }

        }
        return paymentTransactionInformationList;
    }



    //    private BusinessApplicationHeaderV01 buildAppHdr(Party9Choice from, Party9Choice to, String msgDefIdr, String  bizSvc, XMLGregorianCalendar creDt) {
//
//
//
//        BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();
//        appHdr.setFr(from);
//        appHdr.setTo(to);
//        appHdr.setBizMsgIdr(msgId.toString());
//        appHdr.setMsgDefIdr(msgDefIdr);
//        appHdr.setBizSvc(bizSvc);
//        appHdr.setCreDt(creDt);
//
//        return appHdr;
//    }

}
