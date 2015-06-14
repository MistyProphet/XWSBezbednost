package com.project.banka;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import misc.RESTUtil;
import misc.RequestMethod;

import com.project.bankaws.BankaPortImpl;
import com.project.bankaws.ReceiveNalogFault;
import com.project.banke_racuni.RacuniBanaka;
import com.project.banke_racuni.RacuniBanaka.KodBanke;
import com.project.common_types.TBanka;
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
			
			InputStream in = RESTUtil.retrieveResource("//racuni_banaka", "Banke/Podaci", RequestMethod.GET);
			JAXBContext context = JAXBContext.newInstance(RacuniBanaka.class, RacuniBanaka.class);
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
			
			pob.setBankaDuznika(bankaPort.current_bank.getPodaci_o_banci());
			pob.setBankaPoverioca(bankaPoverioca);
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
