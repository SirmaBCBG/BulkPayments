package com.sirmabc.bulkpayments.persistance.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BULK_MESSAGES", schema = "UBXIP", catalog = "")
public class BulkMessagesEntity {
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
    @Column(name = "MESSAGE_TYPE")
    private String messageType;
    @Basic
    @Column(name = "MESSAGE_SEQ")
    private String messageSeq;
    @Basic
    @Column(name = "ACKNOWLEDGED")
    private String acknowledged;

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

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkMessagesEntity that = (BulkMessagesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(creationDate, that.creationDate) && Objects.equals(messageId, that.messageId) && Objects.equals(messageXml, that.messageXml) && Objects.equals(messageType, that.messageType) && Objects.equals(messageSeq, that.messageSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, messageId, messageXml, messageType, messageSeq);
    }
}
