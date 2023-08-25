package com.sirmabc.bulkpayments.ssl.test;

import javax.net.ssl.X509KeyManager;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class CustomKeyManager implements X509KeyManager {
    private KeyStore keyStore;
    private String alias;

    private String pkPassword;

    public CustomKeyManager(KeyStore keyStore, String alias, String pkPassword) {
        this.keyStore = keyStore;
        this.alias = alias;
        this.pkPassword = pkPassword;
    }
    public String[] getClientAliases(String var1, Principal[] var2) {
        System.out.println("getClientAliases() " + var1);

        return new String[] {alias};
    }



    public String[] getServerAliases(String var1, Principal[] var2) {
        System.out.println("getServerAliases() " + var1);

        return new String[] {alias};
    }

    public String chooseServerAlias(String var1, Principal[] var2, Socket var3) {
        System.out.println("chooseServerAlias() " + var1);

        return alias;
    }

    public String chooseClientAlias(String[] var1, Principal[] var2, Socket var3) {
        System.out.println("chooseClientAlias() " + var1);

        return alias;
    }

    public X509Certificate[] getCertificateChain(String var1) {
        System.out.println("getCertificateChain() " + var1);

        try {
            Certificate[] certs = keyStore.getCertificateChain(alias);

            X509Certificate[] x509Certs = new X509Certificate[certs.length];
            int i = 0;
            for (Certificate cert : certs) {
                x509Certs[i] = (X509Certificate) cert;
                i++;
            }

            System.out.println("getCertificateChain()--->" + Arrays.toString(x509Certs));

            return x509Certs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrivateKey getPrivateKey(String var1) {
        System.out.println("getPrivateKey() " + var1);

        try {
            //return null;
            PrivateKey pKey = (PrivateKey)keyStore.getKey(alias, pkPassword.toCharArray());
            System.out.println("getPrivateKey() " + pKey.toString());
            return pKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}