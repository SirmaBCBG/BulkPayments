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

    private PropertiesEntity bulkMsgsDirPath;

    private final PropertiesRepository repository;

    @Autowired
    public Properties(PropertiesRepository repository){
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(Properties.class);

    @PostConstruct
    public void postConstruct () throws AppException {
        logger.info("Getting properties from db...");

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

            bulkMsgsDirPath = repository.findByName("bulkMsgsDirPath");
            logger.info("Bulk messages directory path: " + bulkMsgsDirPath.getValue());
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

    public String getBulkMsgsDirPath() {
        return bulkMsgsDirPath.getValue();
    }

    public void setBulkMsgsDirPath(PropertiesEntity bulkMsgsDirPath) {
        this.bulkMsgsDirPath = bulkMsgsDirPath;
    }
}
