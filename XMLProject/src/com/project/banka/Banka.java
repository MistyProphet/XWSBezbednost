package com.project.banka;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import misc.RESTUtil;
import misc.RequestMethod;

import com.project.bankaws.PortHelper;
import com.project.banke_racuni.RacuniBanaka;
import com.project.banke_racuni.RacuniBanaka.KodBanke;
import com.project.common_types.TBanka;
import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.entities.Identifiable;
import com.project.exceptions.NonexistentAccountException;
import com.project.exceptions.WrongBankSWIFTCodeException;
import com.project.exceptions.WrongOverallSumException;
import com.project.mt102.Mt102;
import com.project.mt103.Mt103;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.presek.Presek;
import com.project.racuni.Racuni;
import com.project.stavka_preseka.StavkaPreseka;
import com.project.stavka_preseka.Transakcija;
import com.project.stavka_preseka.TransakcijaService;
import com.project.transakcije.Transakcije;
import com.project.util.Util;
import com.project.zahtev_za_izvod.ZahtevZaIzvod;

public class Banka extends Identifiable {
	
	
	public static final int BROJ_STAVKI = 20;
	
	private TBanka podaci_o_banci;
	private ArrayList<TBankarskiRacunKlijenta> accounts;
	//Key - naziv banke za koju je namenjen niz naloga
	private HashMap<TBanka,ArrayList<NalogZaPlacanje>> naloziZaClearing;
	private Integer broj_racuna_ID = 1;
	private Long id; //Jedinstvena oznaka kod CB, ona sa kojom pocinju brojevi racuna
	
	public void init() {
		accounts = new ArrayList<TBankarskiRacunKlijenta>();
		naloziZaClearing = new HashMap<TBanka, ArrayList<NalogZaPlacanje>>();
		//Ucitavanje racuna iz baze na osnovu swift koda
		try {
			InputStream in = RESTUtil.retrieveResource("//Racuni", "Banka/00"+id, RequestMethod.GET);
			JAXBContext context = JAXBContext.newInstance(Racuni.class, Racuni.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Marshaller marshaller = context.createMarshaller();
			// set optional properties
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			String xml = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			for (String line; (line = br.readLine()) != null;) {
				xml=xml+line+"\n";
			}
			StringReader reader = new StringReader(xml);
			Racuni rac = (Racuni) unmarshaller.unmarshal(reader);

			for(TBankarskiRacunKlijenta k: rac.getRacun()){
				accounts.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPodaci_o_banci(TBanka podaci){
		if(podaci_o_banci == null){
			podaci_o_banci = new TBanka();
		}
		podaci_o_banci.setId(podaci.getId());
		podaci_o_banci.setBrojRacunaBanke(podaci.getBrojRacunaBanke());
		podaci_o_banci.setNazivBanke(podaci.getNazivBanke());
		podaci_o_banci.setSWIFTKod(podaci.getSWIFTKod());
	}
	
	public TBanka getPodaci_o_banci(){
		return podaci_o_banci;
	}
	
	public Integer getBrojRacunaID(){
		return broj_racuna_ID;
	}
	
	public String getSWIFTCode() {
		return podaci_o_banci.getSWIFTKod();
	}

	public void setSWIFTCode(String SWIFTCode) {
		podaci_o_banci.setSWIFTKod(SWIFTCode);
	}
	
	public String getNazivBanke(){
		return podaci_o_banci.getNazivBanke();
	}
	
	public String getBrojRacunaBanke(){
		return podaci_o_banci.getBrojRacunaBanke();
	}
	
	public String getID(){
		return podaci_o_banci.getId().toString();
	}

	public ArrayList<TBankarskiRacunKlijenta> getAccounts() {
		return accounts;
	}
	
	public TBankarskiRacunKlijenta getSpecificAccount(String account_number){
		for(TBankarskiRacunKlijenta rk: accounts){
			if(rk.getRacun().getBrojRacuna().equals(account_number)){
				return rk;
			}
		}
		return null;
	}

	public HashMap<TBanka, ArrayList<NalogZaPlacanje>> getNaloziZaClearing() {
		return naloziZaClearing;
	}

	public void checkMT102(Mt102 mt102) throws Exception {
			checkMT102SWIFTCode(mt102);
			checkOverallMT102Sum(mt102);
	}
	
	public void checkMT102SWIFTCode(Mt102 mt102) throws WrongBankSWIFTCodeException {
		if (!mt102.getBankaPoverioca().getSWIFTKod().trim().equals(podaci_o_banci.getSWIFTKod().trim())) {
			throw new WrongBankSWIFTCodeException();
		}
	}
	
	public void checkOverallMT102Sum(Mt102 mt102) throws WrongOverallSumException {
		BigDecimal sum = new BigDecimal(0);
		
		for (Placanje p : mt102.getPlacanje()) {
			sum.add(p.getUplata().getIznos());
		}
		
		if (!sum.equals(mt102.getUkupanIznos())) {
			throw new WrongOverallSumException();
		}
	}
	
	public ArrayList<Mt102> formirajClearingNalog() {
	
		Mt102 mt102 = null;
		BigDecimal sum = null;
		ArrayList<Mt102> ret = new ArrayList<Mt102>();
		for (TBanka banka : naloziZaClearing.keySet()) {
			mt102 = new Mt102();
			try {
				mt102.setDatum(Util.getXMLGregorianCalendarNow());
				mt102.setDatumValute(Util.getXMLGregorianCalendarNow());
				mt102.setBankaDuznika(podaci_o_banci);
				mt102.setBankaPoverioca(banka);
				mt102.setSifraValute("RSD");
				sum = new BigDecimal(0);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			for (NalogZaPlacanje nalog : naloziZaClearing.get(banka)) {
				mt102.getPlacanje().add(nalog.getPlacanje());
				sum.add(nalog.getPlacanje().getUplata().getIznos());
			}
			mt102.setUkupanIznos(sum);
			mt102.setId(Long.parseLong(PortHelper.mt102ID.toString()));
			PortHelper.mt102ID++;
		}
		return ret;
	}

	public void obradiClearingNalog(Mt102 mt102) throws NonexistentAccountException {
		NalogZaPlacanje nalog = null;
		Transakcija transakcija = null;
				
		for (Placanje placanje : mt102.getPlacanje()) {
			String broj_rk_primaoca = placanje.getUplata().getRacunPrimaoca().getBrojRacuna();
    		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
    		nalog = new NalogZaPlacanje();
    		nalog.setPlacanje(placanje);
    		try {
				nalog.setDatumValute(Util.getXMLGregorianCalendarNow());
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
    		transakcija = generisiTransakcijuUplate(nalog);

    		if(racun_primaoca != null){
    			transakcija.setStanjePreTransakcije(racun_primaoca.getStanje());
    			racun_primaoca.setStanje(placanje.getUplata().getIznos().add(racun_primaoca.getStanje()));
    			racun_primaoca.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva().add(placanje.getUplata().getIznos()));
    			transakcija.setStanjePosleTransakcije(racun_primaoca.getStanje());
    			
    			Transakcije wrappedResults = new Transakcije();
    			wrappedResults = (Transakcije) RESTUtil.doUnmarshall("//Transakcije", "Banka/00"+id+"/Racuni/"+racun_primaoca.getId(), wrappedResults);
    			wrappedResults.getTransakcija().add(transakcija);
    			RESTUtil.objectToDB("Banka/00"+PortHelper.current_bank.getId()+"/Racuni/"+racun_primaoca.getId(), "Transakcije", wrappedResults);
    			    			
    			Racuni rac1 = new Racuni();
    			//Spustamo izmenjena stanja na racunima u bazu
    			rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "Banka/00"+id, rac1);
    			//Ovde uraditi update stanja na racunima, pa baciti u bazu ponovo
    			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
    				//Nasli smo koji je racun u pitanju
    				if(k.getRacun().getBrojRacuna().equals(racun_primaoca.getRacun().getBrojRacuna())){
    					//dodajemo mu novac
    					k.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva());
    					k.setStanje(racun_primaoca.getStanje());
    					break;
    				}
    			}
    			//vracamo u bazu izmenjena raspoloziva sredstva
    			RESTUtil.doMarshall("Banka/00"+id+"/Racuni", rac1);
    		} else {
    			throw new NonexistentAccountException();
    		}
		}
	}
	

	public void obradiRTGSNalog(Mt103 mt103) throws NonexistentAccountException {
		NalogZaPlacanje nalog = new NalogZaPlacanje();
		Placanje placanje = new Placanje();
		placanje.setSifraValute(mt103.getSifraValute());
		placanje.setUplata(mt103.getUplata());
		nalog.setPlacanje(placanje);
		nalog.setDatumValute(mt103.getDatumValute());
		
		Transakcija transakcija = generisiTransakcijuUplate(nalog);
		
		String broj_rk_primaoca = mt103.getUplata().getRacunPrimaoca().getBrojRacuna();
		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
		
		if(racun_primaoca != null){
			transakcija.setStanjePreTransakcije(racun_primaoca.getStanje());
			racun_primaoca.setStanje(placanje.getUplata().getIznos().add(racun_primaoca.getStanje()));
			racun_primaoca.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva().add(placanje.getUplata().getIznos()));
			transakcija.setStanjePosleTransakcije(racun_primaoca.getStanje());
			
			Transakcije wrappedResults = new Transakcije();
			wrappedResults = (Transakcije) RESTUtil.doUnmarshall("//Transakcije", "Banka/00"+id+"/Racuni/"+racun_primaoca.getId(), wrappedResults);
			wrappedResults.getTransakcija().add(transakcija);
			RESTUtil.objectToDB("Banka/00"+PortHelper.current_bank.getId()+"/Racuni/"+racun_primaoca.getId(), "Transakcije", wrappedResults);
						
			Racuni rac1 = new Racuni();
			//Spustamo izmenjena stanja na racunima u bazu
			rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "Banka/00"+id, rac1);
			//Ovde uraditi update stanja na racunima, pa baciti u bazu ponovo
			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
				//Nasli smo koji je racun u pitanju
				if(k.getRacun().getBrojRacuna().equals(racun_primaoca.getRacun().getBrojRacuna())){
					//dodajemo mu novac
					k.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva());
					k.setStanje(racun_primaoca.getStanje());
					break;
				}
			}
			//vracamo u bazu izmenjena raspoloziva sredstva
			RESTUtil.doMarshall("Banka/00"+id+"/Racuni", rac1);
			
		} else {
			throw new NonexistentAccountException();
		}		
	}
	
	public void addNalogZaClearing(NalogZaPlacanje nalog) throws JAXBException, IOException {
		Long idBanke = null;
		try {
			//ekstrakcija prve tri cifre - oznake banke
			idBanke = new Long(nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna().split("-")[0]);
			String cbOznakaBankePoverioca = idBanke.toString();
			
			InputStream in = RESTUtil.retrieveResource("//racuni_banaka", "Banke/Podaci", RequestMethod.GET);
			JAXBContext context = JAXBContext.newInstance(RacuniBanaka.class, RacuniBanaka.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			String xml = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			for (String line; (line = br.readLine()) != null;) {
				xml=xml+line+"\n";
			}
			StringReader reader = new StringReader(xml);
			RacuniBanaka rac = (RacuniBanaka) unmarshaller.unmarshal(reader);
			
			TBanka bankaPoverioca = new TBanka();
			for(KodBanke k: rac.getKodBanke()){
				if(k.getId().equals(cbOznakaBankePoverioca)){
					bankaPoverioca.setBrojRacunaBanke(k.getRacunBanke());
					bankaPoverioca.setId(Long.parseLong(k.getId()));
					bankaPoverioca.setNazivBanke(k.getNaziv());
					bankaPoverioca.setSWIFTKod(k.getSwift());
					break;
				}
			}
			
			if (naloziZaClearing.containsKey(bankaPoverioca)) {
				naloziZaClearing.get(bankaPoverioca).add(nalog);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long value) {
		this.id = value;
	}

	public Presek formirajPresek(ZahtevZaIzvod zahtev) throws IOException, JAXBException {
		Long idRacuna = null;
		Racuni rac1 = new Racuni();
		rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "Banka/00"+id, rac1);
		for(TBankarskiRacunKlijenta k: rac1.getRacun()){
			if(k.getRacun().getBrojRacuna().equals(zahtev.getBrojRacuna())){
				idRacuna = k.getId();
			}
		}
		if(idRacuna == null){
			throw new JAXBException("Account doesn't exist in this bank.");
		}
		return TransakcijaService.getPresek( zahtev.getDatum(), zahtev.getBrojRacuna(), zahtev.getRedniBrojPreseka(),
				id, idRacuna);		
	}
	
    public Transakcija generisiTransakcijuUplate(NalogZaPlacanje nalog) {
		Transakcija transakcija = new Transakcija();
		String broj_rk_primaoca = nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna();
		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
		transakcija.setRacunKlijenta(racun_primaoca);
		transakcija.setStanjePreTransakcije(racun_primaoca.getStanje());
		Long id = getMaxTransactionID(Long.valueOf(racun_primaoca.getRacun().getBrojRacuna().substring(0, 3)), racun_primaoca.getId());
		id++;
		transakcija.setId(id);
		StavkaPreseka stavkaPreseka = new StavkaPreseka();
		stavkaPreseka.setDatumValute(nalog.getDatumValute());
		stavkaPreseka.setSifraValute(nalog.getPlacanje().getSifraValute());
		stavkaPreseka.setSmer("U");
		stavkaPreseka.setUplata(nalog.getPlacanje().getUplata());
		return transakcija;
    }
    
    public Transakcija generisiTransakcijuUplate(Mt103 nalog) {
		Transakcija transakcija = new Transakcija();
		String broj_rk_primaoca = nalog.getUplata().getRacunPrimaoca().getBrojRacuna();
		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
		transakcija.setRacunKlijenta(racun_primaoca);
		transakcija.setStanjePreTransakcije(racun_primaoca.getStanje());
		Long id = getMaxTransactionID(Long.valueOf(racun_primaoca.getRacun().getBrojRacuna().substring(0, 3)), racun_primaoca.getId());
		id++;
		transakcija.setId(id);
		StavkaPreseka stavkaPreseka = new StavkaPreseka();
		stavkaPreseka.setDatumValute(nalog.getDatumValute());
		stavkaPreseka.setSifraValute(nalog.getSifraValute());
		stavkaPreseka.setSmer("U");
		stavkaPreseka.setUplata(nalog.getUplata());
		return transakcija;
    }
    
    public Transakcija generisiTransakcijuIsplate(NalogZaPlacanje nalog) {
		Transakcija transakcija = new Transakcija();
		String broj_rk_duznika = nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna();
		TBankarskiRacunKlijenta racun_duznika = getSpecificAccount(broj_rk_duznika);
		transakcija.setRacunKlijenta(racun_duznika);
		transakcija.setStanjePreTransakcije(racun_duznika.getStanje());
		Long id = getMaxTransactionID(Long.valueOf(racun_duznika.getRacun().getBrojRacuna().substring(0, 3)), racun_duznika.getId());
		id++;
		transakcija.setId(id);
		StavkaPreseka stavkaPreseka = new StavkaPreseka();
		stavkaPreseka.setDatumValute(nalog.getDatumValute());
		stavkaPreseka.setSifraValute(nalog.getPlacanje().getSifraValute());
		stavkaPreseka.setSmer("I");
		stavkaPreseka.setUplata(nalog.getPlacanje().getUplata());
		return transakcija;
    }
    
    public Long getMaxTransactionID(Long idBanke, Long idRacuna){
    	Transakcije t = new Transakcije();
    	t = (Transakcije) RESTUtil.doUnmarshall("//*:Transakcije", "Banka/00"+idBanke.toString()+"/Racuni/"+idRacuna.toString(), t);
    	int maxID = -1;
    	for(Transakcija tt: t.getTransakcija()){
    		if(tt.getId().doubleValue() > maxID){
    			maxID = Integer.parseInt(tt.getId().toString());
    		}
    	}
    	return Long.valueOf(maxID);
    }
    
    public static void main(String[] args) {
    	Banka b = new Banka();
    	System.out.println(b.getMaxTransactionID(Long.valueOf("1"), Long.valueOf("1")));
    }
	
}
