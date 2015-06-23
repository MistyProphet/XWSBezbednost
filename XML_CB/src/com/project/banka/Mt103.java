
package com.project.banka;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.project.banka.common_types.TBanka;
import com.project.banka.Uplata;


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
 *                   &lt;element name="Banka_Duznika" type="{http://www.project.com/common_types}TBanka"/>
 *                   &lt;element name="Banka_Poverioca" type="{http://www.project.com/common_types}TBanka"/>
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
public class Mt103 extends Identifiable{

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
     *         &lt;element name="Banka_Duznika" type="{http://www.project.com/common_types}TBanka"/>
     *         &lt;element name="Banka_Poverioca" type="{http://www.project.com/common_types}TBanka"/>
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
        "bankaDuznika",
        "bankaPoverioca"
    })
    public static class PodaciOBankama {

        @XmlElement(name = "Banka_Duznika", required = true)
        protected TBanka bankaDuznika;
        @XmlElement(name = "Banka_Poverioca", required = true)
        protected TBanka bankaPoverioca;

        /**
         * Gets the value of the bankaDuznika property.
         * 
         * @return
         *     possible object is
         *     {@link TBanka }
         *     
         */
        public TBanka getBankaDuznika() {
            return bankaDuznika;
        }

        /**
         * Sets the value of the bankaDuznika property.
         * 
         * @param value
         *     allowed object is
         *     {@link TBanka }
         *     
         */
        public void setBankaDuznika(TBanka value) {
            this.bankaDuznika = value;
        }

        /**
         * Gets the value of the bankaPoverioca property.
         * 
         * @return
         *     possible object is
         *     {@link TBanka }
         *     
         */
        public TBanka getBankaPoverioca() {
            return bankaPoverioca;
        }

        /**
         * Sets the value of the bankaPoverioca property.
         * 
         * @param value
         *     allowed object is
         *     {@link TBanka }
         *     
         */
        public void setBankaPoverioca(TBanka value) {
            this.bankaPoverioca = value;
        }

    }


    @Override
	public Long getId() {
		return Long.parseLong(idPoruke);
	}

	@Override
	public void setId(Long value) {
		this.idPoruke = value.toString();
	}

}
