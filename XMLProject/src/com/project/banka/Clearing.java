package com.project.banka;

import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import com.project.bankaws.BankaPortImpl;
import com.project.common_types.TBanka;
import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.mt102.Mt102;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.util.Util;

public class Clearing {

	private BankaPortImpl bankaPort = null;
    private Integer mt102ID = 1;
    
    public Clearing(BankaPortImpl banka){
    	bankaPort = banka;
    }

	public void formirajClearingNalog() {
		
		Mt102 mt102 = null;
		for (String banka : bankaPort.current_bank.getNaloziZaClearing().keySet()) {
			mt102 = new Mt102();
			try {
				mt102.setDatum(Util.getXMLGregorianCalendarNow());
				mt102.setDatumValute(Util.getXMLGregorianCalendarNow());
				//mt102.setBankaDuznika(podaci_o_banci);
				//mt102.setBankaPoverioca(value);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			/*
			for (NalogZaPlacanje nalog : naloziZaClearing.get(banka)) {
				mt102.getPlacanje().add(nalog.getPlacanje());
			}
			*/
		}
	}

	public void obradiClearingNalog(Mt102 mt102) {
		/*
		for (Placanje placanje : mt102.getPlacanje()) {
			String broj_rk_primaoca = placanje.getUplata().getRacunPrimaoca().getBrojRacuna();
    		TBankarskiRacunKlijenta racun_primaoca = getSpecificAccount(broj_rk_primaoca);
    		if(racun_primaoca != null){
    			racun_primaoca.setStanje(placanje.getUplata().getIznos().add(new BigDecimal(racun_primaoca.getStanje())));
		}
*/
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
