//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pain_002_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalServiceLevel1Code_EPC121-16_SCT_INST_C2B_2019_V1.0.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExternalServiceLevel1Code_EPC121-16_SCT_INST_C2B_2019_V1.0">
 *   &lt;restriction base="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ExternalServiceLevel1Code">
 *     &lt;enumeration value="SEPA"/>
 *     &lt;minLength value="1"/>
 *     &lt;maxLength value="4"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExternalServiceLevel1Code_EPC121-16_SCT_INST_C2B_2019_V1.0")
@XmlEnum
public enum ExternalServiceLevel1CodeEPC12116SCTINSTC2B2019V10 {


    /**
     * 
     * Payment must be executed following the Single Euro Payments Area scheme.
     * 
     * Status: New
     * Status Date: April 2009
     * Introduced Date: April 2009
     *           
     * 
     */
    SEPA;

    public String value() {
        return name();
    }

    public static ExternalServiceLevel1CodeEPC12116SCTINSTC2B2019V10 fromValue(String v) {
        return valueOf(v);
    }

}
