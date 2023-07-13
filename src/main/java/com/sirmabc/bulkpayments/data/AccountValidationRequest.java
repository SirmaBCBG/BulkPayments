package com.sirmabc.bulkpayments.data;

import java.math.BigDecimal;

public class AccountValidationRequest {
    public String dcn;
    public String cribanacno;
    public String dribanacno;
    public BigDecimal amount;
    public String ccy;
    public String sendername;
    public String receivername;
    public String rawmsg;

    public AccountValidationRequest(String dcn, String cribanacno, String dribanacno, BigDecimal amount, String ccy, String sendername, String receivername, String rawmsg) {
        this.dcn = dcn;
        this.cribanacno = cribanacno;
        this.dribanacno = dribanacno;
        this.amount = amount;
        this.ccy = ccy;
        this.sendername = sendername;
        this.receivername = receivername;
        this.rawmsg = rawmsg;
    }

    public String getDcn() {
        return dcn;
    }

    public void setDcn(String dcn) {
        this.dcn = dcn;
    }

    public String getCribanacno() {
        return cribanacno;
    }

    public void setCribanacno(String cribanacno) {
        this.cribanacno = cribanacno;
    }

    public String getDribanacno() {
        return dribanacno;
    }

    public void setDribanacno(String dribanacno) {
        this.dribanacno = dribanacno;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getRawmsg() {
        return rawmsg;
    }

    public void setRawmsg(String rawmsg) {
        this.rawmsg = rawmsg;
    }
}
