//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_053_001;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionInterest2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TransactionInterest2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}ActiveOrHistoricCurrencyAndAmount"/>
 *         <element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}CreditDebitCode"/>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}InterestType1Choice" minOccurs="0"/>
 *         <element name="Rate" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}Rate3" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="FrToDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}DateTimePeriodDetails" minOccurs="0"/>
 *         <element name="Rsn" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}Max35Text" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionInterest2", propOrder = {
    "amt",
    "cdtDbtInd",
    "tp",
    "rate",
    "frToDt",
    "rsn"
})
public class TransactionInterest2 {

    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "CdtDbtInd", required = true)
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "Tp")
    protected InterestType1Choice tp;
    @XmlElement(name = "Rate")
    protected List<Rate3> rate;
    @XmlElement(name = "FrToDt")
    protected DateTimePeriodDetails frToDt;
    @XmlElement(name = "Rsn")
    protected String rsn;

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Gets the value of the cdtDbtInd property.
     * 
     * @return
     *     possible object is
     *     {@link CreditDebitCode }
     *     
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDebitCode }
     *     
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link InterestType1Choice }
     *     
     */
    public InterestType1Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterestType1Choice }
     *     
     */
    public void setTp(InterestType1Choice value) {
        this.tp = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the rate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rate3 }
     * 
     * 
     * @return
     *     The value of the rate property.
     */
    public List<Rate3> getRate() {
        if (rate == null) {
            rate = new ArrayList<>();
        }
        return this.rate;
    }

    /**
     * Gets the value of the frToDt property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimePeriodDetails }
     *     
     */
    public DateTimePeriodDetails getFrToDt() {
        return frToDt;
    }

    /**
     * Sets the value of the frToDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimePeriodDetails }
     *     
     */
    public void setFrToDt(DateTimePeriodDetails value) {
        this.frToDt = value;
    }

    /**
     * Gets the value of the rsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRsn() {
        return rsn;
    }

    /**
     * Sets the value of the rsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRsn(String value) {
        this.rsn = value;
    }

}
