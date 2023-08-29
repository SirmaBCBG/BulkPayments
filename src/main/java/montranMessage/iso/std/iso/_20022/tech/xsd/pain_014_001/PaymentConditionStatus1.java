//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pain_014_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentConditionStatus1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PaymentConditionStatus1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="AccptdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}ActiveCurrencyAndAmount" minOccurs="0"/>
 *         <element name="GrntedPmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/>
 *         <element name="EarlyPmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentConditionStatus1", propOrder = {
    "accptdAmt",
    "grntedPmt",
    "earlyPmt"
})
public class PaymentConditionStatus1 {

    @XmlElement(name = "AccptdAmt")
    protected ActiveCurrencyAndAmount accptdAmt;
    @XmlElement(name = "GrntedPmt")
    protected boolean grntedPmt;
    @XmlElement(name = "EarlyPmt")
    protected boolean earlyPmt;

    /**
     * Gets the value of the accptdAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAndAmount }
     *     
     */
    public ActiveCurrencyAndAmount getAccptdAmt() {
        return accptdAmt;
    }

    /**
     * Sets the value of the accptdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAndAmount }
     *     
     */
    public void setAccptdAmt(ActiveCurrencyAndAmount value) {
        this.accptdAmt = value;
    }

    /**
     * Gets the value of the grntedPmt property.
     * 
     */
    public boolean isGrntedPmt() {
        return grntedPmt;
    }

    /**
     * Sets the value of the grntedPmt property.
     * 
     */
    public void setGrntedPmt(boolean value) {
        this.grntedPmt = value;
    }

    /**
     * Gets the value of the earlyPmt property.
     * 
     */
    public boolean isEarlyPmt() {
        return earlyPmt;
    }

    /**
     * Sets the value of the earlyPmt property.
     * 
     */
    public void setEarlyPmt(boolean value) {
        this.earlyPmt = value;
    }

}
