//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.17 at 02:46:20 PM CEST 
//


package com.project.transakcije;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.project.stavka_preseka.Transakcija;


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
 *         &lt;element ref="{http://www.project.com/stavka_preseka}Transakcija" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "transakcija"
})
@XmlRootElement(name = "Transakcije", namespace = "http://www.project.com/transakcije")
public class Transakcije {

    @XmlElement(name = "Transakcija", namespace = "http://www.project.com/stavka_preseka")
    protected List<Transakcija> transakcija;

    /**
     * Gets the value of the transakcija property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transakcija property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransakcija().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Transakcija }
     * 
     * 
     */
    public List<Transakcija> getTransakcija() {
        if (transakcija == null) {
            transakcija = new ArrayList<Transakcija>();
        }
        return this.transakcija;
    }

}
