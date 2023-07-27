package com.sirmabc.bulkpayments.messages;

import com.sirmabc.bulkpayments.persistance.entities.BulkMessagesEntity;

public interface BulkMsgInt {

    public void saveMessage();

    public BulkMessagesEntity buildEntity();

}
