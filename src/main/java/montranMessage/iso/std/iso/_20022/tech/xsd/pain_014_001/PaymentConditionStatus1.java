//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
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
 * <pre>
 * &lt;complexType name="PaymentConditionStatus1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccptdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}ActiveCurrencyAndAmount" minOccurs="0"/>
 *         &lt;element name="GrntedPmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/>
 *         &lt;element name="EarlyPmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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