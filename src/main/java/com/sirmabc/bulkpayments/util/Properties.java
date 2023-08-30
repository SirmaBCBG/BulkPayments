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

    private PropertiesEntity borikaBic;

    private PropertiesEntity accValidUrl;

    private PropertiesEntity accValidBranch;

    private PropertiesEntity accUserId;

    private PropertiesEntity accValidMsgId;

    private PropertiesEntity accValidEntity;

    private PropertiesEntity rtpChannel;

    private PropertiesEntity rtpVersion;

    private PropertiesEntity fcUrl;

    private PropertiesEntity bizMsgIdr;

    private PropertiesEntity signBoricaKeyStorePath;

    private PropertiesEntity signBoricaKeyStorePassword;

    private PropertiesEntity signBoricaCertAlias;

    private PropertiesEntity signSBCKeyStorePath;

    private PropertiesEntity signSBCKeyStorePassword;

    private PropertiesEntity signSBCKeyStoreAlias;

    private PropertiesEntity signSBCPrivateKeyPassword;

    private PropertiesEntity outgngBulkMsgsPath;

    private PropertiesEntity outgngBulkMsgsInProgressPath;

    private PropertiesEntity outgngBulkMsgsProcessedPath;

    private PropertiesEntity incmgBulkMsgsPath;

    private PropertiesEntity sslSBCKeyStorePath;

    private PropertiesEntity sslSBCKeyStorePassword;

    private PropertiesEntity sslSBCKeyStoreAlias;

    private PropertiesEntity sslSBCPrivateKeyPassword;

    private PropertiesEntity sslBoricaKeyStorePath;

    private PropertiesEntity sslBoricaKeyStorePassword;

    private PropertiesEntity sslBoricaCertificate;

    private final PropertiesRepository repository;

    @Autowired
    public Properties(PropertiesRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(Properties.class);

    @PostConstruct
    public void postConstruct() throws AppException {
        logger.info("Getting properties from the database");

        try {
            borikaUrl = repository.findByName("borikaUrl");
            logger.info("Borica url: " + borikaUrl.getValue());

            borikaBic = repository.findByName("borikaBic");
            logger.info("Borica BIC: " + borikaBic.getValue());

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

            signBoricaKeyStorePath = repository.findByName("signBoricaKeyStorePath");
            logger.info("JKS signing borica keystore file path: " + signBoricaKeyStorePath.getValue());

            signBoricaKeyStorePassword = repository.findByName("signBoricaKeyStorePassword");
            logger.info("JKS signing borica password: " + signBoricaKeyStorePassword.getValue());

            signBoricaCertAlias = repository.findByName("signBoricaCertAlias");
            logger.info("JKS sign borica cert alias: " + signBoricaCertAlias.getValue());

            signSBCKeyStorePath = repository.findByName("signSBCKeyStorePath");
            logger.info("JKS sbc signing keystore file path: " + signSBCKeyStorePath.getValue());

            signSBCKeyStorePassword = repository.findByName("signSBCKeyStorePassword");
            logger.info("JKS sbc signing password: " + signSBCKeyStorePassword.getValue());

            signSBCKeyStoreAlias = repository.findByName("signSBCKeyStoreAlias");
            logger.info("JKS sbc signing alias: " + signSBCKeyStoreAlias.getValue());

            signSBCPrivateKeyPassword = repository.findByName("signSBCPrivateKeyPassword");
            logger.info("JKS sbc signing private key password: " + signSBCPrivateKeyPassword.getValue());

            outgngBulkMsgsPath = repository.findByName("outgngBulkMsgsPath");
            logger.info("Outgoing bulk messages directory path: " + outgngBulkMsgsPath.getValue());

            outgngBulkMsgsInProgressPath = repository.findByName("outgngBulkMsgsInProgressPath");
            logger.info("Outgoing bulk messages in progress directory path: " + outgngBulkMsgsInProgressPath.getValue());

            outgngBulkMsgsProcessedPath = repository.findByName("outgngBulkMsgsProcessedPath");
            logger.info("Outgoing processed bulk messages directory path: " + outgngBulkMsgsProcessedPath.getValue());

            incmgBulkMsgsPath = repository.findByName("incmgBulkMsgsPath");
            logger.info("Incoming bulk messages directory path: " + incmgBulkMsgsPath.getValue());

            sslSBCKeyStorePath = repository.findByName("sslSBCKeyStorePath");
            logger.info("JKS sbc SSL key store path: " + sslSBCKeyStorePath.getValue());

            sslSBCKeyStorePassword = repository.findByName("sslSBCKeyStorePassword");
            logger.info("JKS sbc SSL key store password: " + sslSBCKeyStorePassword.getValue());

            sslSBCKeyStoreAlias = repository.findByName("sslSBCKeyStoreAlias");
            logger.info("JKS sbc SSL key store alias: " + sslSBCKeyStoreAlias.getValue());

            sslSBCPrivateKeyPassword = repository.findByName("sslSBCPrivateKeyPassword");
            logger.info("JKS sbc SSL private key store password: " + sslSBCPrivateKeyPassword.getValue());

            sslBoricaKeyStorePath = repository.findByName("sslBoricaKeyStorePath");
            logger.info("JKS Borica SSL key store path: " + sslBoricaKeyStorePath.getValue());

            sslBoricaCertificate = repository.findByName("sslBoricaCertificate");
            logger.info("JKS Borica SSL key store cert: " + sslBoricaCertificate.getValue());

            sslBoricaKeyStorePassword = repository.findByName("sslBoricaKeyStorePassword");
            logger.info("JKS Borica SSL key store password: " + sslBoricaKeyStorePassword.getValue());
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public String getBorikaUrl() {
        return borikaUrl.getValue();
    }

    public void setBorikaUrl(PropertiesEntity borikaUrl) {
        this.borikaUrl = borikaUrl;
    }

    public String getBorikaBic() {
        return borikaBic.getValue();
    }

    public void setBorikaBic(PropertiesEntity borikaBic) {
        this.borikaBic = borikaBic;
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

    public String getSignBoricaKeyStorePath() {
        return signBoricaKeyStorePath.getValue();
    }

    public void setSignBoricaKeyStorePath(PropertiesEntity signBoricaKeyStorePath) {
        this.signBoricaKeyStorePath = signBoricaKeyStorePath;
    }

    public String getSignBoricaKeyStorePassword() {
        return signBoricaKeyStorePassword.getValue();
    }

    public void setSignBoricaKeyStorePassword(PropertiesEntity signBoricaKeyStorePassword) {
        this.signBoricaKeyStorePassword = signBoricaKeyStorePassword;
    }

    public String getSignBoricaCertAlias() {
        return signBoricaCertAlias.getValue();
    }

    public void setSignBoricaCertAlias(PropertiesEntity signBoricaCertAlias) {
        this.signBoricaCertAlias = signBoricaCertAlias;
    }

    public String getSignSBCKeyStorePath() {
        return signSBCKeyStorePath.getValue();
    }

    public void setSignSBCKeyStorePath(PropertiesEntity signSBCKeyStorePath) {
        this.signSBCKeyStorePath = signSBCKeyStorePath;
    }

    public String getSignSBCKeyStorePassword() {
        return signSBCKeyStorePassword.getValue();
    }

    public void setSignSBCKeyStorePassword(PropertiesEntity signSBCKeyStorePassword) {
        this.signSBCKeyStorePassword = signSBCKeyStorePassword;
    }

    public String getSignSBCKeyStoreAlias() {
        return signSBCKeyStoreAlias.getValue();
    }

    public void setSignSBCKeyStoreAlias(PropertiesEntity signSBCKeyStoreAlias) {
        this.signSBCKeyStoreAlias = signSBCKeyStoreAlias;
    }

    public String getSignSBCPrivateKeyPassword() {
        return signSBCPrivateKeyPassword.getValue();
    }

    public void setSignSBCPrivateKeyPassword(PropertiesEntity signSBCPrivateKeyPassword) {
        this.signSBCPrivateKeyPassword = signSBCPrivateKeyPassword;
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

    public String getSslSBCKeyStorePath() {
        return sslSBCKeyStorePath.getValue();
    }

    public void setSslSBCKeyStorePath(PropertiesEntity sslSBCKeyStorePath) {
        this.sslSBCKeyStorePath = sslSBCKeyStorePath;
    }

    public String getSslSBCKeyStorePassword() {
        return sslSBCKeyStorePassword.getValue();
    }

    public void setSslSBCKeyStorePassword(PropertiesEntity sslSBCKeyStorePassword) {
        this.sslSBCKeyStorePassword = sslSBCKeyStorePassword;
    }

    public String getSslSBCKeyStoreAlias() {
        return sslSBCKeyStoreAlias.getValue();
    }

    public void setSslSBCKeyStoreAlias(PropertiesEntity sslSBCKeyStoreAlias) {
        this.sslSBCKeyStoreAlias = sslSBCKeyStoreAlias;
    }

    public String getSslSBCPrivateKeyPassword() {
        return sslSBCPrivateKeyPassword.getValue();
    }

    public void setSslSBCPrivateKeyPassword(PropertiesEntity sslSBCPrivateKeyPassword) {
        this.sslSBCPrivateKeyPassword = sslSBCPrivateKeyPassword;
    }

    public String getSslBoricaKeyStorePath() {
        return sslBoricaKeyStorePath.getValue();
    }

    public void setSslBoricaKeyStorePath(PropertiesEntity sslBoricaKeyStorePath) {
        this.sslBoricaKeyStorePath = sslBoricaKeyStorePath;
    }

    public String getSslBoricaKeyStorePassword() {
        return sslBoricaKeyStorePassword.getValue();
    }

    public void setSslBoricaKeyStorePassword(PropertiesEntity sslBoricaKeyStorePassword) {
        this.sslBoricaKeyStorePassword = sslBoricaKeyStorePassword;
    }

    public String getSslBoricaCertificate() {
        return sslBoricaCertificate.getValue();
    }

    public void setSslBoricaCertificate(PropertiesEntity sslBoricaCertificate) {
        this.sslBoricaCertificate = sslBoricaCertificate;
    }
}
