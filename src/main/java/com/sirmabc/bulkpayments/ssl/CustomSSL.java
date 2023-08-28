package com.sirmabc.bulkpayments.ssl;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

@Component
public class CustomSSL {

    private static final Logger logger = LoggerFactory.getLogger(CustomSSL.class);
    SSLContext sslContext;

    @Autowired
    AliasKeyManager aliasKeyManager;

    @Autowired
    SirmaTrustManager sirmaTrustManager;

    @PostConstruct
    public void init() {
        logger.info("Initiating SSl context...");
        try {

            KeyManager[] keyManagers = new KeyManager[] { aliasKeyManager };

            sslContext = SSLContext.getInstance("SSL"); // OR TLS
            sslContext.init(keyManagers, new TrustManager[]{sirmaTrustManager}, new SecureRandom());


        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public SSLContext getSslContext() {
        return sslContext;
    }
}
