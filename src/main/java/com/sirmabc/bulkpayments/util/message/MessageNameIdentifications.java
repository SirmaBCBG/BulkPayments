package com.sirmabc.bulkpayments.util.message;

public enum MessageNameIdentifications {

    PACS008("pacs.008.001.02"),

    PACS002("pacs.002");

    public final String nameIdentification;

    private MessageNameIdentifications(String nameIdentification) {
        this.nameIdentification = nameIdentification;
    }

}