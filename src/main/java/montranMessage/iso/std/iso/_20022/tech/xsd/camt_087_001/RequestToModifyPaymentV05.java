//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_087_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestToModifyPaymentV05 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RequestToModifyPaymentV05">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Assgnmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.087.001.05}CaseAssignment4"/>
 *         <element name="Case" type="{urn:iso:std:iso:20022:tech:xsd:camt.087.001.05}Case4"/>
 *         <element name="Undrlyg" type="{urn:iso:std:iso:20022:tech:xsd:camt.087.001.05}UnderlyingTransaction4Choice"/>
 *         <element name="Mod" type="{urn:iso:std:iso:20022:tech:xsd:camt.087.001.05}RequestedModification7"/>
 *         <element name="InstrForAssgne" type="{urn:iso:std:iso:20022:tech:xsd:camt.087.001.05}InstructionForAssignee1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestToModifyPaymentV05", propOrder = {
    "assgnmt",
    "_case",
    "undrlyg",
    "mod",
    "instrForAssgne"
})
public class RequestToModifyPaymentV05 {

    @XmlElement(name = "Assgnmt", required = true)
    protected CaseAssignment4 assgnmt;
    @XmlElement(name = "Case", required = true)
    protected Case4 _case;
    @XmlElement(name = "Undrlyg", required = true)
    protected UnderlyingTransaction4Choice undrlyg;
    @XmlElement(name = "Mod", required = true)
    protected RequestedModification7 mod;
    @XmlElement(name = "InstrForAssgne")
    protected InstructionForAssignee1 instrForAssgne;

    /**
     * Gets the value of the assgnmt property.
     * 
     * @return
     *     possible object is
     *     {@link CaseAssignment4 }
     *     
     */
    public CaseAssignment4 getAssgnmt() {
        return assgnmt;
    }

    /**
     * Sets the value of the assgnmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseAssignment4 }
     *     
     */
    public void setAssgnmt(CaseAssignment4 value) {
        this.assgnmt = value;
    }

    /**
     * Gets the value of the case property.
     * 
     * @return
     *     possible object is
     *     {@link Case4 }
     *     
     */
    public Case4 getCase() {
        return _case;
    }

    /**
     * Sets the value of the case property.
     * 
     * @param value
     *     allowed object is
     *     {@link Case4 }
     *     
     */
    public void setCase(Case4 value) {
        this._case = value;
    }

    /**
     * Gets the value of the undrlyg property.
     * 
     * @return
     *     possible object is
     *     {@link UnderlyingTransaction4Choice }
     *     
     */
    public UnderlyingTransaction4Choice getUndrlyg() {
        return undrlyg;
    }

    /**
     * Sets the value of the undrlyg property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnderlyingTransaction4Choice }
     *     
     */
    public void setUndrlyg(UnderlyingTransaction4Choice value) {
        this.undrlyg = value;
    }

    /**
     * Gets the value of the mod property.
     * 
     * @return
     *     possible object is
     *     {@link RequestedModification7 }
     *     
     */
    public RequestedModification7 getMod() {
        return mod;
    }

    /**
     * Sets the value of the mod property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestedModification7 }
     *     
     */
    public void setMod(RequestedModification7 value) {
        this.mod = value;
    }

    /**
     * Gets the value of the instrForAssgne property.
     * 
     * @return
     *     possible object is
     *     {@link InstructionForAssignee1 }
     *     
     */
    public InstructionForAssignee1 getInstrForAssgne() {
        return instrForAssgne;
    }

    /**
     * Sets the value of the instrForAssgne property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstructionForAssignee1 }
     *     
     */
    public void setInstrForAssgne(InstructionForAssignee1 value) {
        this.instrForAssgne = value;
    }

}