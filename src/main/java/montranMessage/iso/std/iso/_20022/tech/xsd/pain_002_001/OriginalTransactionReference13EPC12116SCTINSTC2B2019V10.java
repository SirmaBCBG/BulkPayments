//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pain_002_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OriginalTransactionReference13_EPC121-16_SCT_INST_C2B_2019_V1.0 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OriginalTransactionReference13_EPC121-16_SCT_INST_C2B_2019_V1.0">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}OriginalTransactionReference13">
 *       &lt;sequence>
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}AmountType3Choice_EPC121-16_SCT_INST_C2B_2019_V1.0" minOccurs="0"/>
 *         &lt;element name="ReqdExctnDt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ISODate" minOccurs="0"/>
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PaymentTypeInformation22_EPC121-16_SCT_INST_C2B_2019_V1.0" minOccurs="0"/>
 *         &lt;element name="PmtMtd" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PaymentMethod4Code" minOccurs="0"/>
 *         &lt;element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}RemittanceInformation5_EPC121-16_SCT_INST_C2B_2019_V1.0" minOccurs="0"/>
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PartyIdentification32_EPC121-16_SCT_INST_C2B_2019_V1.0_2" minOccurs="0"/>
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PartyIdentification32_EPC121-16_SCT_INST_C2B_2019_V1.0_3" minOccurs="0"/>
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CashAccount16" minOccurs="0"/>
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4"/>
 *         &lt;element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4_EPC121-16_SCT_INST_C2B_2019_V1.0" minOccurs="0"/>
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PartyIdentification32_EPC121-16_SCT_INST_C2B_2019_V1.0_3"/>
 *         &lt;element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CashAccount16_EPC121-16_SCT_INST_C2B_2019_V1.0" minOccurs="0"/>
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PartyIdentification32_EPC121-16_SCT_INST_C2B_2019_V1.0_2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalTransactionReference13_EPC121-16_SCT_INST_C2B_2019_V1.0")
public class OriginalTransactionReference13EPC12116SCTINSTC2B2019V10
    extends OriginalTransactionReference13
{


}
