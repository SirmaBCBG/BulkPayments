//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.1 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package montranMessage.iso.std.iso._20022.tech.xsd.camt_027_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnderlyingTransaction4Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="UnderlyingTransaction4Choice">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="IntrBk" type="{urn:iso:std:iso:20022:tech:xsd:camt.027.001.06}UnderlyingPaymentTransaction3"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnderlyingTransaction4Choice", propOrder = {
    "intrBk"
})
public class UnderlyingTransaction4Choice {

    @XmlElement(name = "IntrBk")
    protected UnderlyingPaymentTransaction3 intrBk;

    /**
     * Gets the value of the intrBk property.
     * 
     * @return
     *     possible object is
     *     {@link UnderlyingPaymentTransaction3 }
     *     
     */
    public UnderlyingPaymentTransaction3 getIntrBk() {
        return intrBk;
    }

    /**
     * Sets the value of the intrBk property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnderlyingPaymentTransaction3 }
     *     
     */
    public void setIntrBk(UnderlyingPaymentTransaction3 value) {
        this.intrBk = value;
    }

}
