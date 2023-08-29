//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.pacs_008_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentType5Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="DocumentType5Code">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="AROI"/>
 *     <enumeration value="BOLD"/>
 *     <enumeration value="CINV"/>
 *     <enumeration value="CMCN"/>
 *     <enumeration value="CNFA"/>
 *     <enumeration value="CREN"/>
 *     <enumeration value="DEBN"/>
 *     <enumeration value="DISP"/>
 *     <enumeration value="DNFA"/>
 *     <enumeration value="HIRI"/>
 *     <enumeration value="MSIN"/>
 *     <enumeration value="SBIN"/>
 *     <enumeration value="SOAC"/>
 *     <enumeration value="TSUT"/>
 *     <enumeration value="VCHR"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "DocumentType5Code")
@XmlEnum
public enum DocumentType5Code {

    AROI,
    BOLD,
    CINV,
    CMCN,
    CNFA,
    CREN,
    DEBN,
    DISP,
    DNFA,
    HIRI,
    MSIN,
    SBIN,
    SOAC,
    TSUT,
    VCHR;

    public String value() {
        return name();
    }

    public static DocumentType5Code fromValue(String v) {
        return valueOf(v);
    }

}
