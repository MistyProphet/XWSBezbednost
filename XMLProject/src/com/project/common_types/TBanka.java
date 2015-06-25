
package com.project.common_types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TBanka complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TBanka">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Naziv_banke">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SWIFTKod" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
 *         &lt;element name="Broj_racuna_banke">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.project.com/common_types}TBroj_Racuna_Banke">
 *               &lt;pattern value="\d{3}-\d{13}-\d{2}"/>
 *               &lt;length value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TBanka", propOrder = {
    "nazivBanke",
    "swiftKod",
    "brojRacunaBanke"
})
public class TBanka {

    @XmlElement(name = "Naziv_banke", required = true)
    protected String nazivBanke;
    @XmlElement(name = "SWIFTKod", required = true)
    protected String swiftKod;
    @XmlElement(name = "Broj_racuna_banke", required = true)
    protected String brojRacunaBanke;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the nazivBanke property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivBanke() {
        return nazivBanke;
    }

    /**
     * Sets the value of the nazivBanke property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivBanke(String value) {
        this.nazivBanke = value;
    }

    /**
     * Gets the value of the swiftKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSWIFTKod() {
        return swiftKod;
    }

    /**
     * Sets the value of the swiftKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSWIFTKod(String value) {
        this.swiftKod = value;
    }

    /**
     * Gets the value of the brojRacunaBanke property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojRacunaBanke() {
        return brojRacunaBanke;
    }

    /**
     * Sets the value of the brojRacunaBanke property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojRacunaBanke(String value) {
        this.brojRacunaBanke = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

}
