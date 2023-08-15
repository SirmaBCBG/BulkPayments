package com.sirmabc.bulkpayments.util;

public enum MsgDefIdrs {

    PACS008("pacs.008.001.02"),

    PACS004("pacs.004.001.02"),

    CAMT056("camt.056.001.01"),

    CAMT029_03("camt.029.001.03"),

    PACS028("pacs.028.001.01"),

    PACS002("pacs.002.001.03"),

    PAIN013("pain.013.001.07"),

    PAIN014("pain.014.001.07"),

    CAMT053("camt.053.001.02");

    public final String idr;

    private MsgDefIdrs(String idr) {
        this.idr = idr;
    }

}
