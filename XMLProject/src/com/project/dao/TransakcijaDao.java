package com.project.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.project.banka.Banka;
import com.project.presek.Presek;
import com.project.stavka_preseka.Transakcija;
import com.project.zaglavlje_preseka.ZaglavljePreseka;

public class TransakcijaDao extends GenericDao<Transakcija, Long> implements TransakcijaDaoLocal {

	public static final String contextPath = "com.project.stavka_preseka";
	
	public static final String schemaName = "transakcija";
	
	public TransakcijaDao() {
		super(contextPath, schemaName);
	}
	
	public List<Transakcija> getTransakcijeZaPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka) throws IOException, JAXBException {
	
		long begin = brPreseka*Banka.BROJ_STAVKI;
		List<Transakcija> transakcije = null;
		String query = "for $y in subsequence((for $x in transakcija where $x/racun_klijenta/Racun/Broj_racuna  = " +
		brojRacuna + " and $x/Stavka_preseka/Datum_valute = '" + date + "' order by id return $x), " + begin + ", " + Banka.BROJ_STAVKI + ")"
				+ " return $y";
		
	/*	
		let $sorted-people :=
				   for $person in collection($collection)/person
				   order by $person/last-name/text()
				   return $person
				 
				for $person at $count in subsequence($sorted-people, $start, $records)
				return
				   <li>$person/last-name/text()</li>
*/				   
		transakcije = em.executeSelectQuery(query, false);
		
		if (transakcije==null || transakcije.size()==0) {
			transakcije = new ArrayList<Transakcija>();
		}
		
		return transakcije;
	}

	public Presek getPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka) throws IOException, JAXBException {
		
		List<Transakcija> transakcije = getTransakcijeZaPresek(date, brojRacuna, brPreseka);
		
		Presek presek = new Presek();
		ZaglavljePreseka zaglavlje = new ZaglavljePreseka();
		zaglavlje.setDatumNaloga(date);
		int brojPromenaNaTeret = 0;
		int brojPromenaUKorist = 0;
		zaglavlje.setBrojPreseka(brPreseka);
		BigDecimal naTeret = new BigDecimal(0);
		BigDecimal uKorist = new BigDecimal(0);
		if (transakcije.size()!=0) {
			zaglavlje.setPrethodnoStanje(transakcije.get(0).getStanjePreTransakcije());
			zaglavlje.setNovoStanje(transakcije.get(transakcije.size()-1).getStanjePreTransakcije());
			zaglavlje.setBrojRacuna(transakcije.get(0).getRacunKlijenta().getRacun().getBrojRacuna());
		}
		
		presek.getStavkaPreseka().clear();
		
		for (int i=0; i<transakcije.size(); i++) {
			presek.getStavkaPreseka().add(transakcije.get(i).getStavkaPreseka()); 
			if (presek.getStavkaPreseka().get(i).getSmer().toUpperCase().equals("U")) {
				uKorist.add(presek.getStavkaPreseka().get(i).getUplata().getIznos());
				brojPromenaUKorist++;
			} else {
				naTeret.add(presek.getStavkaPreseka().get(i).getUplata().getIznos());
				brojPromenaNaTeret++;
			}
		}
		
		zaglavlje.setBrojPromenaNaTeret(brojPromenaNaTeret);
		zaglavlje.setBrojPromenaUKorist(brojPromenaUKorist);
		zaglavlje.setUkupnoNaTeret(naTeret);
		zaglavlje.setUkupnoUKorist(uKorist);
		presek.setZaglavljePreseka(zaglavlje);
		return presek;
		
	}	
}
