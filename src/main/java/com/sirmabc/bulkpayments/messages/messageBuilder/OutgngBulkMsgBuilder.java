package com.sirmabc.bulkpayments.messages.messageBuilder;

import com.sirmabc.bulkpayments.messages.OutgngBulkMsg;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.montran.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutgngBulkMsgBuilder {

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    @Autowired
    public OutgngBulkMsgBuilder(BulkMessagesRepository bulkMessagesRepository,
                                ParticipantsRepository participantsRepository,
                                Properties properties,
                                XMLSigner xmlSigner) {
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    public OutgngBulkMsg build(Message message) {
        return new OutgngBulkMsg(message, bulkMessagesRepository, participantsRepository, properties, xmlSigner);
    }
}
