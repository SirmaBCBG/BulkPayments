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
 * <p>Java class for PaymentTransactionInformation31_EPC122-16_SCT_INST_IB_2019_V1.0 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentTransactionInformation31_EPC122-16_SCT_INST_IB_2019_V1.0">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}PaymentTransactionInformation31">
 *       &lt;sequence>
 *         &lt;element name="CxlId" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}Max35Text"/>
 *         &lt;element name="OrgnlGrpInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}OriginalGroupInformation3_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *         &lt;element name="OrgnlInstrId" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}Max35Text" minOccurs="0"/>
 *         &lt;element name="OrgnlEndToEndId" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}Max35Text"/>
 *         &lt;element name="OrgnlTxId" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}Max35Text"/>
 *         &lt;element name="OrgnlIntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}ActiveOrHistoricCurrencyAndAmount_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *         &lt;element name="OrgnlIntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}ISODate"/>
 *         &lt;element name="CxlRsnInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}CancellationReasonInformation3_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *         &lt;element name="OrgnlTxRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.056.001.01}OriginalTransactionReference13_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTransactionInformation31_EPC122-16_SCT_INST_IB_2019_V1.0")
public class PaymentTransactionInformation31EPC12216SCTINSTIB2019V10
    extends PaymentTransactionInformation31
{


}
