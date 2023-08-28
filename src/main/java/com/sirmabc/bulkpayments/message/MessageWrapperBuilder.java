package com.sirmabc.bulkpayments.message;

import com.sirmabc.bulkpayments.communicators.BorikaClient;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.enums.InOut;
import com.sirmabc.bulkpayments.util.xmlsigner.XMLSigner;
import montranMessage.montran.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

@Component
public final class MessageWrapperBuilder {

    private final BorikaClient borikaClient;

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    private final Properties properties;

    private final XMLSigner xmlSigner;

    @Autowired
    public MessageWrapperBuilder(BorikaClient borikaClient, BulkMessagesRepository bulkMessagesRepository, ParticipantsRepository participantsRepository, Properties properties, XMLSigner xmlSigner) {
        this.borikaClient = borikaClient;
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
        this.properties = properties;
        this.xmlSigner = xmlSigner;
    }

    public MessageWrapper build(Message message, InOut inOut, HttpResponse<String> response) {
        return new MessageWrapper(message, inOut, response, borikaClient, bulkMessagesRepository, participantsRepository, properties, xmlSigner);
    }
}
