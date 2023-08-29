//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_053_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashAccountType4Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="CashAccountType4Code">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="CASH"/>
 *     <enumeration value="CHAR"/>
 *     <enumeration value="COMM"/>
 *     <enumeration value="TAXE"/>
 *     <enumeration value="CISH"/>
 *     <enumeration value="TRAS"/>
 *     <enumeration value="SACC"/>
 *     <enumeration value="CACC"/>
 *     <enumeration value="SVGS"/>
 *     <enumeration value="ONDP"/>
 *     <enumeration value="MGLD"/>
 *     <enumeration value="NREX"/>
 *     <enumeration value="MOMA"/>
 *     <enumeration value="LOAN"/>
 *     <enumeration value="SLRY"/>
 *     <enumeration value="ODFT"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "CashAccountType4Code")
@XmlEnum
public enum CashAccountType4Code {

    CASH,
    CHAR,
    COMM,
    TAXE,
    CISH,
    TRAS,
    SACC,
    CACC,
    SVGS,
    ONDP,
    MGLD,
    NREX,
    MOMA,
    LOAN,
    SLRY,
    ODFT;

    public String value() {
        return name();
    }

    public static CashAccountType4Code fromValue(String v) {
        return valueOf(v);
    }

}
