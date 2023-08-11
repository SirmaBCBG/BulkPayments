package com.sirmabc.bulkpayments.util;

public enum MsgDefIdrs {

    PACS008("pacs.008.001.02"),

    PACS002("pacs.002.001.03"),

    PACS004("pacs.004.001.02"),

    CAMT056("camt.056.001.01");

    public final String idr;

    private MsgDefIdrs(String idr) {
        this.idr = idr;
    }

}
