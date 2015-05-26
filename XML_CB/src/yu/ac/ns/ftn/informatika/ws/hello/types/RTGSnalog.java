
package yu.ac.ns.ftn.informatika.ws.hello.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RTGSnalog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RTGSnalog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_poruke">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Podaci_o_bankama">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SWIFT_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TSwift_kod_banke"/>
 *                   &lt;element name="Obracunski_racun_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
 *                   &lt;element name="SWIFT_banke_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TSwift_kod_banke"/>
 *                   &lt;element name="Obracunski_racun_banke_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Podaci_o_uplati">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Opsti_deo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Duznik" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TLice"/>
 *                             &lt;element name="Svrha_placanja">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="255"/>
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Poverilac" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TLice"/>
 *                             &lt;element name="Datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Uplata">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Racun_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
 *                             &lt;element name="Model_zaduzenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TModel"/>
 *                             &lt;element name="Poziv_na_broj_zaduzenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TPoziv_na_broj"/>
 *                             &lt;element name="Racun_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
 *                             &lt;element name="Model_odobrenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TModel"/>
 *                             &lt;element name="Poziv_na_broj_odobrenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TPoziv_na_broj"/>
 *                             &lt;element name="Iznos">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;totalDigits value="15"/>
 *                                   &lt;fractionDigits value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Sifra_valute">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;length value="3"/>
 *                                   &lt;pattern value="[A-Z]{3}|[0-9]{3}"/>
 *                                   &lt;enumeration value="EUR"/>
 *                                   &lt;enumeration value="RSD"/>
 *                                   &lt;enumeration value="GBP"/>
 *                                   &lt;enumeration value="CHF"/>
 *                                   &lt;enumeration value="HUF"/>
 *                                   &lt;enumeration value="JPY"/>
 *                                   &lt;enumeration value="TRY"/>
 *                                   &lt;enumeration value="USD"/>
 *                                   &lt;enumeration value="AUD"/>
 *                                   &lt;enumeration value="036"/>
 *                                   &lt;enumeration value="840"/>
 *                                   &lt;enumeration value="949"/>
 *                                   &lt;enumeration value="392"/>
 *                                   &lt;enumeration value="348"/>
 *                                   &lt;enumeration value="756"/>
 *                                   &lt;enumeration value="826"/>
 *                                   &lt;enumeration value="941"/>
 *                                   &lt;enumeration value="978"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "RTGSnalog", propOrder = {
    "idPoruke",
    "podaciOBankama",
    "podaciOUplati"
})
public class RTGSnalog {

    @XmlElement(name = "ID_poruke", required = true)
    protected String idPoruke;
    @XmlElement(name = "Podaci_o_bankama", required = true)
    protected RTGSnalog.PodaciOBankama podaciOBankama;
    @XmlElement(name = "Podaci_o_uplati", required = true)
    protected RTGSnalog.PodaciOUplati podaciOUplati;

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
     *     {@link RTGSnalog.PodaciOBankama }
     *     
     */
    public RTGSnalog.PodaciOBankama getPodaciOBankama() {
        return podaciOBankama;
    }

    /**
     * Sets the value of the podaciOBankama property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTGSnalog.PodaciOBankama }
     *     
     */
    public void setPodaciOBankama(RTGSnalog.PodaciOBankama value) {
        this.podaciOBankama = value;
    }

    /**
     * Gets the value of the podaciOUplati property.
     * 
     * @return
     *     possible object is
     *     {@link RTGSnalog.PodaciOUplati }
     *     
     */
    public RTGSnalog.PodaciOUplati getPodaciOUplati() {
        return podaciOUplati;
    }

    /**
     * Sets the value of the podaciOUplati property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTGSnalog.PodaciOUplati }
     *     
     */
    public void setPodaciOUplati(RTGSnalog.PodaciOUplati value) {
        this.podaciOUplati = value;
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
     *         &lt;element name="SWIFT_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TSwift_kod_banke"/>
     *         &lt;element name="Obracunski_racun_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
     *         &lt;element name="SWIFT_banke_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TSwift_kod_banke"/>
     *         &lt;element name="Obracunski_racun_banke_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
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
     *         &lt;element name="Opsti_deo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Duznik" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TLice"/>
     *                   &lt;element name="Svrha_placanja">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="255"/>
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Poverilac" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TLice"/>
     *                   &lt;element name="Datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Uplata">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Racun_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
     *                   &lt;element name="Model_zaduzenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TModel"/>
     *                   &lt;element name="Poziv_na_broj_zaduzenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TPoziv_na_broj"/>
     *                   &lt;element name="Racun_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
     *                   &lt;element name="Model_odobrenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TModel"/>
     *                   &lt;element name="Poziv_na_broj_odobrenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TPoziv_na_broj"/>
     *                   &lt;element name="Iznos">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;totalDigits value="15"/>
     *                         &lt;fractionDigits value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Sifra_valute">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;length value="3"/>
     *                         &lt;pattern value="[A-Z]{3}|[0-9]{3}"/>
     *                         &lt;enumeration value="EUR"/>
     *                         &lt;enumeration value="RSD"/>
     *                         &lt;enumeration value="GBP"/>
     *                         &lt;enumeration value="CHF"/>
     *                         &lt;enumeration value="HUF"/>
     *                         &lt;enumeration value="JPY"/>
     *                         &lt;enumeration value="TRY"/>
     *                         &lt;enumeration value="USD"/>
     *                         &lt;enumeration value="AUD"/>
     *                         &lt;enumeration value="036"/>
     *                         &lt;enumeration value="840"/>
     *                         &lt;enumeration value="949"/>
     *                         &lt;enumeration value="392"/>
     *                         &lt;enumeration value="348"/>
     *                         &lt;enumeration value="756"/>
     *                         &lt;enumeration value="826"/>
     *                         &lt;enumeration value="941"/>
     *                         &lt;enumeration value="978"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
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
        "opstiDeo",
        "uplata"
    })
    public static class PodaciOUplati {

        @XmlElement(name = "Opsti_deo", required = true)
        protected RTGSnalog.PodaciOUplati.OpstiDeo opstiDeo;
        @XmlElement(name = "Uplata", required = true)
        protected RTGSnalog.PodaciOUplati.Uplata uplata;

        /**
         * Gets the value of the opstiDeo property.
         * 
         * @return
         *     possible object is
         *     {@link RTGSnalog.PodaciOUplati.OpstiDeo }
         *     
         */
        public RTGSnalog.PodaciOUplati.OpstiDeo getOpstiDeo() {
            return opstiDeo;
        }

        /**
         * Sets the value of the opstiDeo property.
         * 
         * @param value
         *     allowed object is
         *     {@link RTGSnalog.PodaciOUplati.OpstiDeo }
         *     
         */
        public void setOpstiDeo(RTGSnalog.PodaciOUplati.OpstiDeo value) {
            this.opstiDeo = value;
        }

        /**
         * Gets the value of the uplata property.
         * 
         * @return
         *     possible object is
         *     {@link RTGSnalog.PodaciOUplati.Uplata }
         *     
         */
        public RTGSnalog.PodaciOUplati.Uplata getUplata() {
            return uplata;
        }

        /**
         * Sets the value of the uplata property.
         * 
         * @param value
         *     allowed object is
         *     {@link RTGSnalog.PodaciOUplati.Uplata }
         *     
         */
        public void setUplata(RTGSnalog.PodaciOUplati.Uplata value) {
            this.uplata = value;
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
         *         &lt;element name="Duznik" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TLice"/>
         *         &lt;element name="Svrha_placanja">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="255"/>
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Poverilac" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TLice"/>
         *         &lt;element name="Datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
            "duznik",
            "svrhaPlacanja",
            "poverilac",
            "datumNaloga",
            "datumValute"
        })
        public static class OpstiDeo {

            @XmlElement(name = "Duznik", required = true)
            protected String duznik;
            @XmlElement(name = "Svrha_placanja", required = true)
            protected String svrhaPlacanja;
            @XmlElement(name = "Poverilac", required = true)
            protected String poverilac;
            @XmlElement(name = "Datum_naloga", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar datumNaloga;
            @XmlElement(name = "Datum_valute", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar datumValute;

            /**
             * Gets the value of the duznik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDuznik() {
                return duznik;
            }

            /**
             * Sets the value of the duznik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDuznik(String value) {
                this.duznik = value;
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
             * Gets the value of the poverilac property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPoverilac() {
                return poverilac;
            }

            /**
             * Sets the value of the poverilac property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPoverilac(String value) {
                this.poverilac = value;
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
         *         &lt;element name="Racun_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
         *         &lt;element name="Model_zaduzenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TModel"/>
         *         &lt;element name="Poziv_na_broj_zaduzenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TPoziv_na_broj"/>
         *         &lt;element name="Racun_poverioca" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
         *         &lt;element name="Model_odobrenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TModel"/>
         *         &lt;element name="Poziv_na_broj_odobrenja" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TPoziv_na_broj"/>
         *         &lt;element name="Iznos">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;totalDigits value="15"/>
         *               &lt;fractionDigits value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Sifra_valute">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;length value="3"/>
         *               &lt;pattern value="[A-Z]{3}|[0-9]{3}"/>
         *               &lt;enumeration value="EUR"/>
         *               &lt;enumeration value="RSD"/>
         *               &lt;enumeration value="GBP"/>
         *               &lt;enumeration value="CHF"/>
         *               &lt;enumeration value="HUF"/>
         *               &lt;enumeration value="JPY"/>
         *               &lt;enumeration value="TRY"/>
         *               &lt;enumeration value="USD"/>
         *               &lt;enumeration value="AUD"/>
         *               &lt;enumeration value="036"/>
         *               &lt;enumeration value="840"/>
         *               &lt;enumeration value="949"/>
         *               &lt;enumeration value="392"/>
         *               &lt;enumeration value="348"/>
         *               &lt;enumeration value="756"/>
         *               &lt;enumeration value="826"/>
         *               &lt;enumeration value="941"/>
         *               &lt;enumeration value="978"/>
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
            "modelZaduzenja",
            "pozivNaBrojZaduzenja",
            "racunPoverioca",
            "modelOdobrenja",
            "pozivNaBrojOdobrenja",
            "iznos",
            "sifraValute"
        })
        public static class Uplata {

            @XmlElement(name = "Racun_duznika", required = true, defaultValue = "000-0000000000000-00")
            protected String racunDuznika;
            @XmlElement(name = "Model_zaduzenja")
            protected long modelZaduzenja;
            @XmlElement(name = "Poziv_na_broj_zaduzenja", required = true)
            protected String pozivNaBrojZaduzenja;
            @XmlElement(name = "Racun_poverioca", required = true, defaultValue = "000-0000000000000-00")
            protected String racunPoverioca;
            @XmlElement(name = "Model_odobrenja")
            protected long modelOdobrenja;
            @XmlElement(name = "Poziv_na_broj_odobrenja", required = true)
            protected String pozivNaBrojOdobrenja;
            @XmlElement(name = "Iznos", required = true)
            protected BigDecimal iznos;
            @XmlElement(name = "Sifra_valute", required = true)
            protected String sifraValute;

            /**
             * Gets the value of the racunDuznika property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRacunDuznika() {
                return racunDuznika;
            }

            /**
             * Sets the value of the racunDuznika property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRacunDuznika(String value) {
                this.racunDuznika = value;
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
             * Gets the value of the racunPoverioca property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRacunPoverioca() {
                return racunPoverioca;
            }

            /**
             * Sets the value of the racunPoverioca property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRacunPoverioca(String value) {
                this.racunPoverioca = value;
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

        }

    }

}
