package com.sirmabc.bulkpayments.messages;

import com.sirmabc.bulkpayments.persistance.entities.MessagesEntity;
import com.sirmabc.bulkpayments.util.CodesPacs002;

public interface DefinedMessageInt {

    public MessagesEntity buildEntity();

    public void saveMessage ();

    public void processMessage(CodesPacs002 codesPacs002) throws Exception;

    public CodesPacs002 validate() throws Exception;
}