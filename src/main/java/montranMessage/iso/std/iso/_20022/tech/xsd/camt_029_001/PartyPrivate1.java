//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_029_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyPrivate1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PartyPrivate1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <choice>
 *           <element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.03}PersonIdentification4"/>
 *         </choice>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyPrivate1", propOrder = {
    "prvtId"
})
public class PartyPrivate1 {

    @XmlElement(name = "PrvtId")
    protected PersonIdentification4 prvtId;

    /**
     * Gets the value of the prvtId property.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentification4 }
     *     
     */
    public PersonIdentification4 getPrvtId() {
        return prvtId;
    }

    /**
     * Sets the value of the prvtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentification4 }
     *     
     */
    public void setPrvtId(PersonIdentification4 value) {
        this.prvtId = value;
    }

}
