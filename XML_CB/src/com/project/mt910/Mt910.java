
package com.project.mt910;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.project.mt102.Mt102;
import com.project.mt103.Mt103;


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
 *         &lt;element name="Podaci_o_odobrenju">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SWIFT_kod_banke_poverioca" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
 *                   &lt;element name="Obracunski_racun_banke_poverioca" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
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
 *                   &lt;element name="Sifra_valute" type="{http://www.project.com/common_types}TOznaka_Valute"/>
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
    "podaciOOdobrenju"
})
@XmlRootElement(name = "mt910")
public class Mt910 {

    @XmlElement(name = "ID_poruke", required = true)
    protected String idPoruke;
    @XmlElement(name = "Podaci_o_odobrenju", required = true)
    protected Mt910 .PodaciOOdobrenju podaciOOdobrenju;

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


    public Mt910(Mt103 mt103) {
    	this.idPoruke = mt103.getIDPoruke();
    	PodaciOOdobrenju zaduzenje = new PodaciOOdobrenju();
		zaduzenje.setIDPorukeNaloga(mt103.getIDPoruke());
		zaduzenje.setSifraValute(mt103.getSifraValute());
		zaduzenje.setIznos(mt103.getUplata().getIznos());
		zaduzenje.setDatumValute(mt103.getDatumValute());
		
		zaduzenje.setObracunskiRacunBankePoverioca(mt103.getPodaciOBankama().getBankaPoverioca().getBrojRacunaBanke());
		zaduzenje.setSWIFTKodBankePoverioca(mt103.getPodaciOBankama().getBankaPoverioca().getSWIFTKod());
		this.setPodaciOOdobrenju(zaduzenje);
		
    }

	public Mt910(Mt102 mt102) {
		this.idPoruke = mt102.getIDPoruke();
		PodaciOOdobrenju zaduzenje = new PodaciOOdobrenju();
			zaduzenje.setIDPorukeNaloga(mt102.getIDPoruke());
			zaduzenje.setSifraValute(mt102.getSifraValute());
			zaduzenje.setIznos(mt102.getUkupanIznos());
			zaduzenje.setDatumValute(mt102.getDatumValute());
			
			zaduzenje.setObracunskiRacunBankePoverioca(mt102.getBankaPoverioca().getBrojRacunaBanke());
			zaduzenje.setSWIFTKodBankePoverioca(mt102.getBankaPoverioca().getSWIFTKod());
			this.setPodaciOOdobrenju(zaduzenje);
	}

	public Mt910() {
		// TODO Auto-generated constructor stub
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
     * Gets the value of the podaciOOdobrenju property.
     * 
     * @return
     *     possible object is
     *     {@link Mt910 .PodaciOOdobrenju }
     *     
     */
    public Mt910 .PodaciOOdobrenju getPodaciOOdobrenju() {
        return podaciOOdobrenju;
    }

    /**
     * Sets the value of the podaciOOdobrenju property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt910 .PodaciOOdobrenju }
     *     
     */
    public void setPodaciOOdobrenju(Mt910 .PodaciOOdobrenju value) {
        this.podaciOOdobrenju = value;
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
     *         &lt;element name="SWIFT_kod_banke_poverioca" type="{http://www.project.com/common_types}TSwift_kod_banke"/>
     *         &lt;element name="Obracunski_racun_banke_poverioca" type="{http://www.project.com/common_types}TBroj_Bankarskog_Racuna"/>
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
     *         &lt;element name="Sifra_valute" type="{http://www.project.com/common_types}TOznaka_Valute"/>
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
        "swiftKodBankePoverioca",
        "obracunskiRacunBankePoverioca",
        "idPorukeNaloga",
        "datumValute",
        "iznos",
        "sifraValute"
    })
    public static class PodaciOOdobrenju {

        @XmlElement(name = "SWIFT_kod_banke_poverioca", required = true, defaultValue = "AAAAAA00")
        protected String swiftKodBankePoverioca;
        @XmlElement(name = "Obracunski_racun_banke_poverioca", required = true, defaultValue = "000-0000000000000-00")
        protected String obracunskiRacunBankePoverioca;
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
         * Gets the value of the swiftKodBankePoverioca property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSWIFTKodBankePoverioca() {
            return swiftKodBankePoverioca;
        }

        /**
         * Sets the value of the swiftKodBankePoverioca property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSWIFTKodBankePoverioca(String value) {
            this.swiftKodBankePoverioca = value;
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
