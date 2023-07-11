//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.25 at 11:54:17 AM EEST 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_056_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancellationReason4Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CancellationReason4Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AGNT"/>
 *     &lt;enumeration value="CURR"/>
 *     &lt;enumeration value="CUST"/>
 *     &lt;enumeration value="CUTA"/>
 *     &lt;enumeration value="DUPL"/>
 *     &lt;enumeration value="UPAY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CancellationReason4Code")
@XmlEnum
public enum CancellationReason4Code {


    /**
     * Agent in the payment workflow is incorrect.
     * 
     */
    AGNT,

    /**
     * Currency of the payment is incorrect.
     * 
     */
    CURR,

    /**
     * Cancellation requested by the Debtor.
     * 
     */
    CUST,

    /**
     * Cancellation requested because an investigation request has been received and no remediation is possible.
     * 
     */
    CUTA,

    /**
     * Payment is a duplicate of another payment.
     * 
     */
    DUPL,

    /**
     * Payment is not justified.
     * 
     */
    UPAY;

    public String value() {
        return name();
    }

    public static CancellationReason4Code fromValue(String v) {
        return valueOf(v);
    }

}
