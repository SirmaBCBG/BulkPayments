//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pacs_004_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentReturnV02_EPC122-16_SCT_INST_IB_2019_V1.0 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentReturnV02_EPC122-16_SCT_INST_IB_2019_V1.0">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.02}PaymentReturnV02">
 *       &lt;sequence>
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.02}GroupHeader38_EPC122-16_SCT_INST_IB_2019_V1.0"/>
 *         &lt;element name="OrgnlGrpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.02}OriginalGroupInformation21_EPC122-16_SCT_INST_IB_2019_V1.0" minOccurs="0"/>
 *         &lt;element name="TxInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.02}PaymentTransactionInformation27_EPC122-16_SCT_INST_IB_2019_V1.0" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentReturnV02_EPC122-16_SCT_INST_IB_2019_V1.0")
public class PaymentReturnV02EPC12216SCTINSTIB2019V10
    extends PaymentReturnV02
{


}
