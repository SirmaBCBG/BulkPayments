package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.util.Header;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public class DatabaseService {

    @Autowired
    private static BulkMessagesRepository bulkMessagesRepository;

    public static void saveBulkMessage(BusinessApplicationHeaderV01 appHdr, HttpResponse<String> response) {
        BulkMessagesEntity entity = buildBulkMessagesEntity(appHdr, response.body(), response.headers().map().get(Header.X_MONTRAN_RTP_MESSAGE_SEQ).get(0));
        bulkMessagesRepository.save(entity);
    }

    private static BulkMessagesEntity buildBulkMessagesEntity(BusinessApplicationHeaderV01 appHdr, String xmlMessage, String messageSeq) {
        BulkMessagesEntity entity = new BulkMessagesEntity();

        entity.setMessageId(appHdr.getBizMsgIdr());
        entity.setMessageXml(xmlMessage);
        entity.setMessageType(appHdr.getMsgDefIdr());
        entity.setMessageSeq(messageSeq);

        return entity;
    }
}
