package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import montranMessage.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private static BulkMessagesRepository bulkMessagesRepository;

    public static void saveBulkMessage(BusinessApplicationHeaderV01 appHdr, String xmlMessage, String messageSeq) {
        BulkMessagesEntity entity = buildBulkMessagesEntity(appHdr, xmlMessage, messageSeq);
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
