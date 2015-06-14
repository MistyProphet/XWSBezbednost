package com.project.banka;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

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
import com.project.stavka_preseka.StavkaPreseka;
import com.project.stavka_preseka.Transakcija;
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
		podaci_o_banci = new TBanka();
		accounts = new ArrayList<TBankarskiRacunKlijenta>();
		naloziZaClearing = new HashMap<TBanka, ArrayList<NalogZaPlacanje>>();
		//ucitavanje iz baze na osnovu swift koda
		try {
			//InputStream result = RESTUtil.retrieveResource("", "TBankarskiRacunKlijenta", RequestMethod.GET);
			//TBankarskiRacunKlijentaService servis = new TBankarskiRacunKlijentaService();
			//List<TBankarskiRacunKlijenta> result = servis.findByAll();
			//for(TBankarskiRacunKlijenta t: result){
				//accounts.add(t);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPodaci_o_banci(TBanka podaci){
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
    			transakcija.setStanjePosleTransakcije(racun_primaoca.getStanje());
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
			transakcija.setStanjePosleTransakcije(racun_primaoca.getStanje());
		} else {
			throw new NonexistentAccountException();
		}		
	}
	
	public void addNalogZaClearing(NalogZaPlacanje nalog) throws JAXBException, IOException {
		Long idBanke = null;
		try {
			//ekstrakcija prve tri cifre - oznake banke
			idBanke = new Long(nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna().split("-")[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//TBanka bankaPrimaoca = tBankaDao.findById(idBanke);
		//if (naloziZaClearing.containsKey(bankaPrimaoca)) {
		//	naloziZaClearing.get(bankaPrimaoca).add(nalog);
		//}
	
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
		return null;
		//return transakcijaDao.getPresek( zahtev.getDatum(), zahtev.getBrojRacuna(), zahtev.getRedniBrojPreseka());		
	}
	
    public Transakcija generisiTransakcijuUplate(NalogZaPlacanje nalog) {
		Transakcija transakcija = new Transakcija();
		String broj_rk_primaoca = nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna();
		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
		transakcija.setRacunKlijenta(racun_primaoca);
		transakcija.setStanjePreTransakcije(racun_primaoca.getStanje());
		StavkaPreseka stavkaPreseka = new StavkaPreseka();
		stavkaPreseka.setDatumValute(nalog.getDatumValute());
		stavkaPreseka.setSifraValute(nalog.getPlacanje().getSifraValute());
		stavkaPreseka.setSmer("U");
		stavkaPreseka.setUplata(nalog.getPlacanje().getUplata());
		return transakcija;
    }
    
    public Transakcija generisiTransakcijuIsplate(NalogZaPlacanje nalog) {
		Transakcija transakcija = new Transakcija();
		String broj_rk_duznika = nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna();
		TBankarskiRacunKlijenta racun_duznika = getSpecificAccount(broj_rk_duznika);
		transakcija.setRacunKlijenta(racun_duznika);
		transakcija.setStanjePreTransakcije(racun_duznika.getStanje());
		StavkaPreseka stavkaPreseka = new StavkaPreseka();
		stavkaPreseka.setDatumValute(nalog.getDatumValute());
		stavkaPreseka.setSifraValute(nalog.getPlacanje().getSifraValute());
		stavkaPreseka.setSmer("I");
		stavkaPreseka.setUplata(nalog.getPlacanje().getUplata());
		return transakcija;
    }

	
}
