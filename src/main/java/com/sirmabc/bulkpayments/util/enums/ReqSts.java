package com.sirmabc.bulkpayments.util.enums;

public enum ReqSts {

    ACCP("ACCP"),

    RJCT("RJCT"),

    EMPTY("EMPTY");

    public final String sts;

    ReqSts(String sts) {
        this.sts = sts;
    }
}
