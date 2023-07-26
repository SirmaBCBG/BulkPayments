package com.sirmabc.bulkpayments.util.helpers;

import com.sirmabc.bulkpayments.util.Directory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileHelper {

    public static void moveFile(File file, String targetPath) throws IOException {
        Path source = Path.of(file.getPath());
        Path target = Path.of(targetPath);

        Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }

    // Gets all files that are directly inside the given directory
    // If the given directory contains other directories, their content won't be read
    public static Directory getDirectoryObject(String path, String fileExtension) {
        File dir = new File(path);
        File[] files;

        if (fileExtension != null && !fileExtension.isBlank()) {
            files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(fileExtension.toLowerCase()));
        } else {
            files = dir.listFiles();
        }

        if (files == null) files = new File[0];

        return new Directory(path, files);
    }

    public static void objectToXmlFile(Object o, String filePath) throws JAXBException, ParserConfigurationException, IOException, SAXException, TransformerException {
        String xmlString = serializeXml(o);

        // Parse the XML String to prevent encoding issues
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

        // Write the parsed document to a XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result =  new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    public static String serializeXml(Object o) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(o, stringWriter);

        String xml = stringWriter.toString();

        return xml;
    }

    public static <T>T deserializeXml(String xml, Class<T> tClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(xml);
        T result = (T) unmarshaller.unmarshal(reader);

        return result;
    }

    public static <T>T deserializeXml(File xmlFile, Class<T> tClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        T result = (T) unmarshaller.unmarshal(xmlFile);

        return result;
    }
}
