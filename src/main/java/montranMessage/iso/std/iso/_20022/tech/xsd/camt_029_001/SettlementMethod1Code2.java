//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_029_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SettlementMethod1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="SettlementMethod1Code">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="CLRG"/>
 *     <enumeration value="COVE"/>
 *     <enumeration value="INDA"/>
 *     <enumeration value="INGA"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "SettlementMethod1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.029.001.08")
@XmlEnum
public enum SettlementMethod1Code2 {

    CLRG,
    COVE,
    INDA,
    INGA;

    public String value() {
        return name();
    }

    public static SettlementMethod1Code2 fromValue(String v) {
        return valueOf(v);
    }

}
