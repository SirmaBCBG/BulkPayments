//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pacs_028_001;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * Scope
 * The FinancialInstitutionToFinancialInstitutionPaymentStatusRequest message is sent by the debtor agent to the creditor agent, directly or through other agents and/or a payment clearing and settlement system.  It is used to request a FIToFIPaymentStatusReport message containing information on the status of a previously sent instruction. 
 * Usage
 * The FIToFIPaymentStatusRequest message is exchanged between agents to request status information about instructions previously sent. Its usage will always be governed by a bilateral agreement between the agents.
 * The FIToFIPaymentStatusRequest message can be used to request information about the status (e.g. rejection, acceptance) of a credit transfer instruction, a direct debit instruction, as well as other intra-agent instructions (for example FIToFIPaymentCancellationRequest).
 * The FIToFIPaymentStatusRequest message refers to the original instruction(s) by means of references only or by means of references and a set of elements from the original instruction.
 * The FIToFIPaymentStatusRequest message can be used in domestic and cross-border scenarios.
 *       
 * 
 * <p>Java class for FIToFIPaymentStatusRequestV01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FIToFIPaymentStatusRequestV01">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}GroupHeader53"/>
 *         &lt;element name="OrgnlGrpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}OriginalGroupInformation27" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TxInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}PaymentTransaction73" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FIToFIPaymentStatusRequestV01", propOrder = {
    "grpHdr",
    "orgnlGrpInf",
    "txInf"
})
@XmlSeeAlso({
    FIToFIPaymentStatusRequestV01EPC12216SCTINSTIB2019V10Update.class
})
public class FIToFIPaymentStatusRequestV01 {

    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader53 grpHdr;
    @XmlElement(name = "OrgnlGrpInf")
    protected List<OriginalGroupInformation27> orgnlGrpInf;
    @XmlElement(name = "TxInf")
    protected List<PaymentTransaction73> txInf;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader53 }
     *     
     */
    public GroupHeader53 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader53 }
     *     
     */
    public void setGrpHdr(GroupHeader53 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the orgnlGrpInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orgnlGrpInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrgnlGrpInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OriginalGroupInformation27 }
     * 
     * 
     */
    public List<OriginalGroupInformation27> getOrgnlGrpInf() {
        if (orgnlGrpInf == null) {
            orgnlGrpInf = new ArrayList<OriginalGroupInformation27>();
        }
        return this.orgnlGrpInf;
    }

    /**
     * Gets the value of the txInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the txInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTxInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTransaction73 }
     * 
     * 
     */
    public List<PaymentTransaction73> getTxInf() {
        if (txInf == null) {
            txInf = new ArrayList<PaymentTransaction73>();
        }
        return this.txInf;
    }

}