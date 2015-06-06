
package com.project.nalog_za_placanje;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.project.common_types.TRacunKlijenta;


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
 *         &lt;element name="Racun_duznika" type="{http://www.project.com/common_types}TRacun_Klijenta"/>
 *         &lt;element name="Svrha_placanja">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Racun_primaoca" type="{http://www.project.com/common_types}TRacun_Klijenta"/>
 *         &lt;element name="Datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Model_zaduzenja" type="{http://www.project.com/common_types}TModel"/>
 *         &lt;element name="Poziv_na_broj_zaduzenja" type="{http://www.project.com/common_types}TPoziv_na_broj"/>
 *         &lt;element name="Model_odobrenja" type="{http://www.project.com/common_types}TModel"/>
 *         &lt;element name="Poziv_na_broj_odobrenja" type="{http://www.project.com/common_types}TPoziv_na_broj"/>
 *         &lt;element name="Iznos">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
    "racunDuznika",
    "svrhaPlacanja",
    "racunPrimaoca",
    "datumNaloga",
    "modelZaduzenja",
    "pozivNaBrojZaduzenja",
    "modelOdobrenja",
    "pozivNaBrojOdobrenja",
    "iznos"
})
@XmlRootElement(name = "Uplata")
public class Uplata {

    @XmlElement(name = "Racun_duznika", required = true)
    protected TRacunKlijenta racunDuznika;
    @XmlElement(name = "Svrha_placanja", required = true)
    protected String svrhaPlacanja;
    @XmlElement(name = "Racun_primaoca", required = true)
    protected TRacunKlijenta racunPrimaoca;
    @XmlElement(name = "Datum_naloga", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumNaloga;
    @XmlElement(name = "Model_zaduzenja")
    protected long modelZaduzenja;
    @XmlElement(name = "Poziv_na_broj_zaduzenja", required = true)
    protected String pozivNaBrojZaduzenja;
    @XmlElement(name = "Model_odobrenja")
    protected long modelOdobrenja;
    @XmlElement(name = "Poziv_na_broj_odobrenja", required = true)
    protected String pozivNaBrojOdobrenja;
    @XmlElement(name = "Iznos", required = true)
    protected BigDecimal iznos;

    /**
     * Gets the value of the racunDuznika property.
     * 
     * @return
     *     possible object is
     *     {@link TRacunKlijenta }
     *     
     */
    public TRacunKlijenta getRacunDuznika() {
        return racunDuznika;
    }

    /**
     * Sets the value of the racunDuznika property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRacunKlijenta }
     *     
     */
    public void setRacunDuznika(TRacunKlijenta value) {
        this.racunDuznika = value;
    }

    /**
     * Gets the value of the svrhaPlacanja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSvrhaPlacanja() {
        return svrhaPlacanja;
    }

    /**
     * Sets the value of the svrhaPlacanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSvrhaPlacanja(String value) {
        this.svrhaPlacanja = value;
    }

    /**
     * Gets the value of the racunPrimaoca property.
     * 
     * @return
     *     possible object is
     *     {@link TRacunKlijenta }
     *     
     */
    public TRacunKlijenta getRacunPrimaoca() {
        return racunPrimaoca;
    }

    /**
     * Sets the value of the racunPrimaoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRacunKlijenta }
     *     
     */
    public void setRacunPrimaoca(TRacunKlijenta value) {
        this.racunPrimaoca = value;
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
     * Gets the value of the modelZaduzenja property.
     * 
     */
    public long getModelZaduzenja() {
        return modelZaduzenja;
    }

    /**
     * Sets the value of the modelZaduzenja property.
     * 
     */
    public void setModelZaduzenja(long value) {
        this.modelZaduzenja = value;
    }

    /**
     * Gets the value of the pozivNaBrojZaduzenja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPozivNaBrojZaduzenja() {
        return pozivNaBrojZaduzenja;
    }

    /**
     * Sets the value of the pozivNaBrojZaduzenja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPozivNaBrojZaduzenja(String value) {
        this.pozivNaBrojZaduzenja = value;
    }

    /**
     * Gets the value of the modelOdobrenja property.
     * 
     */
    public long getModelOdobrenja() {
        return modelOdobrenja;
    }

    /**
     * Sets the value of the modelOdobrenja property.
     * 
     */
    public void setModelOdobrenja(long value) {
        this.modelOdobrenja = value;
    }

    /**
     * Gets the value of the pozivNaBrojOdobrenja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPozivNaBrojOdobrenja() {
        return pozivNaBrojOdobrenja;
    }

    /**
     * Sets the value of the pozivNaBrojOdobrenja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPozivNaBrojOdobrenja(String value) {
        this.pozivNaBrojOdobrenja = value;
    }

    /**
     * Gets the value of the iznos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIznos() {
        return iznos;
    }

    /**
     * Sets the value of the iznos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIznos(BigDecimal value) {
        this.iznos = value;
    }

}
