//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.09 at 09:33:15 PM EEST 
//


package ru.yandex.maps.ymaps._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.yandex.maps.ymaps._1 package. 
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

    private final static QName _GeoObject_QNAME = new QName("http://maps.yandex.ru/ymaps/1.x", "GeoObject");
    private final static QName _Ymaps_QNAME = new QName("http://maps.yandex.ru/ymaps/1.x", "ymaps");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.yandex.maps.ymaps._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link YmapsType }
     * 
     */
    public YmapsType createYmapsType() {
        return new YmapsType();
    }

    /**
     * Create an instance of {@link GeoObjectCollectionType }
     * 
     */
    public GeoObjectCollectionType createGeoObjectCollectionType() {
        return new GeoObjectCollectionType();
    }

    /**
     * Create an instance of {@link GeoObjectType }
     * 
     */
    public GeoObjectType createGeoObjectType() {
        return new GeoObjectType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeoObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://maps.yandex.ru/ymaps/1.x", name = "GeoObject")
    public JAXBElement<GeoObjectType> createGeoObject(GeoObjectType value) {
        return new JAXBElement<GeoObjectType>(_GeoObject_QNAME, GeoObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link YmapsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://maps.yandex.ru/ymaps/1.x", name = "ymaps")
    public JAXBElement<YmapsType> createYmaps(YmapsType value) {
        return new JAXBElement<YmapsType>(_Ymaps_QNAME, YmapsType.class, null, value);
    }

}
