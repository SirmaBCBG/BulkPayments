package com.sirmabc.bulkpayments.communicators;

import com.sirmabc.bulkpayments.services.BorikaMessageService;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Scope("singleton")
public class BorikaClientScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BorikaClientScheduler.class);

    private static final int FILE_BATCH = 10;

    @Autowired
    private BorikaClient borikaClient;

    @Autowired
    private BorikaMessageService borikaMessageService;

    @Autowired
    private Properties properties;

    @Scheduled(fixedDelay = 60000)
    public void getMessage() {
        logger.info("getMessage()...");

        try {
            // Build http client
            HttpClient client = borikaClient.getHttpClient();
            // Build GET request
            HttpRequest request = borikaClient.buildGETRequest();

            logger.debug("getMessage(): GET request headers: " + request.headers().toString());

            // Send the GET request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("getMessage(): GET response headers: " + response.headers());
            logger.debug("getMessage(): GET response: " + response.body());

            // Process the incoming message
            borikaMessageService.asyncProcessIncomingMessage(response);

        } catch (Exception e) {
            logger.error("getMessage(): Exception: " + e.getMessage(), e);
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void sendMessages() {
        logger.info("sendMessage()...");

        // Get all xml files from the directory
        File[] files = FileHelper.getFilesFromPath(properties.getOutgngBulkMsgsPath(), ".xml");

        // Process the files simultaneously
        for (int i = 0; i < Math.min(FILE_BATCH, files.length); i++) {
            try {
                borikaMessageService.asyncProcessOutgoingMessage(files[i]);
            } catch (Exception e) {
                logger.error("sendMessage(): Exception: " + e.getMessage(), e);
            }
        }
    }

    @Scheduled(cron = "0 1 00 * * ?")
    public void getParticipantsMessage() {
        logger.info("getParticipantsMessage()...");

        try {
            // Build http client
            HttpClient client = borikaClient.buildClient(5);
            // Build GET participants request
            HttpRequest request = borikaClient.buildGETParticipantsRequest();

            // Send the GET request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("getParticipantsMessage(): GET response: " + response.body());

            // Process the incoming message
            borikaMessageService.asyncProcessParticipantsMessage(response);

        } catch (Exception e) {
            logger.error("getParticipantsMessage(): Exception: " + e.getMessage(), e);
        }
    }

    public String getParticipantsMessageResource() {
        logger.info("getParticipantsMessageResource()...");

        try {
            // Build http client
            HttpClient client = borikaClient.buildClient(5);
            // Build GET participants request
            HttpRequest request = borikaClient.buildGETParticipantsRequest();

            // Send the GET request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("getParticipantsMessageResource(): GET response: " + response.body());

            // Process the incoming message
            borikaMessageService.asyncProcessParticipantsMessage(response);

            return response.body();
        } catch (Exception e) {
            logger.error("getParticipantsMessageResource(): Exception: " + e.getMessage(), e);
            return e.getMessage();
        }
    }
}
