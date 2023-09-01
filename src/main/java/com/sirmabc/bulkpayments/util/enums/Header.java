package com.sirmabc.bulkpayments.util.enums;

public enum Header {

    BRANCH("BRANCH"),

    USERID("USERID"),

    MSGID("MSGID"),

    ENTITY("ENTITY"),

    SOURCE("SOURCE"),

    CONTENT_TYPE("Content-Type"),

    X_MONTRAN_RTP_POSSIBLE_DUPLICATE("X-MONTRAN-RTP-PossibleDuplicate"),

    X_MONTRAN_RTP_CHANNEL("X-MONTRAN-RTP-Channel"),

    X_MONTRAN_RTP_VERSION("X-MONTRAN-RTP-Version"),

    X_MONTRAN_RTP_MESSAGE_SEQ("X-MONTRAN-RTP-MessageSeq"),

    X_MONTRAN_RTP_MESSAGE_TYPE("X-MONTRAN-RTP-MessageType"),

    X_MONTRAN_RTP_REQSTS("X-MONTRAN-RTP-ReqSts");

    public final String header;

    Header(String header) {
        this.header = header;
    }

}
