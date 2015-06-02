
package com.project.presek;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.project.stavka_preseka.StavkaPreseka;
import com.project.zaglavlje_preseka.ZaglavljePreseka;


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
 *         &lt;element ref="{http://www.project.com/zaglavlje_preseka}Zaglavlje_preseka"/>
 *         &lt;element ref="{http://www.project.com/stavka_preseka}Stavka_preseka" maxOccurs="unbounded" minOccurs="0"/>
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
    "zaglavljePreseka",
    "stavkaPreseka"
})
@XmlRootElement(name = "Presek")
public class Presek {

    @XmlElement(name = "Zaglavlje_preseka", namespace = "http://www.project.com/zaglavlje_preseka", required = true)
    protected ZaglavljePreseka zaglavljePreseka;
    @XmlElement(name = "Stavka_preseka", namespace = "http://www.project.com/stavka_preseka")
    protected List<StavkaPreseka> stavkaPreseka;

    /**
     * Gets the value of the zaglavljePreseka property.
     * 
     * @return
     *     possible object is
     *     {@link ZaglavljePreseka }
     *     
     */
    public ZaglavljePreseka getZaglavljePreseka() {
        return zaglavljePreseka;
    }

    /**
     * Sets the value of the zaglavljePreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZaglavljePreseka }
     *     
     */
    public void setZaglavljePreseka(ZaglavljePreseka value) {
        this.zaglavljePreseka = value;
    }

    /**
     * Gets the value of the stavkaPreseka property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavkaPreseka property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavkaPreseka().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StavkaPreseka }
     * 
     * 
     */
    public List<StavkaPreseka> getStavkaPreseka() {
        if (stavkaPreseka == null) {
            stavkaPreseka = new ArrayList<StavkaPreseka>();
        }
        return this.stavkaPreseka;
    }

}
