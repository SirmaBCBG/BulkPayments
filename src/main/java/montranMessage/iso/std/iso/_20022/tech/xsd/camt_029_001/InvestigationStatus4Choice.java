//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_029_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvestigationStatus4Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="InvestigationStatus4Choice">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Conf" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.08}ExternalInvestigationExecutionConfirmation1Code"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvestigationStatus4Choice", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.029.001.08", propOrder = {
    "conf"
})
public class InvestigationStatus4Choice {

    @XmlElement(name = "Conf")
    @XmlSchemaType(name = "string")
    protected ExternalInvestigationExecutionConfirmation1Code conf;

    /**
     * Gets the value of the conf property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalInvestigationExecutionConfirmation1Code }
     *     
     */
    public ExternalInvestigationExecutionConfirmation1Code getConf() {
        return conf;
    }

    /**
     * Sets the value of the conf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalInvestigationExecutionConfirmation1Code }
     *     
     */
    public void setConf(ExternalInvestigationExecutionConfirmation1Code value) {
        this.conf = value;
    }

}
