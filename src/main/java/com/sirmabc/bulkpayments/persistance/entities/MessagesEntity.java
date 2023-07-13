package com.sirmabc.bulkpayments.persistance.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BULK_MESSAGES", schema = "UBXIP", catalog = "")
public class MessagesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private BigInteger id;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Basic
    @Column(name = "MESSAGE_ID")
    private String messageId;
    @Basic
    @Column(name = "MESSAGE_XML")
    private String messageXml;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MESSAGE_CREATION_DATE")
    private Date messageCreationDate;
    @Basic
    @Column(name = "MESSAGE_TYPE")
    private String messageType;
    /*@Basic
    @Column(name = "CREDITOR_IBAN")
    private String creditorIban;
    @Basic
    @Column(name = "DEBTOR_IBAN")
    private String debtorIban;*/
    @Basic
    @Column(name = "PREV_MESSAGE_ID")
    private String prevMessageId;
    @Basic
    @Column(name = "MESSAGE_SEQ")
    private String messageSeq;
    @Basic
    @Column(name = "ACKNOWLEDGED")
    private String acknowledged;

    @Basic
    @Column(name = "MESSAGE_STATUS")
    private String messageStatus;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageXml() {
        return messageXml;
    }

    public void setMessageXml(String messageXml) {
        this.messageXml = messageXml;
    }

    public Date getMessageCreationDate() {
        return messageCreationDate;
    }

    public void setMessageCreationDate(Date messageCreationDate) {
        this.messageCreationDate = messageCreationDate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /*public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }*/

    public String getPrevMessageId() {
        return prevMessageId;
    }

    public void setPrevMessageId(String prevMessageId) {
        this.prevMessageId = prevMessageId;
    }

    public String getMessageSeq() {
        return messageSeq;
    }

    public void setMessageSeq(String messageSeq) {
        this.messageSeq = messageSeq;
    }

    public String getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(String acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagesEntity that = (MessagesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(creationDate, that.creationDate) && Objects.equals(messageId, that.messageId) && Objects.equals(messageXml, that.messageXml) && Objects.equals(messageCreationDate, that.messageCreationDate) && Objects.equals(messageType, that.messageType) /*&& Objects.equals(creditorIban, that.creditorIban) && Objects.equals(debtorIban, that.debtorIban)*/ && Objects.equals(prevMessageId, that.prevMessageId) && Objects.equals(messageSeq, that.messageSeq) && Objects.equals(acknowledged, that.acknowledged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, messageId, messageXml, messageCreationDate, messageType, /*creditorIban, debtorIban,*/ prevMessageId, messageSeq, acknowledged);
    }
}
