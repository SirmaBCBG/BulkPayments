package com.sirmabc.bulkpayments.messages;

import com.sirmabc.bulkpayments.persistance.entities.MessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.MessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.services.BorikaMessageService;
import com.sirmabc.bulkpayments.services.FCService;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Header;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001.FIToFIPaymentStatusReportV03;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FIToFIPaymentStatusReportMessage extends DefinedMessage {
    private FIToFIPaymentStatusReportV03 fiToFIPaymentStatusReport;

    public FIToFIPaymentStatusReportMessage(ParticipantsRepository participantsRepository,
                                            MessagesRepository messagesRepository,
                                            XMLSigner xmlSigner,
                                            Properties properties,
                                            FCService fcService,
                                            BorikaMessageService borikaMessageService) {
        super(participantsRepository, messagesRepository, xmlSigner, properties, fcService, borikaMessageService);
    }

    @Override
    public MessagesEntity buildEntity() {
        MessagesEntity entity = new MessagesEntity();

        entity.setMessageId(fiToFIPaymentStatusReport.getGrpHdr().getMsgId());
        entity.setMessageXml(getXmlMessage());
        entity.setMessageCreationDate(fiToFIPaymentStatusReport.getGrpHdr().getCreDtTm().toGregorianCalendar().getTime());
        entity.setMessageType(getAppHdr().getMsgDefIdr());
        entity.setPrevMessageId(fiToFIPaymentStatusReport.getOrgnlGrpInfAndSts().getOrgnlMsgId());
        entity.setMessageSeq(getRequestHeaders().get(Header.X_MONTRAN_RTP_MESSAGE_SEQ).get(0));
        entity.setMessageStatus(fiToFIPaymentStatusReport.getOrgnlGrpInfAndSts().getGrpSts().toString());

        return entity;
    }

    @Override
    public void saveMessage() {

    }

    @Override
    protected CodesPacs002 isValid() {
        return null;
    }

    public String getMessageStatus() {
        String pacs02Status = fiToFIPaymentStatusReport.getOrgnlGrpInfAndSts().getGrpSts().value();
        return pacs02Status;
    }

    @Override
    public void processMessage(CodesPacs002 codesPacs002, String filePath) {

    }

    public FIToFIPaymentStatusReportV03 getFiToFIPaymentStatusReport() {
        return fiToFIPaymentStatusReport;
    }

    public void setFiToFIPaymentStatusReport(FIToFIPaymentStatusReportV03 fiToFIPaymentStatusReport) {
        this.fiToFIPaymentStatusReport = fiToFIPaymentStatusReport;
    }
}
