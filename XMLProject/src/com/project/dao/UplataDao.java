package com.project.dao;

import java.util.ArrayList;

import javax.xml.datatype.XMLGregorianCalendar;

import com.project.banka.Banka;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;

public class UplataDao extends GenericDao<Placanje, Long> implements UplataDaoLocal{

	public static final String contextPath = "com.project.nalog_za_placanje";
	
	public static final String schemaName = "uplate";
	
	public UplataDao() {
		super(contextPath, schemaName);
	}
	
	@Override
	public ArrayList<NalogZaPlacanje> getPresek(String brojRacuna, long l, XMLGregorianCalendar date) {
		long begin = l*Banka.BROJ_STAVKI;
		long end = l*Banka.BROJ_STAVKI + Banka.BROJ_STAVKI;
/*		
		String q1 = "for $x in (" + begin + "," + end +") where $x/Uplata/Racun_primaoca/Broj_Racuna/TBroj_Bankarskog_Racuna  = " +
		brojRacuna + " and $x/Uplata/Datum_naloga = '" + date + "' order by $x/Uplata/Datum_naloga return $x/Placanje";
		
		this.findBy(q1, false);
		*/
		return null;
	}
	
}
