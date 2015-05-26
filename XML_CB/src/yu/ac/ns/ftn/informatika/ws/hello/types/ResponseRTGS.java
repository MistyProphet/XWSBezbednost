
package yu.ac.ns.ftn.informatika.ws.hello.types;

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
 *         &lt;element name="ID_poruke">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Podaci_o_zaduzenju">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SWIFT_kod_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TSwift_kod_banke"/>
 *                   &lt;element name="Obracunski_racun_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
 *                   &lt;element name="ID_poruke_naloga">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="50"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    "idPoruke",
    "podaciOZaduzenju"
})
@XmlRootElement(name = "ResponseRTGS")
public class ResponseRTGS {

    @XmlElement(name = "ID_poruke", required = true)
    protected String idPoruke;
    @XmlElement(name = "Podaci_o_zaduzenju", required = true)
    protected ResponseRTGS.PodaciOZaduzenju podaciOZaduzenju;

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
     * Gets the value of the podaciOZaduzenju property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseRTGS.PodaciOZaduzenju }
     *     
     */
    public ResponseRTGS.PodaciOZaduzenju getPodaciOZaduzenju() {
        return podaciOZaduzenju;
    }

    /**
     * Sets the value of the podaciOZaduzenju property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseRTGS.PodaciOZaduzenju }
     *     
     */
    public void setPodaciOZaduzenju(ResponseRTGS.PodaciOZaduzenju value) {
        this.podaciOZaduzenju = value;
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
     *         &lt;element name="SWIFT_kod_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TSwift_kod_banke"/>
     *         &lt;element name="Obracunski_racun_banke_duznika" type="{http://informatika.ftn.ns.ac.yu/ws/hello/types}TBroj_Bankarskog_Racuna"/>
     *         &lt;element name="ID_poruke_naloga">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="50"/>
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "swiftKodBankeDuznika",
        "obracunskiRacunBankeDuznika",
        "idPorukeNaloga",
        "datumValute",
        "iznos",
        "sifraValute"
    })
    public static class PodaciOZaduzenju {

        @XmlElement(name = "SWIFT_kod_banke_duznika", required = true, defaultValue = "AAAAAA00")
        protected String swiftKodBankeDuznika;
        @XmlElement(name = "Obracunski_racun_banke_duznika", required = true, defaultValue = "000-0000000000000-00")
        protected String obracunskiRacunBankeDuznika;
        @XmlElement(name = "ID_poruke_naloga", required = true)
        protected String idPorukeNaloga;
        @XmlElement(name = "Datum_valute", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar datumValute;
        @XmlElement(name = "Iznos", required = true)
        protected BigDecimal iznos;
        @XmlElement(name = "Sifra_valute", required = true)
        protected String sifraValute;

        /**
         * Gets the value of the swiftKodBankeDuznika property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSWIFTKodBankeDuznika() {
            return swiftKodBankeDuznika;
        }

        /**
         * Sets the value of the swiftKodBankeDuznika property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSWIFTKodBankeDuznika(String value) {
            this.swiftKodBankeDuznika = value;
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
         * Gets the value of the idPorukeNaloga property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIDPorukeNaloga() {
            return idPorukeNaloga;
        }

        /**
         * Sets the value of the idPorukeNaloga property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIDPorukeNaloga(String value) {
            this.idPorukeNaloga = value;
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
