package com.sirmabc.bulkpayments.util.helpers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import montranMessage.montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLHelper {

    private static final Logger logger = LoggerFactory.getLogger(XMLHelper.class);

    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Message.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static String serializeXml(Message message) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(message, stringWriter);

        String xml = stringWriter.toString();

        return xml;
    }

    public static Message deserializeXml(String xml) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(xml);
        Message result = (Message) unmarshaller.unmarshal(reader);

        return result;
    }

    public static Message deserializeXml(File xmlFile) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Message result = (Message) unmarshaller.unmarshal(xmlFile);

        return result;
    }

}
