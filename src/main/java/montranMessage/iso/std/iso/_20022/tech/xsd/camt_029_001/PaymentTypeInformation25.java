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
 * <p>Java class for PaymentTypeInformation25 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PaymentTypeInformation25">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="SvcLvl" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}ServiceLevel8Choice"/>
 *         <element name="LclInstrm" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}LocalInstrument2Choice" minOccurs="0"/>
 *         <element name="CtgyPurp" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}CategoryPurpose1Choice" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTypeInformation25", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.029.001.08", propOrder = {
    "svcLvl",
    "lclInstrm",
    "ctgyPurp"
})
public class PaymentTypeInformation25 {

    @XmlElement(name = "SvcLvl", required = true)
    protected ServiceLevel8Choice svcLvl;
    @XmlElement(name = "LclInstrm")
    protected LocalInstrument2Choice2 lclInstrm;
    @XmlElement(name = "CtgyPurp")
    protected CategoryPurpose1Choice2 ctgyPurp;

    /**
     * Gets the value of the svcLvl property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLevel8Choice }
     *     
     */
    public ServiceLevel8Choice getSvcLvl() {
        return svcLvl;
    }

    /**
     * Sets the value of the svcLvl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLevel8Choice }
     *     
     */
    public void setSvcLvl(ServiceLevel8Choice value) {
        this.svcLvl = value;
    }

    /**
     * Gets the value of the lclInstrm property.
     * 
     * @return
     *     possible object is
     *     {@link LocalInstrument2Choice2 }
     *     
     */
    public LocalInstrument2Choice2 getLclInstrm() {
        return lclInstrm;
    }

    /**
     * Sets the value of the lclInstrm property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalInstrument2Choice2 }
     *     
     */
    public void setLclInstrm(LocalInstrument2Choice2 value) {
        this.lclInstrm = value;
    }

    /**
     * Gets the value of the ctgyPurp property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryPurpose1Choice2 }
     *     
     */
    public CategoryPurpose1Choice2 getCtgyPurp() {
        return ctgyPurp;
    }

    /**
     * Sets the value of the ctgyPurp property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryPurpose1Choice2 }
     *     
     */
    public void setCtgyPurp(CategoryPurpose1Choice2 value) {
        this.ctgyPurp = value;
    }

}
