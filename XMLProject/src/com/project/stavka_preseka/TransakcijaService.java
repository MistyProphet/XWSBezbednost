package com.project.stavka_preseka;

import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import misc.RESTUtil;

import com.project.banka.Banka;
import com.project.presek.Presek;
import com.project.transakcije.Transakcije;
import com.project.zaglavlje_preseka.ZaglavljePreseka;

public class TransakcijaService {
	//public static final String REST_URL = "http://localhost:8081/BaseX821/rest/";
	//public static final String schemaName = "BankaRacuni/001/Transakcije";
	
	public static Transakcije getTransakcijeZaPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka, long idBanke, long idRacuna) throws IOException, JAXBException {
		
        long begin = (brPreseka-1)*Banka.BROJ_STAVKI;

        String query = "(for $y in subsequence((for $x in //*:Transakcija where $x//*:Broj_Racuna='" + brojRacuna + "' and "
        		+ "$x//*:Datum_valute='" + date.toXMLFormat() + "'"
				+ " order by xs:integer($x/@id) return $x), " + begin + ", " + Banka.BROJ_STAVKI + ") return $y)";
        
        Transakcije results = executeSelectQuery(query, false, idBanke, idRacuna);
        return results;              


    }

    public static Presek getPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka, long idBanke, long idRacuna) throws IOException, JAXBException {
        
    	Transakcije transakcije = getTransakcijeZaPresek(date, brojRacuna, brPreseka, idBanke, idRacuna);
        
        Presek presek = new Presek();
        ZaglavljePreseka zaglavlje = new ZaglavljePreseka();
        zaglavlje.setDatumNaloga(date);
        int brojPromenaNaTeret = 0;
        int brojPromenaUKorist = 0;
        zaglavlje.setBrojPreseka(brPreseka);
        BigDecimal naTeret = new BigDecimal(0);
        BigDecimal uKorist = new BigDecimal(0);
        if (transakcije.getTransakcija().size()!=0) {
            zaglavlje.setPrethodnoStanje(transakcije.getTransakcija().get(0).getStanjePreTransakcije());
            zaglavlje.setNovoStanje(transakcije.getTransakcija().get(transakcije.getTransakcija().size()-1).getStanjePreTransakcije());
            zaglavlje.setBrojRacuna(transakcije.getTransakcija().get(0).getRacunKlijenta().getRacun().getBrojRacuna());
        }
        
        presek.getStavkaPreseka().clear();
        
        for (int i=0; i<transakcije.getTransakcija().size(); i++) {
            presek.getStavkaPreseka().add(transakcije.getTransakcija().get(i).getStavkaPreseka()); 
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
    
    /*
	 * Takes both XQuery
	 */
	public static Transakcije executeSelectQuery(String xQuery, boolean wrap, Long idBanke, Long idRacuna) throws IOException, JAXBException {

		Transakcije wrappedResults = new Transakcije();
		wrappedResults = (Transakcije) RESTUtil.doUnmarshallTransactions(xQuery, "Banka/00"+idBanke+"/Racuni/"+idRacuna+"/Transakcije", wrappedResults);
		return wrappedResults;   
	}
}
