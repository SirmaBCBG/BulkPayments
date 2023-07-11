//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pacs_004_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargeBearerType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChargeBearerType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SLEV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChargeBearerType1Code")
@XmlEnum
public enum ChargeBearerType1Code {


    /**
     * Charges are to be applied following the rules agreed in the service level and/or scheme.
     * 
     */
    SLEV;

    public String value() {
        return name();
    }

    public static ChargeBearerType1Code fromValue(String v) {
        return valueOf(v);
    }

}
