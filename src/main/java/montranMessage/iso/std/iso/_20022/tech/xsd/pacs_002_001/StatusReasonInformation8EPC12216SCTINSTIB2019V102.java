//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pacs_002_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusReasonInformation8_EPC122-16_SCT_INST_IB_2019_V1.0_2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusReasonInformation8_EPC122-16_SCT_INST_IB_2019_V1.0_2">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03}StatusReasonInformation8">
 *       &lt;sequence>
 *         &lt;element name="Orgtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03}PartyIdentification32_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *         &lt;element name="Rsn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03}StatusReason6Choice_EPC122-16_SCT_INST_IB_2019_V1.0_2"/>
 *         &lt;element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03}Max105Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusReasonInformation8_EPC122-16_SCT_INST_IB_2019_V1.0_2")
public class StatusReasonInformation8EPC12216SCTINSTIB2019V102
    extends StatusReasonInformation8
{


}
