package com.sirmabc.bulkpayments.util.xmlsigner;

import org.springframework.beans.factory.annotation.Value;
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

  @Value("${keystore.sign.path}")
  private String keyStorePath;

  @Value("${keystore.sign.password}")
  private String keyStorePassword;
    public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context)
          throws KeySelectorException {

      try {
        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(new FileInputStream(keyStorePath), keyStorePassword.toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate("sirmabcxml");//, new KeyStore.PasswordProtection("sla6945".toCharArray()));


        final PublicKey key = cert.getPublicKey();

        return new KeySelectorResult() {
          public Key getKey() { return key; }
        };
      } catch (Exception e) {
        throw new KeySelectorException(e.getMessage());
      }

    }
}
