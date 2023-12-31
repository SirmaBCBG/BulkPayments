package com.sirmabc.bulkpayments.util.xmlsigner;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;

public class X509KeySelector extends KeySelector {
  public KeySelectorResult select(KeyInfo keyInfo,
                                  Purpose purpose,
                                  AlgorithmMethod method,
                                  XMLCryptoContext context)
          throws KeySelectorException {
    Iterator ki = keyInfo.getContent().iterator();
    while (ki.hasNext()) {
      XMLStructure info = (XMLStructure) ki.next();
      if (!(info instanceof X509Data))
        continue;
      X509Data x509Data = (X509Data) info;
      Iterator xi = x509Data.getContent().iterator();
      while (xi.hasNext()) {
        Object o = xi.next();
        if (!(o instanceof X509Certificate))
          continue;
        final PublicKey key = ((X509Certificate)o).getPublicKey();
        // Make sure the algorithm is compatible
        // with the method.
        if (algEquals(method.getAlgorithm(), key.getAlgorithm())) {
          return new KeySelectorResult() {
            public Key getKey() { return key; }
          };
        }
      }
    }
    throw new KeySelectorException("No key found!");
  }

  static boolean algEquals(String algURI, String algName) {
    if (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) {
      return true;
    }

    if (algName.equalsIgnoreCase("RSA") &&
        (algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1) || algURI.equalsIgnoreCase("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"))) {
      return true;
    }

    return false;
  }
}
