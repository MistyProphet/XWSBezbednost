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

import com.project.bankaws.PortHelper;
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
import com.project.racuni.Racuni;
import com.project.util.Util;

public class RTGSProccessing {
	
	public Mt103 kreirajMT103(NalogZaPlacanje nalog) throws ReceiveNalogFault{
		try{
			Mt103 rtgsNalog = new Mt103();
			rtgsNalog.setIDPoruke((PortHelper.mt103ID++).toString());
			rtgsNalog.setDatumValute(Util.getXMLGregorianCalendarNow());
			rtgsNalog.setSifraValute(nalog.getPlacanje().getSifraValute());
			rtgsNalog.setUplata(nalog.getPlacanje().getUplata());
			
			PodaciOBankama pob = new PodaciOBankama();
			//Dobiti banku na osnovu prve tri cifre racuna. To je jedinstvena oznaka banke kod CB
			String cbOznakaBankePoverioca = nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna().substring(0, 3);
			
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
			
			pob.setBankaDuznika(PortHelper.current_bank.getPodaci_o_banci());
			pob.setBankaPoverioca(bankaPoverioca);
			rtgsNalog.setPodaciOBankama(pob);
			
			//rezervisati sredstva klijenta (raspoloziva sredstva)
			TBankarskiRacunKlijenta racun_duznika = PortHelper.current_bank.getSpecificAccount(nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna());
			if(racun_duznika != null){
				BigDecimal iznos = nalog.getPlacanje().getUplata().getIznos();
				if(racun_duznika.getRaspolozivaSredstva() - iznos.doubleValue() >= 0){
					//duznik ima dovoljno para, skidamo pare
	    			racun_duznika.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva()-iznos.doubleValue());
	    			
	    			InputStream in1 = RESTUtil.retrieveResource("//Racuni", "Banka/00"+PortHelper.current_bank.getId(), RequestMethod.GET);
	    			JAXBContext context1 = JAXBContext.newInstance(Racuni.class, Racuni.class);
	    			Unmarshaller unmarshaller1 = context1.createUnmarshaller();
	    			Marshaller marshaller1 = context1.createMarshaller();
	    			// set optional properties
	    			marshaller1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    			String xml1 = "";
	    			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
	    			for (String line; (line = br1.readLine()) != null;) {
	    				xml1=xml1+line+"\n";
	    			}
	    			StringReader reader1 = new StringReader(xml1);
	    			Racuni rac1 = (Racuni) unmarshaller1.unmarshal(reader1);

    				//Ovde uraditi update raspolozivog stanja na racunima, pa baciti u bazu ponovo
	    			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
	    				//Nasli smo koji je racun u pitanju
	    				if(k.getRacun().getBrojRacuna().equals(racun_duznika.getRacun().getBrojRacuna())){
	    					//skidamo mu novac
	    					k.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva());
	    					//vracamo u bazu izmenjena raspoloziva sredstva
	    					RESTUtil.doMarshall("Banka/00"+PortHelper.current_bank.getId()+"/Racuni", rac1);
	    					break;
	    				}
	    			}
	    			
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
