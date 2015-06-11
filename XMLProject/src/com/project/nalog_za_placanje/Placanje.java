
package com.project.nalog_za_placanje;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.project.entities.Identifiable;


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
 *         &lt;element name="Sifra_valute" type="{http://www.project.com/common_types}TOznaka_Valute"/>
 *         &lt;element ref="{http://www.project.com/nalog_za_placanje}Uplata"/>
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
    "sifraValute",
    "uplata"
})
@XmlRootElement(name = "Placanje")
public class Placanje extends Identifiable{

    @XmlElement(name = "ID_poruke", required = true)
    protected String idPoruke;
    @XmlElement(name = "Sifra_valute", required = true)
    protected String sifraValute;
    @XmlElement(name = "Uplata", required = true)
    protected Uplata uplata;

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
    
    @Override
	public Long getId() {
		return Long.parseLong(idPoruke);
	}

	@Override
	public void setId(Long value) {
		this.idPoruke = value.toString();
	}

}
