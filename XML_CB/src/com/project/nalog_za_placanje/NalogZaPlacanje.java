
package com.project.nalog_za_placanje;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.project.banka.Identifiable;


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
 *         &lt;element ref="{http://www.project.com/nalog_za_placanje}Placanje"/>
 *         &lt;element name="Datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Hitno" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "placanje",
    "datumValute",
    "hitno"
})
@XmlRootElement(name = "Nalog_Za_Placanje")
public class NalogZaPlacanje extends Identifiable{

    @XmlElement(name = "Placanje", required = true)
    protected Placanje placanje;
    @XmlElement(name = "Datum_valute", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumValute;
    @XmlElement(name = "Hitno")
    protected boolean hitno;

    /**
     * Gets the value of the placanje property.
     * 
     * @return
     *     possible object is
     *     {@link Placanje }
     *     
     */
    public Placanje getPlacanje() {
        return placanje;
    }

    /**
     * Sets the value of the placanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Placanje }
     *     
     */
    public void setPlacanje(Placanje value) {
        this.placanje = value;
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
     * Gets the value of the hitno property.
     * 
     */
    public boolean isHitno() {
        return hitno;
    }

    /**
     * Sets the value of the hitno property.
     * 
     */
    public void setHitno(boolean value) {
        this.hitno = value;
    }
    
    @Override
	public Long getId() {
		return placanje.getId();
	}

	@Override
	public void setId(Long value) {
		if(placanje == null){
			placanje = new Placanje();
		}
		this.placanje.setId(value);
	}
}
