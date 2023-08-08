package com.sirmabc.bulkpayments.util;

import com.sirmabc.bulkpayments.exceptions.AppException;
import com.sirmabc.bulkpayments.persistance.entities.PropertiesEntity;
import com.sirmabc.bulkpayments.persistance.repositories.PropertiesRepository;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

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

    private PropertiesEntity keyStorePath;

    private PropertiesEntity keyStorePassword;

    private PropertiesEntity keyStoreAlias;

    private PropertiesEntity incmgBulkMsgsDirPath;

    private PropertiesEntity outgngBulkMsgsDirPath;

    private PropertiesEntity outgngBulkMsgsInProgressDirPath;

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

            keyStorePath = repository.findByName("keyStorePath");
            logger.info("JKS file path: " + keyStorePath.getValue());

            keyStorePassword = repository.findByName("keyStorePassword");
            logger.info("JKS password: " + keyStorePassword.getValue());

            keyStoreAlias = repository.findByName("keyStoreAlias");
            logger.info("JKS alias: " + keyStoreAlias.getValue());

            incmgBulkMsgsDirPath = repository.findByName("incmgBulkMsgsDirPath");
            logger.info("Incoming bulk messages directory path: " + incmgBulkMsgsDirPath.getValue());

            outgngBulkMsgsDirPath = repository.findByName("outgngBulkMsgsDirPath");
            logger.info("Outgoing bulk messages directory path: " + outgngBulkMsgsDirPath.getValue());

            outgngBulkMsgsInProgressDirPath = repository.findByName("outgngBulkMsgsInProgressDirPath");
            logger.info("Outgoing bulk messages in progress directory path: " + outgngBulkMsgsInProgressDirPath.getValue());
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

    public String getKeyStorePath() {
        return keyStorePath.getValue();
    }

    public void setKeyStorePath(PropertiesEntity keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    public String getKeyStorePassword() {
        return keyStorePassword.getValue();
    }

    public void setKeyStorePassword(PropertiesEntity keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreAlias() {
        return keyStoreAlias.getValue();
    }

    public void setKeyStoreAlias(PropertiesEntity keyStoreAlias) {
        this.keyStoreAlias = keyStoreAlias;
    }

    public String getIncmgBulkMsgsDirPath() {
        return incmgBulkMsgsDirPath.getValue();
    }

    public void setIncmgBulkMsgsDirPath(PropertiesEntity incmgBulkMsgsDirPath) {
        this.incmgBulkMsgsDirPath = incmgBulkMsgsDirPath;
    }

    public String getOutgngBulkMsgsDirPath() {
        return outgngBulkMsgsDirPath.getValue();
    }

    public void setOutgngBulkMsgsDirPath(PropertiesEntity outgngBulkMsgsDirPath) {
        this.outgngBulkMsgsDirPath = outgngBulkMsgsDirPath;
    }

    public String getOutgngBulkMsgsInProgressDirPath() {
        return outgngBulkMsgsInProgressDirPath.getValue();
    }

    public void setOutgngBulkMsgsInProgressDirPath(PropertiesEntity outgngBulkMsgsInProgressDirPath) {
        this.outgngBulkMsgsInProgressDirPath = outgngBulkMsgsInProgressDirPath;
    }

    /* TODO: Decide if there are going to be different PropertiesEntity fields for all outgoing messages directories
    *   or a single PropertiesEntity field for a directory that contains all outgoing messages directories!
    *   Currently the latter is implemented. */
    public String[] getAllOutgngBulkMsgsDirPaths() {
        File[] outgngBulkMsgsDir = FileHelper.getFilesFromPath(outgngBulkMsgsDirPath.getValue(), null);
        String[] dirPaths = new String[outgngBulkMsgsDir.length];

        for (int i = 0; i < outgngBulkMsgsDir.length; i++) dirPaths[i] = outgngBulkMsgsDir[i].getPath();

        return dirPaths;
    }
}
