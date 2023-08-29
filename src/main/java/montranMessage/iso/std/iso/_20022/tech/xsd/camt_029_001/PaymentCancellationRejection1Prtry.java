//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_029_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentCancellationRejection1Prtry.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="PaymentCancellationRejection1Prtry">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="AC04"/>
 *     <enumeration value="AM04"/>
 *     <enumeration value="NOAS"/>
 *     <enumeration value="NOOR"/>
 *     <enumeration value="ARDT"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "PaymentCancellationRejection1Prtry")
@XmlEnum
public enum PaymentCancellationRejection1Prtry {

    @XmlEnumValue("AC04")
    AC_04("AC04"),
    @XmlEnumValue("AM04")
    AM_04("AM04"),
    NOAS("NOAS"),
    NOOR("NOOR"),
    ARDT("ARDT");
    private final String value;

    PaymentCancellationRejection1Prtry(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentCancellationRejection1Prtry fromValue(String v) {
        for (PaymentCancellationRejection1Prtry c: PaymentCancellationRejection1Prtry.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
