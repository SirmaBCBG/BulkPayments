package com.sirmabc.bulkpayments.communicators;

import com.sirmabc.bulkpayments.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.sirmabc.bulkpayments.util.Header.*;

@Component
public class BorikaClient {

    private static final Logger logger = LoggerFactory.getLogger(BorikaClient.class);

    @Autowired
    Properties properties;

    public HttpResponse<String> postMessage(String requestBody) throws IOException, InterruptedException {
        logger.info("Posting message to Borika");

        HttpClient client = buildClient(20);
        HttpRequest request = buildPostRequest(requestBody);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Post request response: " + response.body());

        return response;
    }

    public void postAcknowledge(String msgSeq) throws IOException, InterruptedException {
        logger.info("Posting acknowledge message to Borika");

        HttpClient client = buildClient(20);
        HttpRequest request = buildPostRequest("", msgSeq);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Post request response: " + response.body());
    }

    public HttpClient buildClient(int connectionTimeout) {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    public HttpRequest buildGETRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/bulk/Message"))
                .header(X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .GET()
                .build();
    }

    /*public HttpRequest buildGETParticipantsRequest () {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/api/participants"))
                .header(X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .GET()
                .build();
    }*/

    public HttpRequest buildPostRequest(String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/bulk/Message"))
                .header(X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public HttpRequest buildPostRequest(String requestBody, String msgSeq) {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/bulk/Message"))
                .header(X_MONTRAN_RTP_CHANNEL.header, properties.getRtpVersion())
                .header(X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .header(X_MONTRAN_RTP_MESSAGE_SEQ.header, msgSeq)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }
}
