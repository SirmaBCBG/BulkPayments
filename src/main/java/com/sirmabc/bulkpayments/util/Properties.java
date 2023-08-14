package com.sirmabc.bulkpayments.util;

import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.persistance.entities.PropertiesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.PropertiesRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Properties {
    private PropertiesEntity borikaUrl;

    private PropertiesEntity accValidUrl;

    private PropertiesEntity accValidBranch;

    private PropertiesEntity accUserId;

    private PropertiesEntity accValidMsgId;

    private PropertiesEntity accValidEntity;

    private PropertiesEntity rtpChannel;

    private PropertiesEntity rtpVersion;

    private PropertiesEntity fcUrl;

    private PropertiesEntity bizMsgIdr;

    private PropertiesEntity boricaKeyStorePath;

    private PropertiesEntity boricaKeyStorePassword;

    private PropertiesEntity boricaKeyStoreAlias;

    private PropertiesEntity boricaKeyPassword;

    private PropertiesEntity sbcKeyStorePath;

    private PropertiesEntity sbcKeyStorePassword;

    private PropertiesEntity sbcKeyStoreAlias;

    private PropertiesEntity sbcKeyPassword;

    private PropertiesEntity outgngBulkMsgsPath;

    private PropertiesEntity outgngBulkMsgsInProgressPath;

    private PropertiesEntity outgngBulkMsgsProcessedPath;

    private PropertiesEntity incmgBulkMsgsPath;

    private final PropertiesRepository repository;

    @Autowired
    public Properties(PropertiesRepository repository){
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(Properties.class);

    @PostConstruct
    public void postConstruct () throws AppException {
        logger.info("Getting properties from the database");

        try {
            borikaUrl = repository.findByName("borikaUrl");
            logger.info("Borica url: " + borikaUrl.getValue());

            accValidUrl = repository.findByName("accValidUrl");
            logger.info("Account validation service url: " + accValidUrl.getValue());

            accValidBranch = repository.findByName("accValidBranch");
            logger.info("Account validation service branch: " + accValidBranch.getValue());

            accUserId = repository.findByName("accUserId");
            logger.info("Account validation service userId: " + accUserId.getValue());

            // accValidMsgId = repository.findByName("accValidMsgId");
            // logger.info("Account validation service url: " + accValidMsgId.getValue());

            accValidEntity = repository.findByName("accValidEntity");
            logger.info("Account validation service entity: " + accValidEntity.getValue());

            rtpChannel = repository.findByName("rtpChannel");
            logger.info("Borica rtp channel (BIC) of institution: " + rtpChannel.getValue());

            rtpVersion = repository.findByName("rtpVersion");
            logger.info("Borica rtp version: " + rtpVersion.getValue());

            fcUrl = repository.findByName("fcUrl");
            logger.info("Flex cube service url: " + fcUrl.getValue());

            bizMsgIdr = repository.findByName("bizMsgIdr");
            logger.info("Flex cube bizMsgIdr url: " + bizMsgIdr.getValue());

            boricaKeyStorePath = repository.findByName("boricaKeyStorePath");
            logger.info("JKS borica keystore file path: " + boricaKeyStorePath.getValue());

            boricaKeyStorePassword = repository.findByName("boricaKeyStorePassword");
            logger.info("JKS borica password: " + boricaKeyStorePassword.getValue());

            boricaKeyStoreAlias = repository.findByName("boricaKeyStoreAlias");
            logger.info("JKS borica alias: " + boricaKeyStoreAlias.getValue());

            boricaKeyPassword = repository.findByName("boricaKeyPassword");
            logger.info("JKS borica key password: " + boricaKeyPassword.getValue());

            sbcKeyStorePath = repository.findByName("sbcKeyStorePath");
            logger.info("JKS sbc keystore file path: " + sbcKeyStorePath.getValue());

            sbcKeyStorePassword = repository.findByName("sbcKeyStorePassword");
            logger.info("JKS sbc password: " + sbcKeyStorePassword.getValue());

            sbcKeyStoreAlias = repository.findByName("sbcKeyStoreAlias");
            logger.info("JKS sbc alias: " + sbcKeyStoreAlias.getValue());

            sbcKeyPassword = repository.findByName("sbcKeyPassword");
            logger.info("JKS sbc key password: " + sbcKeyPassword.getValue());

            outgngBulkMsgsPath = repository.findByName("outgngBulkMsgsPath");
            logger.info("JKS sbc key password: " + outgngBulkMsgsPath.getValue());

            outgngBulkMsgsInProgressPath = repository.findByName("outgngBulkMsgsInProgressPath");
            logger.info("Outgoing bulk messages in progress directory path: " + outgngBulkMsgsInProgressPath.getValue());

            outgngBulkMsgsProcessedPath = repository.findByName("outgngBulkMsgsProcessedPath");
            logger.info("Outgoing processed bulk messages directory path: " + outgngBulkMsgsProcessedPath.getValue());

            incmgBulkMsgsPath = repository.findByName("incmgBulkMsgsPath");
            logger.info("Outgoing processed bulk messages directory path: " + incmgBulkMsgsPath.getValue());
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public String getBorikaUrl() {
        // TODO: Uncomment
        //return borikaUrl.getValue();
        return "http://172.16.51.196:8888/certservice/api/borica";
    }

    public void setBorikaUrl(PropertiesEntity borikaUrl) {
        this.borikaUrl = borikaUrl;
    }

    public String getAccValidUrl() {
        return accValidUrl.getValue();
    }

    public void setAccValidUrl(PropertiesEntity accValidUrl) {
        this.accValidUrl = accValidUrl;
    }

    public String getAccValidBranch() {
        return accValidBranch.getValue();
    }

    public void setAccValidBranch(PropertiesEntity accValidBranch) {
        this.accValidBranch = accValidBranch;
    }

    public String getAccUserId() {
        return accUserId.getValue();
    }

    public void setAccUserId(PropertiesEntity accUserId) {
        this.accUserId = accUserId;
    }

    public String getAccValidMsgId() {
        return accValidMsgId.getValue();
    }

    public void setAccValidMsgId(PropertiesEntity accValidMsgId) {
        this.accValidMsgId = accValidMsgId;
    }

    public String getAccValidEntity() {
        return accValidEntity.getValue();
    }

    public void setAccValidEntity(PropertiesEntity accValidEntity) {
        this.accValidEntity = accValidEntity;
    }

    public String getRtpChannel() {
        return rtpChannel.getValue();
    }

    public void setRtpChannel(PropertiesEntity rtpChannel) {
        this.rtpChannel = rtpChannel;
    }

    public String getRtpVersion() {
        return rtpVersion.getValue();
    }

    public void setRtpVersion(PropertiesEntity rtpVersion) {
        this.rtpVersion = rtpVersion;
    }

    public String getFcUrl() {
        return fcUrl.getValue();
    }

    public void setFcUrl(PropertiesEntity fcUrl) {
        this.fcUrl = fcUrl;
    }

    public String getBizMsgIdr() {
        return bizMsgIdr.getValue();
    }

    public void setBizMsgIdr(PropertiesEntity bizMsgIdr) {
        this.bizMsgIdr = bizMsgIdr;
    }

    public String getBoricaKeyStorePath() {
        return boricaKeyStorePath.getValue();
    }

    public void setBoricaKeyStorePath(PropertiesEntity boricaKeyStorePath) {
        this.boricaKeyStorePath = boricaKeyStorePath;
    }

    public String getBoricaKeyStorePassword() {
        return boricaKeyStorePassword.getValue();
    }

    public void setBoricaKeyStorePassword(PropertiesEntity boricaKeyStorePassword) {
        this.boricaKeyStorePassword = boricaKeyStorePassword;
    }

    public String getBoricaKeyStoreAlias() {
        return boricaKeyStoreAlias.getValue();
    }

    public void setBoricaKeyStoreAlias(PropertiesEntity boricaKeyStoreAlias) {
        this.boricaKeyStoreAlias = boricaKeyStoreAlias;
    }

    public String getBoricaKeyPassword() {
        return boricaKeyPassword.getValue();
    }

    public void setBoricaKeyPassword(PropertiesEntity boricaKeyPassword) {
        this.boricaKeyPassword = boricaKeyPassword;
    }

    public String getSbcKeyStorePath() {
        return sbcKeyStorePath.getValue();
    }

    public void setSbcKeyStorePath(PropertiesEntity sbcKeyStorePath) {
        this.sbcKeyStorePath = sbcKeyStorePath;
    }

    public String getSbcKeyStorePassword() {
        return sbcKeyStorePassword.getValue();
    }

    public void setSbcKeyStorePassword(PropertiesEntity sbcKeyStorePassword) {
        this.sbcKeyStorePassword = sbcKeyStorePassword;
    }

    public String getSbcKeyStoreAlias() {
        return sbcKeyStoreAlias.getValue();
    }

    public void setSbcKeyStoreAlias(PropertiesEntity sbcKeyStoreAlias) {
        this.sbcKeyStoreAlias = sbcKeyStoreAlias;
    }

    public String getSbcKeyPassword() {
        return sbcKeyPassword.getValue();
    }

    public void setSbcKeyPassword(PropertiesEntity sbcKeyPassword) {
        this.sbcKeyPassword = sbcKeyPassword;
    }

    public String getOutgngBulkMsgsPath() {
        return outgngBulkMsgsPath.getValue();
    }

    public void setOutgngBulkMsgsPath(PropertiesEntity outgngBulkMsgsPath) {
        this.outgngBulkMsgsPath = outgngBulkMsgsPath;
    }

    public String getOutgngBulkMsgsInProgressPath() {
        return outgngBulkMsgsInProgressPath.getValue();
    }

    public void setOutgngBulkMsgsInProgressPath(PropertiesEntity outgngBulkMsgsInProgressPath) {
        this.outgngBulkMsgsInProgressPath = outgngBulkMsgsInProgressPath;
    }

    public String getOutgngBulkMsgsProcessedPath() {
        return outgngBulkMsgsProcessedPath.getValue();
    }

    public void setOutgngBulkMsgsProcessedPath(PropertiesEntity outgngBulkMsgsProcessedPath) {
        this.outgngBulkMsgsProcessedPath = outgngBulkMsgsProcessedPath;
    }

    public String getIncmgBulkMsgsPath() {
        return incmgBulkMsgsPath.getValue();
    }

    public void setIncmgBulkMsgsPath(PropertiesEntity incmgBulkMsgsPath) {
        this.incmgBulkMsgsPath = incmgBulkMsgsPath;
    }
}
