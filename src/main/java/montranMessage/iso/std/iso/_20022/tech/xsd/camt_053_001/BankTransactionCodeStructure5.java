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
 * <p>Java class for BankTransactionCodeStructure5 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="BankTransactionCodeStructure5">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}ExternalBankTransactionDomain1Code"/>
 *         <element name="Fmly" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}BankTransactionCodeStructure6"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankTransactionCodeStructure5", propOrder = {
    "cd",
    "fmly"
})
public class BankTransactionCodeStructure5 {

    @XmlElement(name = "Cd", required = true)
    protected String cd;
    @XmlElement(name = "Fmly", required = true)
    protected BankTransactionCodeStructure6 fmly;

    /**
     * Gets the value of the cd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCd(String value) {
        this.cd = value;
    }

    /**
     * Gets the value of the fmly property.
     * 
     * @return
     *     possible object is
     *     {@link BankTransactionCodeStructure6 }
     *     
     */
    public BankTransactionCodeStructure6 getFmly() {
        return fmly;
    }

    /**
     * Sets the value of the fmly property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankTransactionCodeStructure6 }
     *     
     */
    public void setFmly(BankTransactionCodeStructure6 value) {
        this.fmly = value;
    }

}
