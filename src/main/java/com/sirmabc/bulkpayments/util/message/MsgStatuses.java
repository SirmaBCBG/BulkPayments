package com.sirmabc.bulkpayments.util.message;

public enum MsgStatuses {

    ACCP("ACCP"),
    RJCT("RJCT"),
    INPR("INPR");

    public final String msgStatus;

    private MsgStatuses(String msgStatus) {
        this.msgStatus = msgStatus;
    }


}
