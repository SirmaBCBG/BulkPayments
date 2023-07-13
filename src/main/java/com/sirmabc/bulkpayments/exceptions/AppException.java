package com.sirmabc.bulkpayments.exceptions;

public class AppException extends Exception {
    public static final int CERTIFICATE_INVALID = 401;
    public static final int CERTIFICATE_EXPIRED = 401;

    // Signature/corporate seal certificate has been blocked by the ASPSP.
    public static final int CERTIFICATE_BLOCKED = 401;

    public static final int CERTIFICATE_REVOKED = 401;
    public static final int CERTIFICATE_MISSING = 401;

    public static final int SIGNATURE_INVALID = 401;
    public static final int SIGNATURE_MISSING = 401;

    //Format of certain request fields are not matching the XS2A requirements.
    // An explicit path to the corresponding field might be added in the return message.//
    public static final int FORMAT_ERROR = 400;

    // The parameter is not supported by the API provider.
    // his code should only be used for parameters that are described as "optional if supported by API provider."
    public static final int PARAMETER_NOT_SUPPORTED = 400;


    public static final int PSU_CREDENTIALS_INVALID = 401;

    public static final int SERVICE_INVALID = 401;

    public static final int RTP_CHANNEL_INVALID = 401;
    public static final int SERVICE_INVALID_WEB_METHOD = 405;
    public static final int SERVICE_BLOCKED = 403;

    public static final int CORPORATE_ID_INVALID = 401;

    //    public static final int CONSENT_UNKNOWN = 400;
//    public static final int CONSENT_INVALID = 401;
//    public static final int CONSENT_EXPIRED = 401;
//
//
//    public static final int TOKEN_UNKNOWN = 401;
//    public static final int TOKEN_INVALID = 401;
    public static final int TOKEN_EXPIRED = 401;


    public static final int RESOURCE_UNKNOWN_PATH = 404;
    public static final int RESOURCE_EXPIRED_PATH = 403;


    public static final int TIMESTAMP_INVALID = 400;
    public static final int PERIOD_INVALID = 400;
    public static final int SCA_METHOD_UNKNOWN = 400;

    // PIS Specific
    public static final int PRODUCT_INVALID = 403;
    public static final int PRODUCT_UNKNOWN = 404;
    public static final int PAYMENT_FAILED = 400;
    public static final int REQUIRED_KID_MISSING = 401;
    public static final int EXECUTION_DATE_INVALID = 400;


    // AIS Specific
    public static final int SESSIONS_NOT_SUPPORTED = 400;
    public static final int ACCESS_EXCEEDED = 429;
    public static final int REQUESTED_FORMATS_INVALID = 406;

    // PIIS Specific
    public static final int CARD_INVALID = 400;
    public static final int NO_PIIS_ACTIVATION = 400;


    public static final int INTERNAL_ERROR = 500;

    private int code;


    public AppException(String message) {
        this(INTERNAL_ERROR, null, message);
    }

    public AppException(int type, String message) {
        this(type, null, message);
    }

    public AppException(String message, Throwable t) {
        this(INTERNAL_ERROR, t, message);
    }

    public AppException(int code, Throwable t, String message) {
        //super(code, t, message, "");
        super(message, t);

        this.code = code;
    }

    public int getCode() {
        return code;
    }
}