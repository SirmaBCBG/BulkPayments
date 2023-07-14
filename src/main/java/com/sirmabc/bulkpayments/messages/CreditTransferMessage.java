package com.sirmabc.bulkpayments.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirmabc.bulkpayments.communicators.AccountValidationClient;
import com.sirmabc.bulkpayments.communicators.BorikaClientScheduler;
import com.sirmabc.bulkpayments.data.AccountValidationRequest;
import com.sirmabc.bulkpayments.data.AccountValidationResponse;
import com.sirmabc.bulkpayments.data.FcRequestApprovedPacs008;
import com.sirmabc.bulkpayments.messages.messageBuilder.CreditTransferMessageBuilder;
import com.sirmabc.bulkpayments.persistance.entities.MessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.MessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.services.BorikaMessageService;
import com.sirmabc.bulkpayments.services.FCService;
import com.sirmabc.bulkpayments.util.*;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransactionInformation11;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader33;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.UUID;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreditTransferMessage extends DefinedMessage {

    private static final Logger logger = LoggerFactory.getLogger(BorikaClientScheduler.class);
    private FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10 fiToFICstmrCdtTrf;

    private String addInfo;

    // this object is build during isValid method, and is only used for calling fc after Borica approved the transaction.
    private FcRequestApprovedPacs008 requestApprovedPacs008;

    private final AccountValidationClient accountValidationClient;

    private final CreditTransferMessageBuilder creditTransferMessageBuilder;

    public CreditTransferMessage(ParticipantsRepository participantsRepository,
                                 MessagesRepository messagesRepository,
                                 CreditTransferMessageBuilder creditTransferMessageBuilder,
                                 XMLSigner xmlSigner,
                                 AccountValidationClient accountValidationClient,
                                 Properties properties,
                                 FCService fcService,
                                 BorikaMessageService borikaMessageService) {
        super(participantsRepository, messagesRepository, xmlSigner, properties, fcService, borikaMessageService);
        this.creditTransferMessageBuilder = creditTransferMessageBuilder;
        this.accountValidationClient = accountValidationClient;
    }
    @Override
    public void processMessage(CodesPacs002 codesPacs002) throws Exception {
        logger.info("processMessage()...");
        XMLFileHelper.objectToXmlFile(fiToFICstmrCdtTrf, properties.getFiToFICstmrCdtTrfXmlFilePath());
    }

    @Override
    protected CodesPacs002 isValid() throws Exception {
        logger.info("isValid()...");

        FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10 creditTransfer = getFiToFICstmrCdtTrf();

        GroupHeader33 groupHeader = creditTransfer.getGrpHdr();
        XMLGregorianCalendar intrBksttlemn = groupHeader.getIntrBkSttlmDt();

        Date now = new Date();

        boolean sameDay = Util.sameDay(intrBksttlemn.toGregorianCalendar().getTime(), now);

        if(!sameDay) {
            return CodesPacs002.FF01;
        }
        // check currency
        if(!groupHeader.getTtlIntrBkSttlmAmt().getCcy().equals("BGN")){
            return CodesPacs002.FF01;
        }
        //check if amount is not a negative or 0
        if(groupHeader.getTtlIntrBkSttlmAmt().getValue().compareTo(new BigDecimal(0))  <= 0 ){
            return CodesPacs002.FF01;
        }

        //check if Local Instrument tag is INST
        if(!groupHeader.getPmtTpInf().getLclInstrm().getCd().equals("INST")){
            return CodesPacs002.FF01;
        }

        String finInstnBic = groupHeader.getInstgAgt().getFinInstnId().getBIC();
        if(participantsRepository.checkParticipant(finInstnBic) == 0) {
            return CodesPacs002.RC01;
        }

        //Credit information validations
        for(CreditTransferTransactionInformation11 creditTransferTransactionInformation : creditTransfer.getCdtTrfTxInf()) {

            String debtorIBAN = creditTransferTransactionInformation.getDbtrAcct().getId().getIBAN();
            String creditorIBAN = creditTransferTransactionInformation.getCdtrAcct().getId().getIBAN();

            //check if ibans are valid.
            if(!Util.validateIBAN(debtorIBAN) || !Util.validateIBAN(creditorIBAN)){
                return CodesPacs002.AC01;
            }

            if(!creditTransferTransactionInformation.getIntrBkSttlmAmt().getCcy().equals("BGN")){
                return CodesPacs002.FF01;
            }
            //check if amount is not a negative or 0
            if (creditTransferTransactionInformation.getIntrBkSttlmAmt().getValue().compareTo(new BigDecimal(0))  <= 0 ){
                return CodesPacs002.FF01;
            }

            //check bics again...
            String debtorAgt = creditTransferTransactionInformation.getDbtrAgt().getFinInstnId().getBIC();
            if (participantsRepository.checkParticipant(debtorAgt) == 0) {
                return CodesPacs002.DNOR;
            }

            String creditorAgt = creditTransferTransactionInformation.getCdtrAgt().getFinInstnId().getBIC();
            if(participantsRepository.checkParticipant(creditorAgt) == 0) {
                return CodesPacs002.CNOR;
            }

            // Start validation via Account validation service.
            String msgID = getAppHdr().getBizMsgIdr();
            String creditIban = creditTransferTransactionInformation.getCdtrAcct().getId().getIBAN();
            String creditName = creditTransferTransactionInformation.getCdtr().getNm();
            String debtIban = creditTransferTransactionInformation.getDbtrAcct().getId().getIBAN();
            String debtName = creditTransferTransactionInformation.getDbtr().getNm();
            BigDecimal amount = creditTransferTransactionInformation.getIntrBkSttlmAmt().getValue();
            String ccy = creditTransferTransactionInformation.getIntrBkSttlmAmt().getCcy();

            AccountValidationRequest request = new AccountValidationRequest(msgID,creditIban, debtIban, amount,ccy,debtName, creditName,getXmlMessage());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(request);

            HttpResponse<String> response = accountValidationClient.postMessage(msgID, json);
            AccountValidationResponse accountValidationResponse = mapper.readValue(response.body(), AccountValidationResponse.class);

            setAddInfo(accountValidationResponse.getAccvallog().getAddtlinfo());

            if(accountValidationResponse.getAccvallog().getResponse() < 1) {

                return CodesPacs002.AC01;

            }
        }
        return CodesPacs002.OK01;
    }


    public MessagesEntity buildEntity(){
        MessagesEntity entity = new MessagesEntity();

        //CreditTransferTransactionInformation11 creditTransferTransactionInformation = fiToFICstmrCdtTrf.getCdtTrfTxInf().get(0);

        //TODO: remove hack
        entity.setMessageId(fiToFICstmrCdtTrf.getGrpHdr().getMsgId() + UUID.randomUUID());
        entity.setMessageXml(getXmlMessage());
        entity.setMessageCreationDate(fiToFICstmrCdtTrf.getGrpHdr().getCreDtTm().toGregorianCalendar().getTime());
        entity.setMessageType(getAppHdr().getMsgDefIdr());
        //entity.setCreditorIban(creditTransferTransactionInformation.getCdtrAcct().getId().getIBAN());
        //entity.setDebtorIban(creditTransferTransactionInformation.getDbtrAcct().getId().getIBAN());
        entity.setMessageStatus(MsgStatuses.INPR.toString());

        return entity;
    }

    public FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10 getFiToFICstmrCdtTrf() {
        return fiToFICstmrCdtTrf;
    }

    public void setFiToFICstmrCdtTrf(FIToFICustomerCreditTransferV02EPC12216SCTINSTIB2019V10 fiToFICstmrCdtTrf) {
        this.fiToFICstmrCdtTrf = fiToFICstmrCdtTrf;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }


}
