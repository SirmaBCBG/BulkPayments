package com.sirmabc.bulkpayments.communicators;

import com.sirmabc.bulkpayments.services.BorikaMessageService;
import com.sirmabc.bulkpayments.util.Directory;
import com.sirmabc.bulkpayments.util.helpers.BulkMessageHelper;
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
import java.util.List;

@Service
@Scope("singleton")
public class BorikaClientScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BorikaClientScheduler.class);

    @Autowired
    private BorikaClient borikaClient;

    @Autowired
    private BorikaMessageService borikaMessageService;

    @Autowired
    private BulkMessageHelper bulkMessageHelper;

    @Scheduled(fixedDelay = 60000)
    public void getMessage() {
        logger.info("Getting message from Borika");

        try {
            HttpClient client = borikaClient.buildClient(5);
            HttpRequest request = borikaClient.buildGETRequest();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Get request response: " + response.body());

            borikaMessageService.asyncProcessIncomingMessage(response);
        } catch (Exception e) {
            logger.error("getMessage() error: " + e.getMessage(), e);
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void sendMessage() {
        logger.info("Sending message to Borika");

        try {
            List<Directory> directories = bulkMessageHelper.getAllOutgngBulkMsgsDirs();

            for (Directory dir : directories) {
                for (File file : dir.getFiles()) borikaMessageService.asyncProcessOutgoingMessage(file);
            }
        } catch (Exception e) {
            logger.error("sendMessage() error: " + e.getMessage(), e);
        }
    }

    /*@Scheduled(cron = "0 1 00 * * ?")
    public void getParticipantsMessage() {
        logger.info("Get participants message from Borika...");

        try {
            HttpClient client = borikaClient.buildClient(5);
            HttpRequest request = borikaClient.buildGETParticipantsRequest();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.debug("Borika server response: " + response.body());

            borikaMessageService.asyncStartProcessingParticipantsMessage(response);
        } catch (Exception e) {
            logger.error("getMessage error: " + e.getMessage(), e);
        }
    }*/
}
