//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_053_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rate3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Rate3">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}RateType4Choice"/>
 *         <element name="VldtyRg" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}CurrencyAndAmountRange2" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rate3", propOrder = {
    "tp",
    "vldtyRg"
})
public class Rate3 {

    @XmlElement(name = "Tp", required = true)
    protected RateType4Choice tp;
    @XmlElement(name = "VldtyRg")
    protected CurrencyAndAmountRange2 vldtyRg;

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link RateType4Choice }
     *     
     */
    public RateType4Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateType4Choice }
     *     
     */
    public void setTp(RateType4Choice value) {
        this.tp = value;
    }

    /**
     * Gets the value of the vldtyRg property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyAndAmountRange2 }
     *     
     */
    public CurrencyAndAmountRange2 getVldtyRg() {
        return vldtyRg;
    }

    /**
     * Sets the value of the vldtyRg property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyAndAmountRange2 }
     *     
     */
    public void setVldtyRg(CurrencyAndAmountRange2 value) {
        this.vldtyRg = value;
    }

}
