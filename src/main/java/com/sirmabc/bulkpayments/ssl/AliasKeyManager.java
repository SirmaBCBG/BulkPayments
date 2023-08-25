package com.sirmabc.bulkpayments.ssl;

import com.sirmabc.bulkpayments.util.Properties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Component
public class AliasKeyManager extends X509ExtendedKeyManager {

    private static final Logger logger = LoggerFactory.getLogger(CustomSSL.class);
    private KeyStore keystore;
    private String alias;
    private String pivateKeyPassword;

    @Autowired
    Properties properties;


    @PostConstruct
    public void init() {
        logger.debug("Init ...");

        try {

            FileInputStream keyStoreIS = new FileInputStream(properties.getSslSBCKeyStorePath());

            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(keyStoreIS, properties.getSslSBCKeyStorePassword().toCharArray());

            this.keystore = keystore;
            this.alias = properties.getSslSBCKeyStoreAlias();
            this.pivateKeyPassword = properties.getSslSBCPrivateKeyPassword();
        }catch (Exception e) {

            logger.error(e.getMessage());
        }
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
//        logger.debug("getCertificateChain(....) alias = " + alias + "; keystore = " + keystore );

        X509Certificate[] certsX509 = null;
        try {
            java.security.cert.Certificate[] certs =  keystore.getCertificateChain(alias);
            if (certs == null) {
                X509Certificate cert = (X509Certificate) keystore.getCertificate(alias);

                certsX509 = new X509Certificate[1];
                certsX509[0] = cert;
            } else {
                certsX509 = new X509Certificate[certs.length];

                for (int i=0;i<certs.length;i++) {
                    certsX509[i] = (X509Certificate)certs[i];
                }
            }



        } catch (KeyStoreException e) {
//            logger.debug("getCertificateChain(....) nothing!");
            logger.error(e.getMessage(), e);
        }

//        logger.debug("getCertificateChain(....) chain size *** = " + certsX509.length );

        return certsX509;
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
//        logger.debug("getPrivateKey(....) alias = " + alias );

        PrivateKey pk = null;
        try {
            pk = (PrivateKey) keystore.getKey(alias, pivateKeyPassword.toCharArray());

//            logger.debug("getPrivateKey(....) pk = " + pk );
        } catch (Exception e) {
//            logger.debug("getPrivateKey(....) nothing!");
            logger.error(e.getMessage(), e);
        }
        return pk;
    }

    @Override
    public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
        return alias;
    }

    @Override
    public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
        return alias;
    }

    @Override
    public String[] getClientAliases(String arg0, Principal[] arg1) {
//        logger.debug("getClientAliases(....) arg0 = " + arg0 );
        return new String[] { alias };
    }

    @Override
    public String[] getServerAliases(String arg0, Principal[] arg1) {
//        logger.debug("getServerAliases(....) arg0 = " + arg0 );
        return new String[] { alias };
    }

    public String chooseEngineClientAlias(String[] keyType,
                                          Principal[] issuers, SSLEngine engine) {
//        logger.debug("chooseEngineClientAlias(....) keyType = " + keyType );

        return alias;
    }
}
