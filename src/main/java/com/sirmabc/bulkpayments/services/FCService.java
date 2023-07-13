package com.sirmabc.bulkpayments.services;

import com.sirmabc.bulkpayments.communicators.FcClient;
import com.sirmabc.bulkpayments.data.FcRequestApprovedPacs008;
import com.sirmabc.bulkpayments.data.FcResponse;
import com.sirmabc.bulkpayments.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

import static java.lang.Thread.sleep;

@Service
public class FCService {

    private final FcClient fcClient;

    private static final Logger logger = LoggerFactory.getLogger(FCService.class);

    @Autowired
    public FCService(FcClient fcClient) {
        this.fcClient = fcClient;
    }

    public FcResponse sendApprovedPacs008(FcRequestApprovedPacs008 fcRequestApprovedPacs008) throws Exception {
        logger.info("sendApprovedPacs008...");

        String requstBody = Util.objectToJson(fcRequestApprovedPacs008);
        HttpResponse<String> response = fcClient.postMessage(requstBody);

        String responseS = response.body();

        logger.debug("Integration layer returned: " + responseS);

        if (responseS.contains("fcubsErrorResp")) {
            sleep(30000);

            logger.info("Trying to send again message with ID: " + fcRequestApprovedPacs008.msgId());
            sendApprovedPacs008(fcRequestApprovedPacs008);

        }
        //TODO: Remove hack
        FcResponse fcResponse = new FcResponse();//Parser.deserializeXml(response.body(), FcResponse.class);

        logger.info("Successfully sending the message with ID - " + fcRequestApprovedPacs008.msgId() + "to Flex Cube!");

        return fcResponse;
    }

}

