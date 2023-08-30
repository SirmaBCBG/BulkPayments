package com.sirmabc.bulkpayments.util.xmlsigner;

import com.sirmabc.bulkpayments.communicators.BorikaClientScheduler;
import com.sirmabc.bulkpayments.util.Properties;
import com.sirmabc.bulkpayments.util.helpers.XMLHelper;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
@Scope("singleton")
public class XMLSigner {
    public static String SIGNATURE_PARENT_TAG = "Sgntr";
    public static String SIGNATURE_PARENT_TAG_NS = "";

    private static final Logger logger = LoggerFactory.getLogger(BorikaClientScheduler.class);

    @Autowired
    MontranPublicKeySelector montranPublicKeySelector;

    @Autowired
    Properties properties;

    @Autowired
    public XMLSigner (MontranPublicKeySelector montranPublicKeySelector) {
      this.montranPublicKeySelector = montranPublicKeySelector;
    }

    public Document string2XML(String xml) throws Exception
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);

      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(xml));

      return builder.parse(is);
    }

    public String xml2String(Document document) throws Exception {
      StringWriter writer = new StringWriter();

      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.transform(new DOMSource(document), new StreamResult(writer));
      //String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
      String output = writer.getBuffer().toString();

      return output;
    }

    public void saveXML(Document document, File file) throws Exception {
      OutputStream os = new FileOutputStream(file);

      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.transform(new DOMSource(document), new StreamResult(os));
    }

    public Document loadXMLFromFile(File xmlFile) throws Exception
    {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlFile));

      return doc;
    }

    public boolean verifyWithExternalKey(Document document) throws Exception {
      NodeList nl = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
      if (nl.getLength() == 0) {
        logger.info("Cannot find Signature element");

        return false;
      }

      DOMValidateContext valContext = new DOMValidateContext(montranPublicKeySelector, nl.item(0));

      XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
      XMLSignature signature = fac.unmarshalXMLSignature(valContext);

      return signature.validate(valContext);
    }

    public boolean verify(Document document) throws Exception {
      XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

      NodeList nl = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
      if (nl.getLength() == 0) {
        logger.info("Cannot find Signature element");
        return false;
      }

      boolean result = false;

      // try with presented cert in XML (if any)
      NodeList x509elements = document.getElementsByTagNameNS(XMLSignature.XMLNS, "X509Certificate");
      if (x509elements.getLength() > 0) {
        logger.info("There is a cert, lets validate with it...");

        DOMValidateContext valContext = new DOMValidateContext(new X509KeySelector(), nl.item(0));
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        try {
          result = signature.validate(valContext);
        } catch (Exception e) {
          logger.info(e.getMessage(), e);
          //return false;
        }
      }

      // try with external keystore
      if (!result) {
        DOMValidateContext valContext = new DOMValidateContext(montranPublicKeySelector, nl.item(0));
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        result = signature.validate(valContext);
      }

      return result;
    }

    public String sign(Message message) throws Exception {

        Document document = string2XML(XMLHelper.serializeXml(message));

        document = sign(document);
        String signedXml = xml2String(document);

        return signedXml;
    }

    private Document sign(Document doc) throws Exception {

      KeyStore ks = KeyStore.getInstance("JKS");
      ks.load(new FileInputStream(properties.getSignSBCKeyStorePath()), properties.getSignSBCKeyStorePassword().toCharArray());
      KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(properties.getSignSBCKeyStorePassword().toCharArray());
      KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(properties.getSignSBCKeyStoreAlias(), passwordProtection);

      XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
      //Transform exc14nTranform = fac.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (TransformParameterSpec) null);
      Transform exc14nTranform = fac.newTransform("http://www.w3.org/2006/12/xml-c14n11", (TransformParameterSpec) null);
      Transform envTransform = fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);

      List<Transform> transformList = new ArrayList();
      transformList.add(envTransform);
      transformList.add(exc14nTranform);

      Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA256, null), transformList, null, null);
      //Reference ref = fac.newReference("",fac.newDigestMethod(DigestMethod.SHA256, null));

      SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
              fac.newSignatureMethod(SignatureMethod.RSA_SHA256, null), Collections.singletonList(ref));

      X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

      KeyInfoFactory kif = fac.getKeyInfoFactory();
      List x509Content = new ArrayList();

      X509IssuerSerial issuer = kif.newX509IssuerSerial(cert.getIssuerX500Principal().toString(), cert.getSerialNumber());

      x509Content.add(cert.getSubjectX500Principal().getName());
      x509Content.add(issuer);
      x509Content.add(cert);

      X509Data xd = kif.newX509Data(x509Content);
      KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

      NodeList nl = doc.getElementsByTagName(SIGNATURE_PARENT_TAG);
      Node node = nl.item(0);
      DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), node);
      XMLSignature signature = fac.newXMLSignature(si, ki);//, Collections.singletonList(obj), null, null);
      signature.sign(dsc);

      return doc;
    }

    public static void main(String[] args) {


//      try {
//          XMLSigner signer = new XMLSigner();
//        KeyStore ks = KeyStore.getInstance("JKS");
//        ks.load(new FileInputStream("d:\\xml_sign.jks"), "sla6945".toCharArray());
//        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("sirmabcxml", new KeyStore.PasswordProtection("sla6945".toCharArray()));
//
//        Document doc = string2XML(xml);
//        Document signedDoc = sign(doc, keyEntry);
//        System.out.println(xml2String(signedDoc));
//        saveXML(signedDoc, new File("d:/work/c3_signed.xml"));
//
//        Document doc2 = loadXMLFromFile(new File("d:/work/c3_signed.xml"));
//
//        System.out.println(verify(doc2));
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
   }

}
