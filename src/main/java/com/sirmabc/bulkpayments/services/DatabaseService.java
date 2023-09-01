package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import com.sirmabc.bulkpayments.persistance.entities.ParticipantsEntity;
import com.sirmabc.bulkpayments.persistance.repositories.BulkMessagesRepository;
import com.sirmabc.bulkpayments.persistance.repositories.ParticipantsRepository;
import com.sirmabc.bulkpayments.util.xmlsigner.Util;
import montranMessage.montran.participants.ParticipantInfo;
import montranMessage.montran.participants.ParticipantsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    private final BulkMessagesRepository bulkMessagesRepository;

    private final ParticipantsRepository participantsRepository;

    @Autowired
    public DatabaseService(BulkMessagesRepository bulkMessagesRepository, ParticipantsRepository participantsRepository) {
        this.bulkMessagesRepository = bulkMessagesRepository;
        this.participantsRepository = participantsRepository;
    }

    public BulkMessagesEntity saveBulkMessageEntity(BulkMessagesEntity entity) {
        logger.info("saveBulkMessageEntity(): Saving message entity " + entity);
        return bulkMessagesRepository.save(entity);
    }

    public BulkMessagesEntity findBulkMessageEntity(String messageId) {
        logger.info("saveBulkMessageEntity(): Searching for message entity with id " + messageId);
        return bulkMessagesRepository.findByMessageId(messageId);
    }

    public BulkMessagesEntity buildBulkMessageEntity(String messageId,
                                                     String messageXml,
                                                     String messageType,
                                                     String messageSeq,
                                                     String acknowledged,
                                                     String reqSts,
                                                     String inOut,
                                                     String fileName,
                                                     String error,
                                                     String originalMessage) {
        BulkMessagesEntity entity = new BulkMessagesEntity();

        entity.setMessageId(messageId);
        entity.setMessageXml(messageXml);
        entity.setMessageType(messageType);
        entity.setMessageSeq(messageSeq);
        entity.setAcknowledged(acknowledged);
        entity.setReqSts(reqSts);
        entity.setInOut(inOut);
        entity.setFileName(fileName);
        entity.setError(error);
        entity.setOriginalMessage(originalMessage);

        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateParticipants(ParticipantsType participantsType){
        logger.info("Updating the participants");

        if (participantsType != null) {
            participantsRepository.archive();
            int i = 0;
            for (ParticipantInfo info : participantsType.getParticipant()) {

                ParticipantsEntity entity = new ParticipantsEntity();
                entity.setBic(info.getBic());
                entity.setpType(info.getType().toString());
                entity.setValidFrom(Util.toSQLDate(info.getValidFrom()));
                entity.setValidTo(Util.toSQLDate(info.getValidTo()));
                entity.setpStatus(info.getStatus().toString());
                entity.setpOnline(Boolean.toString(info.isOnline()));
                entity.setDirectAgent(info.getDirectAgent());

                logger.debug("Participant entity: " + entity);

                participantsRepository.save(entity);
            }
        }
    }
}
