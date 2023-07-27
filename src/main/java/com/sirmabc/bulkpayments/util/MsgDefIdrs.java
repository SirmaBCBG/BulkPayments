package com.sirmabc.bulkpayments.util;

public enum MsgDefIdrs {

    PACS008("pacs.008.001.02"),

    PACS002("pacs.002.001.03");

    public final String idr;

    private MsgDefIdrs(String idr) {
        this.idr = idr;
    }

}
