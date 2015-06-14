package com.project.banka;

import java.math.BigDecimal;

import com.project.bankaws.BankaPortImpl;
import com.project.bankaws.ReceiveNalogFault;
import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.exceptions.NoMoneyException;
import com.project.exceptions.WrongBankException;
import com.project.mt103.Mt103;
import com.project.mt103.Mt103.PodaciOBankama;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.util.Util;

public class RTGSProccessing {
	private BankaPortImpl bankaPort = null;
    private Integer mt103ID = 1;
    
    public RTGSProccessing(BankaPortImpl banka){
    	bankaPort = banka;
    }
	
	public Mt103 kreirajMT103(NalogZaPlacanje nalog) throws ReceiveNalogFault{
		try{
			Mt103 rtgsNalog = new Mt103();
			rtgsNalog.setIDPoruke((mt103ID++).toString());
			rtgsNalog.setDatumValute(Util.getXMLGregorianCalendarNow());
			rtgsNalog.setSifraValute(nalog.getPlacanje().getSifraValute());
			rtgsNalog.setUplata(nalog.getPlacanje().getUplata());
			
			PodaciOBankama pob = new PodaciOBankama();
			//Dobiti banku na osnovu prve tri cifre racuna. To je jedinstvena oznaka banke kod CB
			String cbOznakaBankePoverioca = nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna().substring(0, 2);
			//BankaService servis = new BankaService();
			//Banka temp = servis.findById(cbOznakaBankePoverioca);
			//TBanka bankaPoverioca = temp.getPodaci_o_banci();
			pob.setBankaDuznika(bankaPort.current_bank.getPodaci_o_banci());
			//pob.setBankaPoverioca(bankaPoverioca);
			rtgsNalog.setPodaciOBankama(pob);
			
			//rezervisati sredstva klijenta (raspoloziva sredstva)
			TBankarskiRacunKlijenta racun_duznika = bankaPort.current_bank.getSpecificAccount(nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna());
			if(racun_duznika != null){
				BigDecimal iznos = nalog.getPlacanje().getUplata().getIznos();
				if(!(racun_duznika.getRaspolozivaSredstva().compareTo(iznos) == -1)){
					//duznik ima dovoljno para, skidamo pare
	    			racun_duznika.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva().subtract(iznos));
				} else {
					//no-money exception
					throw new NoMoneyException();
				}
			} else {
				//wrong-bank exception
				throw new WrongBankException();
			}
			return rtgsNalog;
		}catch(Exception e){
			e.printStackTrace();
            throw new ReceiveNalogFault(e.getMessage());
		}
	}
	
}
