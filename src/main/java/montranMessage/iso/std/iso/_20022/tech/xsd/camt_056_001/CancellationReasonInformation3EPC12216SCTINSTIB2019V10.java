//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_056_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancellationReasonInformation3_EPC122-16_SCT_INST_IB_2019_V1.0 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancellationReasonInformation3_EPC122-16_SCT_INST_IB_2019_V1.0">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}CancellationReasonInformation3">
 *       &lt;sequence>
 *         &lt;element name="Orgtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}PartyIdentification32_EPC122-16_SCT_INST_IB_2019_V1.0_2"/>
 *         &lt;element name="Rsn" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}CancellationReason2Choice_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *         &lt;element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}Max105Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancellationReasonInformation3_EPC122-16_SCT_INST_IB_2019_V1.0")
public class CancellationReasonInformation3EPC12216SCTINSTIB2019V10
    extends CancellationReasonInformation3
{


}
