package com.sirmabc.bulkpayments.messages;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.CodesPacs002;
import com.sirmabc.bulkpayments.util.Header;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.net.http.HttpResponse;

import static com.sirmabc.bulkpayments.util.Header.X_MONTRAN_RTP_POSSIBLE_DUPLICATE;

public class IncmgBulkMsg implements BulkMsgInt {

    private static final Logger logger = LoggerFactory.getLogger(IncmgBulkMsg.class);

    private Message message;

    private HttpResponse<String> response;

    protected final BulkMessagesRepository bulkMessagesRepository;

    protected final ParticipantsRepository participantsRepository;

    protected final Properties properties;

    protected final XMLSigner xmlSigner;

    public IncmgBulkMsg(Message message,
                        HttpResponse<String> response,
                        BulkMessagesRepository bulkMessagesRepository,
                        ParticipantsRepository participantsRepository,
                        Properties properties,
                        XMLSigner xmlSigner) {
        this.message = message;
        this.response = response;
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    public CodesPacs002 validate() throws Exception {
        logger.info("Validating application header");

        CodesPacs002 pacs002Code = isValidAppHdr();
        if (!pacs002Code.equals(CodesPacs002.OK01)) {
            return pacs002Code;
        }

        pacs002Code = isDuplicate();
        if(!pacs002Code.equals(CodesPacs002.OK01)){
            return pacs002Code;
        }

        return CodesPacs002.OK01;
    }

    @Override
    public void saveMessage() {
        BulkMessagesEntity entity = buildEntity();
        bulkMessagesRepository.save(entity);
    }

    private CodesPacs002 isValidAppHdr() throws Exception {
        Document document = xmlSigner.string2XML(response.body());
        if (!xmlSigner.verify(document)) {
            return CodesPacs002.FF01;
        }

        String senderBic = message.getAppHdr().getFr().getFIId().getFinInstnId().getBICFI();
        String receiverBic = message.getAppHdr().getTo().getFIId().getFinInstnId().getBICFI();

        int result = participantsRepository.checkParticipant(senderBic);
        String bic = properties.getRtpChannel();

        //check if sender bic is valid and check if receiver bic is the same as the institution bic.
        if(result == 0 || !receiverBic.equals(bic)) {
            return CodesPacs002.RC01;
        }

        return  CodesPacs002.OK01;
    }

    private CodesPacs002 isDuplicate() {
        boolean isDuplicateHeader = response.headers().map().containsKey(X_MONTRAN_RTP_POSSIBLE_DUPLICATE.header);

        // this way so there is no call to db if the header present!
        if (isDuplicateHeader) {
            BulkMessagesEntity entity = bulkMessagesRepository.findByMessageId(message.getAppHdr().getBizMsgIdr());

            if(entity != null) {
                return CodesPacs002.AM05;
            }
        }
        return CodesPacs002.OK01;
    }

    @Override
    public BulkMessagesEntity buildEntity() {
        BulkMessagesEntity entity = new BulkMessagesEntity();

        entity.setMessageId(message.getAppHdr().getBizMsgIdr());
        entity.setMessageXml(response.body());
        entity.setMessageType(message.getAppHdr().getMsgDefIdr());
        entity.setMessageSeq(response.headers().map().get(Header.X_MONTRAN_RTP_MESSAGE_SEQ).get(0));

        return entity;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }
}
