package com.project.banka;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import com.project.common_types.TBanka;
import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.exceptions.WrongBankSWIFTCodeException;
import com.project.exceptions.WrongOverallSumException;
import com.project.mt102.Mt102;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.util.Util;

public class Banka {
	
	private TBanka podaci_o_banci;
	private ArrayList<TBankarskiRacunKlijenta> accounts;
	//Key - naziv banke za koju je namenjen niz naloga
	private HashMap<String,ArrayList<NalogZaPlacanje>> naloziZaClearing;
	private Integer broj_racuna_ID = 1;
	
	public void init() {
		podaci_o_banci = new TBanka();
		accounts = new ArrayList<TBankarskiRacunKlijenta>();
		naloziZaClearing = new HashMap<String, ArrayList<NalogZaPlacanje>>();
		//ucitavanje iz baze na osnovu swift koda?
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

	public HashMap<String, ArrayList<NalogZaPlacanje>> getNaloziZaClearing() {
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
	
	public void formirajClearingNalog() {
	
		Mt102 mt102 = null;
		for (String banka : naloziZaClearing.keySet()) {
			mt102 = new Mt102();
			try {
				mt102.setDatum(Util.getXMLGregorianCalendarNow());
				mt102.setDatumValute(Util.getXMLGregorianCalendarNow());
				mt102.setBankaDuznika(podaci_o_banci);
				//mt102.setBankaPoverioca(value);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			for (NalogZaPlacanje nalog : naloziZaClearing.get(banka)) {
				mt102.getPlacanje().add(nalog.getPlacanje());
			}
			
		}
	}

	public void obradiClearingNalog(Mt102 mt102) {
		
		for (Placanje placanje : mt102.getPlacanje()) {
			String broj_rk_primaoca = placanje.getUplata().getRacunPrimaoca().getBrojRacuna();
    		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
    		if(racun_primaoca != null){
    			racun_primaoca.setStanje(placanje.getUplata().getIznos().add(racun_primaoca.getStanje()));
    		}
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
		/*
		TBanka bankaPrimaoca = bankaDao.findById(idBanke);
		if (naloziZaClearing.containsKey(bankaPrimaoca)) {
			naloziZaClearing.get(bankaPrimaoca).add(nalog);
		}
		*/
	}

	
}
