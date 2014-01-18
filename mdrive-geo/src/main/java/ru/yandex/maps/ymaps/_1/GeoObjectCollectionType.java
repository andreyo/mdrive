//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.09 at 09:33:15 PM EEST 
//


package ru.yandex.maps.ymaps._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.opengis.gml.FeatureMemberType;
import net.opengis.gml.MetaDataPropertyType;


/**
 * <p>Java class for GeoObjectCollectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeoObjectCollectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}metaDataProperty"/>
 *         &lt;element ref="{http://www.opengis.net/gml}featureMember" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeoObjectCollectionType", propOrder = {
    "metaDataProperty",
    "featureMember"
})
public class GeoObjectCollectionType {

    @XmlElement(namespace = "http://www.opengis.net/gml", required = true)
    protected MetaDataPropertyType metaDataProperty;
    @XmlElement(namespace = "http://www.opengis.net/gml")
    protected List<FeatureMemberType> featureMember;

    /**
     * Gets the value of the metaDataProperty property.
     * 
     * @return
     *     possible object is
     *     {@link MetaDataPropertyType }
     *     
     */
    public MetaDataPropertyType getMetaDataProperty() {
        return metaDataProperty;
    }

    /**
     * Sets the value of the metaDataProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaDataPropertyType }
     *     
     */
    public void setMetaDataProperty(MetaDataPropertyType value) {
        this.metaDataProperty = value;
    }

    /**
     * Gets the value of the featureMember property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureMember property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureMember().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FeatureMemberType }
     * 
     * 
     */
    public List<FeatureMemberType> getFeatureMember() {
        if (featureMember == null) {
            featureMember = new ArrayList<FeatureMemberType>();
        }
        return this.featureMember;
    }

}