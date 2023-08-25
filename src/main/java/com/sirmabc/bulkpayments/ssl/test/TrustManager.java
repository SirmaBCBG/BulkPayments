package com.sirmabc.bulkpayments.ssl.test;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
        System.out.println("checkClientTrusted " + var1);
    }

    public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {

        System.out.println("checkServerTrusted " + Arrays.toString(var1));

    }

    public X509Certificate[] getAcceptedIssuers() {
        System.out.println("getAcceptedIssuers() ");
        return null;
    }
}
