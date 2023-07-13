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
public class AccountValidationClient {

    private static final Logger logger = LoggerFactory.getLogger(AccountValidationClient.class);

    @Autowired
    Properties properties;

    public HttpResponse<String> postMessage(String msgId, String requestBody) throws IOException, InterruptedException {
        logger.info("Posting message to Borika...");

        HttpClient client = buildClient(20);

        HttpRequest request = buildPostRequest(msgId, requestBody);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpClient buildClient(int connectionTimeout) {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    public HttpRequest buildPostRequest(String msgId, String requestBody) {
        return   HttpRequest.newBuilder()
                .uri(URI.create(properties.getAccValidUrl()))
                .header(BRANCH.header, properties.getAccValidBranch())
                .header(USERID.header, properties.getAccUserId())
                .header(MSGID.header,msgId)
                .header(ENTITY.header, properties.getAccValidEntity())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

}
