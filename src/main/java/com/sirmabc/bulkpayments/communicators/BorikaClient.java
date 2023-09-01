package com.sirmabc.bulkpayments.communicators;

import com.sirmabc.bulkpayments.exceptions.PostMessageException;
import com.sirmabc.bulkpayments.ssl.CustomSSL;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.enums.Header;
import jakarta.annotation.PostConstruct;
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

@Component
public class BorikaClient {

    private static final Logger logger = LoggerFactory.getLogger(BorikaClient.class);

    @Autowired
    Properties properties;

    @Autowired
    CustomSSL customSSL;

    HttpClient httpClient;

    private final static int SOCKET_TIMEOUT = 8;
    private final static int READ_TIMEOUT = 8;
    private static final String REQUEST_CONTENT_TYPE = "text/xml; charset=utf-8";

    @PostConstruct
    public void init() {
        this.httpClient = HttpClient.newBuilder()
                .sslContext(customSSL.getSslContext())
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(SOCKET_TIMEOUT))
                .build();
    }

    public HttpResponse<String> postMessage(String requestBody) throws PostMessageException {
        logger.info("Posting message to Borika");

        try {

            java.util.Properties props = System.getProperties();
            props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

            HttpRequest request = buildPostRequest(requestBody);

            logger.info("Post message headers: " + request.headers());

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Post response: " + response.body());

            return response;
        } catch (Exception e) {
            throw new PostMessageException(e.getMessage(), e);
        }
    }

    public HttpResponse<String> postAcknowledge(String msgSeq) throws IOException, InterruptedException {
        logger.info("Posting acknowledge message to Borika");

        HttpRequest request = buildPostAcknowledgeRequest("", msgSeq);

        logger.info("Acknowledge headers: " + request.headers());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Acknowledge response: " + response.body());

        return response;
    }

    public HttpClient buildClient(int connectionTimeout) {
        return HttpClient.newBuilder()
                .sslContext(customSSL.getSslContext())
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    public HttpRequest buildGETRequest() {
        // TODO: Possibly add content type header
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/bulk/Message"))
                .header(Header.X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(Header.X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .timeout(Duration.ofSeconds(READ_TIMEOUT))
                .GET()
                .build();
    }

    public HttpRequest buildGETParticipantsRequest () {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/api/participants"))
                .header(Header.X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(Header.X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .header(Header.CONTENT_TYPE.header, REQUEST_CONTENT_TYPE)
                .timeout(Duration.ofSeconds(READ_TIMEOUT))
                .GET()
                .build();
    }

    public HttpRequest buildPostRequest(String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/bulk/Message"))
                .header(Header.X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(Header.X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .header(Header.CONTENT_TYPE.header, REQUEST_CONTENT_TYPE)
                .timeout(Duration.ofSeconds(READ_TIMEOUT))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public HttpRequest buildPostAcknowledgeRequest(String requestBody, String msgSeq) {
        return HttpRequest.newBuilder()
                .uri(URI.create(properties.getBorikaUrl() + "/bulk/MessageAck"))
                .header(Header.X_MONTRAN_RTP_CHANNEL.header, properties.getRtpChannel())
                .header(Header.X_MONTRAN_RTP_VERSION.header, properties.getRtpVersion())
                .header(Header.X_MONTRAN_RTP_MESSAGE_SEQ.header, msgSeq)
                .header(Header.CONTENT_TYPE.header, REQUEST_CONTENT_TYPE)
                .timeout(Duration.ofSeconds(READ_TIMEOUT))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
