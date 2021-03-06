//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.13 at 04:10:03 AM CEST 
//


package com.project.stavka_preseka;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.entities.Identifiable;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.project.com/stavka_preseka}Stavka_preseka"/>
 *         &lt;element name="stanje_pre_transakcije" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="stanje_posle_transakcije" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="racun_klijenta" type="{http://www.project.com/common_types}TBankarski_Racun_Klijenta"/>
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
@XmlType(name = "", propOrder = {
    "stavkaPreseka",
    "stanjePreTransakcije",
    "stanjePosleTransakcije",
    "racunKlijenta"
})
@XmlRootElement(name = "Transakcija", namespace = "http://www.project.com/stavka_preseka")
public class Transakcija extends Identifiable {

    @XmlElement(name = "Stavka_preseka", namespace = "http://www.project.com/stavka_preseka", required = true)
    protected StavkaPreseka stavkaPreseka;
    @XmlElement(name = "stanje_pre_transakcije", namespace = "http://www.project.com/stavka_preseka")
    protected BigDecimal stanjePreTransakcije;
    @XmlElement(name = "stanje_posle_transakcije", namespace = "http://www.project.com/stavka_preseka")
    protected BigDecimal stanjePosleTransakcije;
    @XmlElement(name = "racun_klijenta", namespace = "http://www.project.com/stavka_preseka", required = true)
    protected TBankarskiRacunKlijenta racunKlijenta;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the stavkaPreseka property.
     * 
     * @return
     *     possible object is
     *     {@link StavkaPreseka }
     *     
     */
    public StavkaPreseka getStavkaPreseka() {
        return stavkaPreseka;
    }

    /**
     * Sets the value of the stavkaPreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link StavkaPreseka }
     *     
     */
    public void setStavkaPreseka(StavkaPreseka value) {
        this.stavkaPreseka = value;
    }

    /**
     * Gets the value of the stanjePreTransakcije property.
     * 
     */
    public BigDecimal getStanjePreTransakcije() {
        return stanjePreTransakcije;
    }

    /**
     * Sets the value of the stanjePreTransakcije property.
     * 
     */
    public void setStanjePreTransakcije(BigDecimal value) {
        this.stanjePreTransakcije = value;
    }

    /**
     * Gets the value of the stanjePosleTransakcije property.
     * 
     */
    public BigDecimal getStanjePosleTransakcije() {
        return stanjePosleTransakcije;
    }

    /**
     * Sets the value of the stanjePosleTransakcije property.
     * 
     */
    public void setStanjePosleTransakcije(BigDecimal value) {
        this.stanjePosleTransakcije = value;
    }

    /**
     * Gets the value of the racunKlijenta property.
     * 
     * @return
     *     possible object is
     *     {@link TBankarskiRacunKlijenta }
     *     
     */
    public TBankarskiRacunKlijenta getRacunKlijenta() {
        return racunKlijenta;
    }

    /**
     * Sets the value of the racunKlijenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link TBankarskiRacunKlijenta }
     *     
     */
    public void setRacunKlijenta(TBankarskiRacunKlijenta value) {
        this.racunKlijenta = value;
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
