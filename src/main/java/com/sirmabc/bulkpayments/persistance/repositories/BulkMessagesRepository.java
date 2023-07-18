package com.sirmabc.bulkpayments.persistance.repositories;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkMessagesRepository extends CrudRepository<BulkMessagesEntity, Integer> {

    public BulkMessagesEntity findByMessageId(String messageId);

}
