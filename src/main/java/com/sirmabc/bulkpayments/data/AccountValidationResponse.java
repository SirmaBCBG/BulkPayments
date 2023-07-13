package com.sirmabc.bulkpayments.data;

public class AccountValidationResponse {

    public Accvallog accvallog;
    public FcubsWarningResp fcubsWarningResp;

    public AccountValidationResponse() {
    }

    public Accvallog getAccvallog() {
        return accvallog;
    }

    public void setAccvallog(Accvallog accvallog) {
        this.accvallog = accvallog;
    }

    public FcubsWarningResp getFcubsWarningResp() {
        return fcubsWarningResp;
    }

    public void setFcubsWarningResp(FcubsWarningResp fcubsWarningResp) {
        this.fcubsWarningResp = fcubsWarningResp;
    }
}

