package com.project.stavka_preseka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import misc.RESTUtil;
import misc.RequestMethod;

import org.basex.rest.Result;
import org.basex.rest.Results;
import org.w3c.dom.Node;

import com.project.banka.Banka;
import com.project.presek.Presek;
import com.project.zaglavlje_preseka.ZaglavljePreseka;

public class TransakcijaService {
	public static final String REST_URL = "http://localhost:8081/BaseX821/rest/";
	public static final String schemaName = "BankaRacuni/001/Transakcije";
	
	public static List<Transakcija> getTransakcijeZaPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka) throws IOException, JAXBException {
		
        long begin = (brPreseka-1)*Banka.BROJ_STAVKI;

        String query = "(for $y in subsequence((for $x in //*:Transakcija where $x//*:Broj_Racuna='" + brojRacuna + "' and "
        		+ "$x//*:Datum_valute='" + date.toXMLFormat() + "'"
				+ " order by $x/@id return $x), " + begin + ", " + Banka.BROJ_STAVKI + ") return $y)";
        
        List<Transakcija> results = executeSelectQuery(query, false);
        return results;              


    }

    public static Presek getPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka) throws IOException, JAXBException {
        
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
    
    /*
	 * Takes both XQuery
	 */
	public static List<Transakcija> executeSelectQuery(String xQuery, boolean wrap) throws IOException, JAXBException {
		
		JAXBContext context = JAXBContext.newInstance("com.project.stavka_preseka");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		JAXBContext basex_context = JAXBContext.newInstance("org.basex.rest");
		Unmarshaller basex_unmarshaller = basex_context.createUnmarshaller();
		
		Results wrappedResults = null;
		List<Transakcija> results = new ArrayList<Transakcija>();
		
		try {
			wrappedResults = (Results) basex_unmarshaller.unmarshal(RESTUtil.retrieveResource(xQuery, "BankaRacuni", RequestMethod.GET));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Result result : wrappedResults.getResult()) {
			results.add((Transakcija) unmarshaller.unmarshal((Node)result.getAny()));
		}
		return results;   
	}
}
