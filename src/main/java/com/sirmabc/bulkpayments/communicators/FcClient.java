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

@Component
public class FcClient {

    private static final Logger logger = LoggerFactory.getLogger(FcClient.class);

    @Autowired
    private Properties properties;
    public HttpResponse<String> postMessage(String requestBody) throws IOException, InterruptedException {
        logger.info("Posting message to FC...");

        HttpClient client = buildClient(20);

        HttpRequest request = buildPostRequest(requestBody);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        response.statusCode();

        return response;

    }

    public HttpClient buildClient(int connectionTimeout) {

        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    public HttpRequest buildPostRequest(String requestBody) {
        return   HttpRequest.newBuilder()
                .header("Content-type", "application/json")
                .uri(URI.create(properties.getFcUrl() + "/api/v1/payments"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }
}
