//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_027_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Case4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Case4">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.027.001.06}Max35Text"/>
 *         <element name="Cretr" type="{urn:iso:std:iso:20022:tech:xsd:camt.027.001.06}Party35Choice_2"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Case4", propOrder = {
    "id",
    "cretr"
})
public class Case4 {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Cretr", required = true)
    protected Party35Choice2 cretr;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the cretr property.
     * 
     * @return
     *     possible object is
     *     {@link Party35Choice2 }
     *     
     */
    public Party35Choice2 getCretr() {
        return cretr;
    }

    /**
     * Sets the value of the cretr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party35Choice2 }
     *     
     */
    public void setCretr(Party35Choice2 value) {
        this.cretr = value;
    }

}
