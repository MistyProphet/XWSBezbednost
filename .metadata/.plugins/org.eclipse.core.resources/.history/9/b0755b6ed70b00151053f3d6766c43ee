
package com.project.mt103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.project.nalog_za_placanje.Uplata;


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
 *         &lt;element name="ID_poruke">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Podaci_o_bankama">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SWIFT_banke_duznika" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
 *                   &lt;element name="Obracunski_racun_banke_duznika" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
 *                   &lt;element name="SWIFT_banke_poverioca" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
 *                   &lt;element name="Obracunski_racun_banke_poverioca" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Sifra_valute" type="{http://www.project.com/common_types}TOznaka_Valute"/>
 *         &lt;element ref="{http://www.project.com/nalog_za_placanje}Uplata"/>
 *         &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    "idPoruke",
    "podaciOBankama",
    "sifraValute",
    "uplata",
    "datumValute"
})
@XmlRootElement(name = "mt103")
public class Mt103 {

    @XmlElement(name = "ID_poruke", required = true)
    protected String idPoruke;
    @XmlElement(name = "Podaci_o_bankama", required = true)
    protected Mt103 .PodaciOBankama podaciOBankama;
    @XmlElement(name = "Sifra_valute", required = true)
    protected String sifraValute;
    @XmlElement(name = "Uplata", namespace = "http://www.project.com/nalog_za_placanje", required = true)
    protected Uplata uplata;
    @XmlElement(name = "Datum_valute", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumValute;

    /**
     * Gets the value of the idPoruke property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDPoruke() {
        return idPoruke;
    }

    /**
     * Sets the value of the idPoruke property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDPoruke(String value) {
        this.idPoruke = value;
    }

    /**
     * Gets the value of the podaciOBankama property.
     * 
     * @return
     *     possible object is
     *     {@link Mt103 .PodaciOBankama }
     *     
     */
    public Mt103 .PodaciOBankama getPodaciOBankama() {
        return podaciOBankama;
    }

    /**
     * Sets the value of the podaciOBankama property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt103 .PodaciOBankama }
     *     
     */
    public void setPodaciOBankama(Mt103 .PodaciOBankama value) {
        this.podaciOBankama = value;
    }

    /**
     * Gets the value of the sifraValute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSifraValute() {
        return sifraValute;
    }

    /**
     * Sets the value of the sifraValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSifraValute(String value) {
        this.sifraValute = value;
    }

    /**
     * Gets the value of the uplata property.
     * 
     * @return
     *     possible object is
     *     {@link Uplata }
     *     
     */
    public Uplata getUplata() {
        return uplata;
    }

    /**
     * Sets the value of the uplata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Uplata }
     *     
     */
    public void setUplata(Uplata value) {
        this.uplata = value;
    }

    /**
     * Gets the value of the datumValute property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumValute() {
        return datumValute;
    }

    /**
     * Sets the value of the datumValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumValute(XMLGregorianCalendar value) {
        this.datumValute = value;
    }


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
     *         &lt;element name="SWIFT_banke_duznika" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
     *         &lt;element name="Obracunski_racun_banke_duznika" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
     *         &lt;element name="SWIFT_banke_poverioca" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
     *         &lt;element name="Obracunski_racun_banke_poverioca" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
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
        "swiftBankeDuznika",
        "obracunskiRacunBankeDuznika",
        "swiftBankePoverioca",
        "obracunskiRacunBankePoverioca"
    })
    public static class PodaciOBankama {

        @XmlElement(name = "SWIFT_banke_duznika", required = true, defaultValue = "AAAAAA00")
        protected String swiftBankeDuznika;
        @XmlElement(name = "Obracunski_racun_banke_duznika", required = true, defaultValue = "000-0000000000000-00")
        protected String obracunskiRacunBankeDuznika;
        @XmlElement(name = "SWIFT_banke_poverioca", required = true, defaultValue = "AAAAAA00")
        protected String swiftBankePoverioca;
        @XmlElement(name = "Obracunski_racun_banke_poverioca", required = true, defaultValue = "000-0000000000000-00")
        protected String obracunskiRacunBankePoverioca;

        /**
         * Gets the value of the swiftBankeDuznika property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSWIFTBankeDuznika() {
            return swiftBankeDuznika;
        }

        /**
         * Sets the value of the swiftBankeDuznika property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSWIFTBankeDuznika(String value) {
            this.swiftBankeDuznika = value;
        }

        /**
         * Gets the value of the obracunskiRacunBankeDuznika property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getObracunskiRacunBankeDuznika() {
            return obracunskiRacunBankeDuznika;
        }

        /**
         * Sets the value of the obracunskiRacunBankeDuznika property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setObracunskiRacunBankeDuznika(String value) {
            this.obracunskiRacunBankeDuznika = value;
        }

        /**
         * Gets the value of the swiftBankePoverioca property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSWIFTBankePoverioca() {
            return swiftBankePoverioca;
        }

        /**
         * Sets the value of the swiftBankePoverioca property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSWIFTBankePoverioca(String value) {
            this.swiftBankePoverioca = value;
        }

        /**
         * Gets the value of the obracunskiRacunBankePoverioca property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getObracunskiRacunBankePoverioca() {
            return obracunskiRacunBankePoverioca;
        }

        /**
         * Sets the value of the obracunskiRacunBankePoverioca property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setObracunskiRacunBankePoverioca(String value) {
            this.obracunskiRacunBankePoverioca = value;
        }

    }

}
