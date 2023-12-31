//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_029_001;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OriginalTransactionReference27 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="OriginalTransactionReference27">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="IntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}ISODate" minOccurs="0"/>
 *         <element name="SttlmInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}SettlementInstruction4" minOccurs="0"/>
 *         <element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}PaymentTypeInformation25" minOccurs="0"/>
 *         <element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}RemittanceInformation15" minOccurs="0"/>
 *         <element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}Party35Choice" minOccurs="0"/>
 *         <element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}Party35Choice" minOccurs="0"/>
 *         <element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}CashAccount24" minOccurs="0"/>
 *         <element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/>
 *         <element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/>
 *         <element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}Party35Choice" minOccurs="0"/>
 *         <element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}CashAccount24" minOccurs="0"/>
 *         <element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}Party35Choice" minOccurs="0"/>
 *         <element name="Purp" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}Purpose2Choice" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalTransactionReference27", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.029.001.08", propOrder = {
    "intrBkSttlmAmt",
    "intrBkSttlmDt",
    "sttlmInf",
    "pmtTpInf",
    "rmtInf",
    "ultmtDbtr",
    "dbtr",
    "dbtrAcct",
    "dbtrAgt",
    "cdtrAgt",
    "cdtr",
    "cdtrAcct",
    "ultmtCdtr",
    "purp"
})
public class OriginalTransactionReference27 {

    @XmlElement(name = "IntrBkSttlmAmt")
    protected ActiveOrHistoricCurrencyAndAmount2 intrBkSttlmAmt;
    @XmlElement(name = "IntrBkSttlmDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar intrBkSttlmDt;
    @XmlElement(name = "SttlmInf")
    protected SettlementInstruction4 sttlmInf;
    @XmlElement(name = "PmtTpInf")
    protected PaymentTypeInformation25 pmtTpInf;
    @XmlElement(name = "RmtInf")
    protected RemittanceInformation15 rmtInf;
    @XmlElement(name = "UltmtDbtr")
    protected Party35Choice ultmtDbtr;
    @XmlElement(name = "Dbtr")
    protected Party35Choice dbtr;
    @XmlElement(name = "DbtrAcct")
    protected CashAccount24 dbtrAcct;
    @XmlElement(name = "DbtrAgt")
    protected BranchAndFinancialInstitutionIdentification5 dbtrAgt;
    @XmlElement(name = "CdtrAgt")
    protected BranchAndFinancialInstitutionIdentification5 cdtrAgt;
    @XmlElement(name = "Cdtr")
    protected Party35Choice cdtr;
    @XmlElement(name = "CdtrAcct")
    protected CashAccount24 cdtrAcct;
    @XmlElement(name = "UltmtCdtr")
    protected Party35Choice ultmtCdtr;
    @XmlElement(name = "Purp")
    protected Purpose2Choice2 purp;

    /**
     * Gets the value of the intrBkSttlmAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount2 }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount2 getIntrBkSttlmAmt() {
        return intrBkSttlmAmt;
    }

    /**
     * Sets the value of the intrBkSttlmAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount2 }
     *     
     */
    public void setIntrBkSttlmAmt(ActiveOrHistoricCurrencyAndAmount2 value) {
        this.intrBkSttlmAmt = value;
    }

    /**
     * Gets the value of the intrBkSttlmDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIntrBkSttlmDt() {
        return intrBkSttlmDt;
    }

    /**
     * Sets the value of the intrBkSttlmDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIntrBkSttlmDt(XMLGregorianCalendar value) {
        this.intrBkSttlmDt = value;
    }

    /**
     * Gets the value of the sttlmInf property.
     * 
     * @return
     *     possible object is
     *     {@link SettlementInstruction4 }
     *     
     */
    public SettlementInstruction4 getSttlmInf() {
        return sttlmInf;
    }

    /**
     * Sets the value of the sttlmInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementInstruction4 }
     *     
     */
    public void setSttlmInf(SettlementInstruction4 value) {
        this.sttlmInf = value;
    }

    /**
     * Gets the value of the pmtTpInf property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTypeInformation25 }
     *     
     */
    public PaymentTypeInformation25 getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * Sets the value of the pmtTpInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTypeInformation25 }
     *     
     */
    public void setPmtTpInf(PaymentTypeInformation25 value) {
        this.pmtTpInf = value;
    }

    /**
     * Gets the value of the rmtInf property.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceInformation15 }
     *     
     */
    public RemittanceInformation15 getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceInformation15 }
     *     
     */
    public void setRmtInf(RemittanceInformation15 value) {
        this.rmtInf = value;
    }

    /**
     * Gets the value of the ultmtDbtr property.
     * 
     * @return
     *     possible object is
     *     {@link Party35Choice }
     *     
     */
    public Party35Choice getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party35Choice }
     *     
     */
    public void setUltmtDbtr(Party35Choice value) {
        this.ultmtDbtr = value;
    }

    /**
     * Gets the value of the dbtr property.
     * 
     * @return
     *     possible object is
     *     {@link Party35Choice }
     *     
     */
    public Party35Choice getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party35Choice }
     *     
     */
    public void setDbtr(Party35Choice value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the dbtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setDbtrAcct(CashAccount24 value) {
        this.dbtrAcct = value;
    }

    /**
     * Gets the value of the dbtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Sets the value of the dbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setDbtrAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.dbtrAgt = value;
    }

    /**
     * Gets the value of the cdtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Sets the value of the cdtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setCdtrAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.cdtrAgt = value;
    }

    /**
     * Gets the value of the cdtr property.
     * 
     * @return
     *     possible object is
     *     {@link Party35Choice }
     *     
     */
    public Party35Choice getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party35Choice }
     *     
     */
    public void setCdtr(Party35Choice value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the cdtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setCdtrAcct(CashAccount24 value) {
        this.cdtrAcct = value;
    }

    /**
     * Gets the value of the ultmtCdtr property.
     * 
     * @return
     *     possible object is
     *     {@link Party35Choice }
     *     
     */
    public Party35Choice getUltmtCdtr() {
        return ultmtCdtr;
    }

    /**
     * Sets the value of the ultmtCdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party35Choice }
     *     
     */
    public void setUltmtCdtr(Party35Choice value) {
        this.ultmtCdtr = value;
    }

    /**
     * Gets the value of the purp property.
     * 
     * @return
     *     possible object is
     *     {@link Purpose2Choice2 }
     *     
     */
    public Purpose2Choice2 getPurp() {
        return purp;
    }

    /**
     * Sets the value of the purp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Purpose2Choice2 }
     *     
     */
    public void setPurp(Purpose2Choice2 value) {
        this.purp = value;
    }

}
