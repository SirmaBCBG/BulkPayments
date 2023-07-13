package com.sirmabc.bulkpayments.persistance.repositories;

import com.sirmabc.bulkpayments.persistance.entities.MessagesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends CrudRepository<MessagesEntity, Integer> {

    public MessagesEntity findByMessageId(String messageId);

}
