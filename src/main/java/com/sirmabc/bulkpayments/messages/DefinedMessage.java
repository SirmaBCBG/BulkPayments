package com.sirmabc.bulkpayments.messages;

import com.sirmabc.bulkpayments.persistance.entities.MessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.MessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.services.BorikaMessageService;
import com.sirmabc.bulkpayments.services.FCService;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE;

public abstract class DefinedMessage implements DefinedMessageInt {

    protected Map<String, List<String>> requestHeaders;
    private BusinessApplicationHeaderV01 appHdr;
    private String xmlMessage;


    // Autowired services
    protected final ParticipantsRepository participantsRepository;
    protected final MessagesRepository messagesRepository;

    protected final XMLSigner xmlSigner;

    protected final Properties properties;

    protected final FCService fcService;

    protected final BorikaMessageService borikaMessageService;


    @Autowired
    public DefinedMessage(ParticipantsRepository participantsRepository,
                          MessagesRepository messagesRepository,
                          XMLSigner xmlSigner,
                          Properties properties,
                          FCService fcService,
                          BorikaMessageService borikaMessageService) {
        this.participantsRepository = participantsRepository;
        this.messagesRepository = messagesRepository;
        this.xmlSigner = xmlSigner;
        this.properties = properties;
        this.fcService = fcService;
        this.borikaMessageService = borikaMessageService;

    }

    public void saveMessage () {
        MessagesEntity entity = buildEntity();
        messagesRepository.save(entity);
    }

    public CodesPacs002 validate() throws Exception {

        CodesPacs002 pacs002Code = isValidAppHdr();

        if (!pacs002Code.equals(CodesPacs002.OK01)){
            return pacs002Code;
        }

        pacs002Code = isDuplicate();
        if(!pacs002Code.equals(CodesPacs002.OK01)){
            return  pacs002Code;
        }

        pacs002Code = isValid();
        if(!pacs002Code.equals(CodesPacs002.OK01)){
            return  pacs002Code;
        }

        return CodesPacs002.OK01;
    }

    protected abstract CodesPacs002 isValid() throws Exception;

    private CodesPacs002 isDuplicate() {

        boolean isDuplicateHeader = getRequestHeaders().containsKey(X_MONTRAN_RTP_POSSIBLE_DUPLICATE.header);

        // this way so there is no call to db if the header present!
        if (isDuplicateHeader) {

            MessagesEntity entity = messagesRepository.findByMessageId(getAppHdr().getBizMsgIdr());
            if(entity != null) {
                return CodesPacs002.AM05;
            }
        }
        return CodesPacs002.OK01;
    }

    public CodesPacs002 isValidAppHdr () throws Exception{

        Document document = xmlSigner.string2XML(xmlMessage);
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        BusinessApplicationHeaderV01 appHdr = getAppHdr();

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

    public Map<String, List<String>> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, List<String>> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void setAppHdr(BusinessApplicationHeaderV01 appHdr) {
        this.appHdr = appHdr;
    }

    public BusinessApplicationHeaderV01 getAppHdr() {
        return appHdr;
    }

    public String getXmlMessage() {
        return xmlMessage;
    }

    public void setXmlMessage(String xmlMessage) {
        this.xmlMessage = xmlMessage;
    }
}
