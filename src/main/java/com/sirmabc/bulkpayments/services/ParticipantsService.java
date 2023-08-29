package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.persistance.entities.ParticipantsEntity;
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
public class ParticipantsService {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantsService.class);

    @Autowired
    ParticipantsRepository participantsRepository;

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
