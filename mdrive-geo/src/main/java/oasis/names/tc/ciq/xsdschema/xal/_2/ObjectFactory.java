//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.09 at 09:33:15 PM EEST 
//


package oasis.names.tc.ciq.xsdschema.xal._2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the oasis.names.tc.ciq.xsdschema.xal._2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddressDetails_QNAME = new QName("urn:oasis:names:tc:ciq:xsdschema:xAL:2.0", "AddressDetails");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oasis.names.tc.ciq.xsdschema.xal._2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddressDetailsType }
     * 
     */
    public AddressDetailsType createAddressDetailsType() {
        return new AddressDetailsType();
    }

    /**
     * Create an instance of {@link LocalityType }
     * 
     */
    public LocalityType createLocalityType() {
        return new LocalityType();
    }

    /**
     * Create an instance of {@link CountryType }
     * 
     */
    public CountryType createCountryType() {
        return new CountryType();
    }

    /**
     * Create an instance of {@link ThoroughfareType }
     * 
     */
    public ThoroughfareType createThoroughfareType() {
        return new ThoroughfareType();
    }

    /**
     * Create an instance of {@link PremiseType }
     * 
     */
    public PremiseType createPremiseType() {
        return new PremiseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressDetailsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0", name = "AddressDetails")
    public JAXBElement<AddressDetailsType> createAddressDetails(AddressDetailsType value) {
        return new JAXBElement<AddressDetailsType>(_AddressDetails_QNAME, AddressDetailsType.class, null, value);
    }

}