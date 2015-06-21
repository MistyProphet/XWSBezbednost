
package com.project.mt910;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.project.common_types.TBanka;
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
 *                   &lt;element name="Banka_poverioca" type="{http://www.project.com/common_types}TBanka"/>
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
    public Mt910(Mt103 mt103) {
    
    	PodaciOOdobrenju zaduzenje = new PodaciOOdobrenju();
		zaduzenje.setIDPorukeNaloga(mt103.getIDPoruke());
		zaduzenje.setSifraValute(mt103.getSifraValute());
		zaduzenje.setIznos(mt103.getUplata().getIznos());
		zaduzenje.setDatumValute(mt103.getDatumValute());
		zaduzenje.setBankaPoverioca(mt103.getPodaciOBankama().getBankaPoverioca());
		this.setPodaciOOdobrenju(zaduzenje);
		
    }

	public Mt910(Mt102 mt102) {
		
		PodaciOOdobrenju zaduzenje = new PodaciOOdobrenju();
			zaduzenje.setIDPorukeNaloga(mt102.getIDPoruke());
			zaduzenje.setSifraValute(mt102.getSifraValute());
			zaduzenje.setIznos(mt102.getUkupanIznos());
			zaduzenje.setDatumValute(mt102.getDatumValute());
			
			zaduzenje.setBankaPoverioca(mt102.getBankaPoverioca());
			this.setPodaciOOdobrenju(zaduzenje);
	}
    public Mt910() {
		// TODO Auto-generated constructor stub
	}

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
     *         &lt;element name="Banka_poverioca" type="{http://www.project.com/common_types}TBanka"/>
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
        "bankaPoverioca",
        "idPorukeNaloga",
        "datumValute",
        "iznos",
        "sifraValute"
    })
    public static class PodaciOOdobrenju {

        @XmlElement(name = "Banka_poverioca", required = true)
        protected TBanka bankaPoverioca;
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
