//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pain_013_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxAuthorisation1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TaxAuthorisation1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Titl" type="{urn:iso:std:iso:20022:tech:xsd:pain.013.001.07}Max35Text" minOccurs="0"/>
 *         <element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:pain.013.001.07}Max140Text" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxAuthorisation1", propOrder = {
    "titl",
    "nm"
})
public class TaxAuthorisation1 {

    @XmlElement(name = "Titl")
    protected String titl;
    @XmlElement(name = "Nm")
    protected String nm;

    /**
     * Gets the value of the titl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitl() {
        return titl;
    }

    /**
     * Sets the value of the titl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitl(String value) {
        this.titl = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

}
