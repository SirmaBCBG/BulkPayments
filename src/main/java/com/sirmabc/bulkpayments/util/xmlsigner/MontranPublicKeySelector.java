package com.sirmabc.bulkpayments.util.xmlsigner;

import com.sirmabc.bulkpayments.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@Component
public class MontranPublicKeySelector extends KeySelector {

    @Autowired
    private Properties properties;

    public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context)
          throws KeySelectorException {

      try {
        // todo: implement fallback certs
        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(new FileInputStream(properties.getSignBoricaKeyStorePath()), properties.getSignBoricaKeyStorePassword().toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate(properties.getSignBoricaCertAlias());


        final PublicKey key = cert.getPublicKey();

        return new KeySelectorResult() {
          public Key getKey() { return key; }
        };
      } catch (Exception e) {
        throw new KeySelectorException(e.getMessage());
      }

    }
}
