
package com.project.banka.zaglavlje_preseka;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Broj_racuna" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
 *         &lt;element name="Datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Broj_preseka">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *               &lt;totalDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Prethodno_stanje" type="{http://www.project.com/common_types}TStanje"/>
 *         &lt;element name="Broj_promena_u_korist" type="{http://www.project.com/common_types}TBroj_Promena"/>
 *         &lt;element name="Ukupno_u_korist" type="{http://www.project.com/common_types}TStanje"/>
 *         &lt;element name="Broj_promena_na_teret" type="{http://www.project.com/common_types}TBroj_Promena"/>
 *         &lt;element name="Ukupno_na_teret" type="{http://www.project.com/common_types}TStanje"/>
 *         &lt;element name="Novo_stanje" type="{http://www.project.com/common_types}TStanje"/>
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
    "brojRacuna",
    "datumNaloga",
    "brojPreseka",
    "prethodnoStanje",
    "brojPromenaUKorist",
    "ukupnoUKorist",
    "brojPromenaNaTeret",
    "ukupnoNaTeret",
    "novoStanje"
})
@XmlRootElement(name = "Zaglavlje_preseka")
public class ZaglavljePreseka {

    @XmlElement(name = "Broj_racuna", required = true)
    protected String brojRacuna;
    @XmlElement(name = "Datum_naloga", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumNaloga;
    @XmlElement(name = "Broj_preseka")
    protected long brojPreseka;
    @XmlElement(name = "Prethodno_stanje", required = true)
    protected BigDecimal prethodnoStanje;
    @XmlElement(name = "Broj_promena_u_korist")
    protected long brojPromenaUKorist;
    @XmlElement(name = "Ukupno_u_korist", required = true)
    protected BigDecimal ukupnoUKorist;
    @XmlElement(name = "Broj_promena_na_teret")
    protected long brojPromenaNaTeret;
    @XmlElement(name = "Ukupno_na_teret", required = true)
    protected BigDecimal ukupnoNaTeret;
    @XmlElement(name = "Novo_stanje", required = true)
    protected BigDecimal novoStanje;

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
     * Gets the value of the datumNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumNaloga() {
        return datumNaloga;
    }

    /**
     * Sets the value of the datumNaloga property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumNaloga(XMLGregorianCalendar value) {
        this.datumNaloga = value;
    }

    /**
     * Gets the value of the brojPreseka property.
     * 
     */
    public long getBrojPreseka() {
        return brojPreseka;
    }

    /**
     * Sets the value of the brojPreseka property.
     * 
     */
    public void setBrojPreseka(long value) {
        this.brojPreseka = value;
    }

    /**
     * Gets the value of the prethodnoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrethodnoStanje() {
        return prethodnoStanje;
    }

    /**
     * Sets the value of the prethodnoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrethodnoStanje(BigDecimal value) {
        this.prethodnoStanje = value;
    }

    /**
     * Gets the value of the brojPromenaUKorist property.
     * 
     */
    public long getBrojPromenaUKorist() {
        return brojPromenaUKorist;
    }

    /**
     * Sets the value of the brojPromenaUKorist property.
     * 
     */
    public void setBrojPromenaUKorist(long value) {
        this.brojPromenaUKorist = value;
    }

    /**
     * Gets the value of the ukupnoUKorist property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnoUKorist() {
        return ukupnoUKorist;
    }

    /**
     * Sets the value of the ukupnoUKorist property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnoUKorist(BigDecimal value) {
        this.ukupnoUKorist = value;
    }

    /**
     * Gets the value of the brojPromenaNaTeret property.
     * 
     */
    public long getBrojPromenaNaTeret() {
        return brojPromenaNaTeret;
    }

    /**
     * Sets the value of the brojPromenaNaTeret property.
     * 
     */
    public void setBrojPromenaNaTeret(long value) {
        this.brojPromenaNaTeret = value;
    }

    /**
     * Gets the value of the ukupnoNaTeret property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnoNaTeret() {
        return ukupnoNaTeret;
    }

    /**
     * Sets the value of the ukupnoNaTeret property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnoNaTeret(BigDecimal value) {
        this.ukupnoNaTeret = value;
    }

    /**
     * Gets the value of the novoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNovoStanje() {
        return novoStanje;
    }

    /**
     * Sets the value of the novoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNovoStanje(BigDecimal value) {
        this.novoStanje = value;
    }

}
