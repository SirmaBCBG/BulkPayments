package com.sirmabc.bulkpayments.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SirmaTrustManager extends X509ExtendedTrustManager {

    private static final Logger logger = LoggerFactory.getLogger(SirmaTrustManager.class);

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
