
package com.project.common_types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.project.entities.Identifiable;


/**
 * <p>Java class for TBankarski_Racun_Klijenta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TBankarski_Racun_Klijenta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Racun" type="{http://www.project.com/common_types}TRacun_Klijenta"/>
 *         &lt;element name="Stanje" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Raspoloziva_sredstva" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valuta" type="{http://www.project.com/common_types}TOznaka_Valute"/>
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
@XmlType(name = "TBankarski_Racun_Klijenta", propOrder = {
    "racun",
    "stanje",
    "raspolozivaSredstva",
    "valuta"
})
public class TBankarskiRacunKlijenta extends Identifiable {

    @XmlElement(name = "Racun", required = true)
    protected TRacunKlijenta racun;
    @XmlElement(name = "Stanje")
    protected double stanje;
    @XmlElement(name = "Raspoloziva_sredstva")
    protected double raspolozivaSredstva;
    @XmlElement(name = "Valuta", required = true)
    protected String valuta;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the racun property.
     * 
     * @return
     *     possible object is
     *     {@link TRacunKlijenta }
     *     
     */
    public TRacunKlijenta getRacun() {
        return racun;
    }

    /**
     * Sets the value of the racun property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRacunKlijenta }
     *     
     */
    public void setRacun(TRacunKlijenta value) {
        this.racun = value;
    }

    /**
     * Gets the value of the stanje property.
     * 
     */
    public double getStanje() {
        return stanje;
    }

    /**
     * Sets the value of the stanje property.
     * 
     */
    public void setStanje(double value) {
        this.stanje = value;
    }

    /**
     * Gets the value of the raspolozivaSredstva property.
     * 
     */
    public double getRaspolozivaSredstva() {
        return raspolozivaSredstva;
    }

    /**
     * Sets the value of the raspolozivaSredstva property.
     * 
     */
    public void setRaspolozivaSredstva(double value) {
        this.raspolozivaSredstva = value;
    }

    /**
     * Gets the value of the valuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuta() {
        return valuta;
    }

    /**
     * Sets the value of the valuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuta(String value) {
        this.valuta = value;
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
