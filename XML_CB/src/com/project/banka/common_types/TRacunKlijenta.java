
package com.project.banka.common_types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.project.banka.Identifiable;


/**
 * <p>Java class for TRacun_Klijenta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TRacun_Klijenta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Vlasnik" type="{http://www.project.com/common_types}TLice"/>
 *         &lt;element name="Broj_Racuna" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
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
@XmlType(name = "TRacun_Klijenta", propOrder = {
    "vlasnik",
    "brojRacuna"
})
public class TRacunKlijenta extends Identifiable{

    @XmlElement(name = "Vlasnik", required = true)
    protected String vlasnik;
    @XmlElement(name = "Broj_Racuna", required = true)
    protected String brojRacuna;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the vlasnik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVlasnik() {
        return vlasnik;
    }

    /**
     * Sets the value of the vlasnik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVlasnik(String value) {
        this.vlasnik = value;
    }

    /**
     * Gets the value of the brojRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojRacuna() {
        return brojRacuna;
    }

    /**
     * Sets the value of the brojRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojRacuna(String value) {
        this.brojRacuna = value;
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
