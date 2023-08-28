package com.sirmabc.bulkpayments.ssl;

import com.sirmabc.bulkpayments.util.Properties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Component
public class SirmaTrustManager extends X509ExtendedTrustManager {

    private static final Logger logger = LoggerFactory.getLogger(SirmaTrustManager.class);

    X509Certificate[] trustedCert;

    @Autowired
    Properties properties;

    @PostConstruct
    public void init() {
        logger.debug("Init ...");

        try {
            FileInputStream keyStoreIS = new FileInputStream(properties.getSslBoricaKeyStorePath());

            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(keyStoreIS, properties.getSslBoricaKeyStorePassword().toCharArray());

            String cert = properties.getSslBoricaCertificate();

            java.security.cert.Certificate[] certs =  keystore.getCertificateChain(cert);
            if (certs == null) {
                X509Certificate certTemp = (X509Certificate) keystore.getCertificate(cert);

                trustedCert = new X509Certificate[1];
                trustedCert[0] = certTemp;
            } else {
                trustedCert = new X509Certificate[certs.length];

                for (int i=0;i<certs.length;i++) {
                    trustedCert[i] = (X509Certificate)certs[i];
                }
            }

        }catch (Exception e) {
            logger.error(e.getMessage());

        }
    }

    public void checkClientTrusted(X509Certificate[] cert, String authType)
            throws CertificateException {
        logger.debug("checkClientTrusted(...)");
    }

    /**
     * Doesn't throw an exception, so this is how it approves a certificate.
     *
     * @see X509TrustManager#checkServerTrusted(X509Certificate[], String)
     */
    public void checkServerTrusted(X509Certificate[] cert, String authType)
            throws CertificateException {
        logger.debug("checkServerTrusted(...) " + cert[0].toString());

        if(!cert[0].equals(trustedCert[0])) {
            throw new CertificateException("Server certificate is not trusted!");
        }

    }

    /**
     * @see X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        logger.debug("getAcceptedIssuers(...)");

        return null;  // I've seen someone return new X509Certificate[ 0 ];
    }





    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

    }
}
