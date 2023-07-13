package com.sirmabc.bulkpayments.persistance.entities;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PARTICIPANTS", schema = "UBXIP", catalog = "")
public class ParticipantsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private BigInteger id;
    @Basic
    @Column(name = "BIC")
    private String bic;
    @Basic
    @Column(name = "P_TYPE")
    private String pType;
    @Basic
    @Column(name = "VALID_FROM")
    private Date validFrom;
    @Basic
    @Column(name = "VALID_TO")
    private Date validTo;
    @Basic
    @Column(name = "P_STATUS")
    private String pStatus;
    @Basic
    @Column(name = "P_ONLINE")
    private String pOnline;
    @Basic
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    @Basic
    @Column(name = "DIRECT_AGENT")
    private String directAgent;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpOnline() {
        return pOnline;
    }

    public void setpOnline(String pOnline) {
        this.pOnline = pOnline;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getDirectAgent() {
        return directAgent;
    }

    public void setDirectAgent(String directAgent) {
        this.directAgent = directAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantsEntity that = (ParticipantsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(bic, that.bic) && Objects.equals(pType, that.pType) && Objects.equals(validFrom, that.validFrom) && Objects.equals(validTo, that.validTo) && Objects.equals(pStatus, that.pStatus) && Objects.equals(pOnline, that.pOnline) && Objects.equals(createdAt, that.createdAt) && Objects.equals(directAgent, that.directAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bic, pType, validFrom, validTo, pStatus, pOnline, createdAt, directAgent);
    }
}
