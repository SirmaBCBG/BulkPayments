//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pacs_028_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OriginalTransactionReference24_EPC122-16_SCT_INST_IB_2019_V1.0_Update complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OriginalTransactionReference24_EPC122-16_SCT_INST_IB_2019_V1.0_Update">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}OriginalTransactionReference24">
 *       &lt;sequence>
 *         &lt;element name="IntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         &lt;element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}ISODate" minOccurs="0"/>
 *         &lt;element name="SttlmInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}SettlementInstruction4" minOccurs="0"/>
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}PaymentTypeInformation25_EPC122-16_SCT_INST_IB_2019_V1.0_Update" minOccurs="0"/>
 *         &lt;element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}RemittanceInformation11_EPC122-16_SCT_INST_IB_2019_V1.0_Update" minOccurs="0"/>
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}PartyIdentification43_EPC122-16_SCT_INST_IB_2019_V1.0_Update" minOccurs="0"/>
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}PartyIdentification43_EPC122-16_SCT_INST_IB_2019_V1.0_Update_2" minOccurs="0"/>
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}CashAccount24_EPC122-16_SCT_INST_IB_2019_V1.0_Update" minOccurs="0"/>
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}BranchAndFinancialInstitutionIdentification5_EPC122-16_SCT_INST_IB_2019_V1.0_Update"/>
 *         &lt;element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}BranchAndFinancialInstitutionIdentification5_EPC122-16_SCT_INST_IB_2019_V1.0_Update" minOccurs="0"/>
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}PartyIdentification43_EPC122-16_SCT_INST_IB_2019_V1.0_Update_2" minOccurs="0"/>
 *         &lt;element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}CashAccount24_EPC122-16_SCT_INST_IB_2019_V1.0_Update" minOccurs="0"/>
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.01}PartyIdentification43_EPC122-16_SCT_INST_IB_2019_V1.0_Update_3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalTransactionReference24_EPC122-16_SCT_INST_IB_2019_V1.0_Update")
public class OriginalTransactionReference24EPC12216SCTINSTIB2019V10Update
    extends OriginalTransactionReference24
{


}
