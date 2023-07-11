//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.montran.participants;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the montran.participants package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Participants_QNAME = new QName("urn:montran:participants.001", "participants");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: montran.participants
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParticipantsType }
     * 
     */
    public ParticipantsType createParticipantsType() {
        return new ParticipantsType();
    }

    /**
     * Create an instance of {@link ParticipantInfo }
     * 
     */
    public ParticipantInfo createParticipantInfo() {
        return new ParticipantInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParticipantsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:montran:participants.001", name = "participants")
    public JAXBElement<ParticipantsType> createParticipants(ParticipantsType value) {
        return new JAXBElement<ParticipantsType>(_Participants_QNAME, ParticipantsType.class, null, value);
    }

}