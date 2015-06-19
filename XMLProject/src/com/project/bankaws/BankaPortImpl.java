
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.project.bankaws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.ws.Service;

import misc.RESTUtil;

import org.apache.commons.io.IOUtils;

import com.project.banka.Banka;
import com.project.banka.RTGSProccessing;
import com.project.banke_racuni.RacuniBanaka;
import com.project.banke_racuni.RacuniBanaka.KodBanke;
import com.project.common_types.TBanka;
import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.common_types.TRacunKlijenta;
import com.project.exceptions.NoMoneyException;
import com.project.exceptions.WrongBankException;
import com.project.mt102.Mt102;
import com.project.mt103.Mt103;
import com.project.mt900.Mt900Clearing;
import com.project.mt900.Mt900RTGS;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.nalog_za_placanje.Uplata;
import com.project.racuni.Racuni;
import com.project.stavka_preseka.Transakcija;
import com.project.util.CBport;
import com.project.util.RecieveMT102Fault;
import com.project.util.RecieveMT103Fault;
import com.project.util.Util;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-07T02:41:04.821+02:00
 * Generated source version: 2.6.5
 * 
 */
@Stateless

@javax.jws.WebService(
                      serviceName = "BankaService",
                      portName = "BankaPort",
                      targetNamespace = "http://www.project.com/BankaWS",
                      wsdlLocation = "file:/C:/Users/Geek/Desktop/Wp/XWSEclipse/XMLProject/WEB-INF/wsdl/Banka.wsdl",
                      endpointInterface = "com.project.bankaws.BankaPort")
@HandlerChain(file = "/com/project/document/handler-chain-document.xml")
public class BankaPortImpl implements BankaPort {

    private static final Logger LOG = Logger.getLogger(BankaPortImpl.class.getName());
    private static int ID_Instance_Banke = 1;
    public Banka current_bank;
    private RTGSProccessing rtgsObrada = null;
    private String checkNalogEx = "";
    
    public void init() {
    	current_bank = new Banka();
    	String propFile = "deploy"+ID_Instance_Banke;
    	String s = ResourceBundle.getBundle(propFile).getString("swift.code");
    	Long i = Long.parseLong(Integer.toString(ID_Instance_Banke));
    	TBanka podaci = new TBanka();
    	podaci.setSWIFTKod(s);
    	podaci.setId(current_bank.getId());
    	podaci.setBrojRacunaBanke(ResourceBundle.getBundle(propFile).getString("account.number"));
    	podaci.setNazivBanke(ResourceBundle.getBundle(propFile).getString("bank.name"));
    	current_bank.setPodaci_o_banci(podaci);
    	current_bank.setSWIFTCode(s);
    	current_bank.setId(i);
    	
    	current_bank.init();
    	rtgsObrada = new RTGSProccessing(this);
    	ID_Instance_Banke++;
    }
    
    /* (non-Javadoc)
     * @see com.project.bankaws.BankaPort#odradiClearing(*
     */
    public com.project.common_types.Status odradiClearing() throws ClearingFault {
        	com.project.common_types.Status _return = new com.project.common_types.Status();
            ArrayList<Mt102> nalozi = current_bank.formirajClearingNalog();
            
			try {
				URL wsdl = new URL("http://localhost:8080/XML_CB/services/Banka?wsdl");
		    	QName serviceName = new QName("http://www.project.com/CBws", "CBservice");
		    	QName portName = new QName("http://www.project.com/CBws", "CBport");
		    	Service service = Service.create(wsdl, serviceName);
		        CBport centralnaBanka = service.getPort(portName, CBport.class);
		        //Za svaku banku pojedinacno poslati naloge za kliring
		        for(Mt102 m: nalozi){
	    	        try {
						Mt900Clearing response = centralnaBanka.recieveMT102CB(m);
						RESTUtil.objectToDB("BankaPoruke/MT900clearing", response.getIDPoruke(), response);
					} catch (RecieveMT102Fault e) {
						e.printStackTrace();
						//Ako se desila greska pri kliringu, npr. neispravan ukupan iznos ili pogresan swift kod.
						//Revertovati svaki nalog posebno (vratiti raspoloziva sredstva na staru vrednost)
						for(Placanje p: m.getPlacanje()){
							TBankarskiRacunKlijenta racun_duznika = current_bank.getSpecificAccount(p.getUplata().getRacunDuznika().getBrojRacuna());
							racun_duznika.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva().add(
									p.getUplata().getIznos()));
							//Update stanja racuna u bazi
							Racuni rac1 = new Racuni();
			    			//Spustamo izmenjena stanja na racunima u bazu
			    			rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "BankaRacuni/00"+current_bank.getId(), rac1);
			    			//Ovde uraditi update stanja na racunima, pa baciti u bazu ponovo
			    			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
			    				//Nasli smo koji je racun u pitanju
			    				if(k.getRacun().getBrojRacuna().equals(racun_duznika.getRacun().getBrojRacuna())){
			    					//dodajemo mu novac
			    					k.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva());
			    					break;
			    				}
			    			}
			    			//vracamo u bazu izmenjena raspoloziva sredstva
			    			RESTUtil.doMarshall("BankaRacuni/00"+current_bank.getId(), rac1);
						}
						throw new ClearingFault("An error occured with the clearing "+m.getIDPoruke()+"\nReason: "+
						e.getMessage());
					}
	            }
		        _return.setStatusText("All clearings have been processed.");
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new ClearingFault("Wsdl url not valid.");
			} catch(Exception ex){
				ex.printStackTrace();
				throw new ClearingFault(ex.getMessage());
			}
            return _return;
        //throw new ClearingFault("clearingFault...");
    }

    /* (non-Javadoc)
     * @see com.project.bankaws.BankaPort#receiveMT910(com.project.mt910.Mt910  mt910 )*
     */
    public com.project.common_types.Status receiveMT910(com.project.mt910.Mt910 mt910) throws ReceiveMT103Fault    { 
        LOG.info("Executing operation receiveMT910");
        System.out.println(mt910);
        com.project.common_types.Status _return = new com.project.common_types.Status();
        try {
			RESTUtil.objectToDB("BankaPoruke/MT910", mt910.getIDPoruke(), mt910);
			//Update stanja na racunu klijenta na osnovu odobrenja
			//Izvlacim iz baze mt103 nalog na osnovu polja u mt910
			Mt103 temp = new Mt103();
			temp = (Mt103) RESTUtil.doUnmarshall("//"+mt910.getIDPoruke(), "BankaPoruke/MT103", temp);
			//Update-ujem racun
			Racuni rac1 = new Racuni();
			//Ucitavamo racune iz baze, kako bi update-ovali podatke
			rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "BankaRacuni/00"+current_bank.getId(), rac1);
			
			TBankarskiRacunKlijenta racun_primaoca = current_bank.getSpecificAccount(temp.getUplata().getRacunPrimaoca().getBrojRacuna());
			if(racun_primaoca == null){
				_return.setStatusText("Client account does not exist.");
				throw new ReceiveMT103Fault(_return.getStatusText());
			}
			Transakcija transakcija = null;
			transakcija = current_bank.generisiTransakcijuUplate(temp);
			transakcija.setStanjePreTransakcije(racun_primaoca.getStanje());
			racun_primaoca.setStanje(racun_primaoca.getStanje().add(temp.getUplata().getIznos()));
			racun_primaoca.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva().add(temp.getUplata().getIznos()));
			transakcija.setStanjePosleTransakcije(racun_primaoca.getStanje());
			RESTUtil.objectToDB("BankaRacuni/001/Transakcije", transakcija.getId().toString(), transakcija);
			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
				//Nasli smo koji je racun u pitanju
				if(k.getRacun().getBrojRacuna().equals(temp.getUplata().getRacunPrimaoca().getBrojRacuna())){
					//dodajemo mu novac
					k.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva());
					k.setStanje(racun_primaoca.getStanje());
					break;
				}
			}
			//Vracam novo stanje racuna u bazu
			RESTUtil.doMarshall("BankaRacuni/00"+current_bank.getId(), rac1);
			_return.setStatusText("Account updated.");
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new ReceiveMT103Fault(_return.getStatusText()+" Reason: "+ex.getMessage());
        }
        //throw new ReceiveMT103Fault("receiveMT103fault...");
    }

    /* (non-Javadoc)
     * @see com.project.bankaws.BankaPort#receiveMT102(com.project.mt102.Mt102  mt102 )*
     */
    public com.project.common_types.Status receiveMT102(com.project.mt102.Mt102 mt102) throws ReceiveMT102Fault    { 
        LOG.info("Executing operation receiveMT102");
        try {
	        current_bank.obradiClearingNalog(mt102);
			RESTUtil.objectToDB("BankaPoruke/MT102", mt102.getIDPoruke(), mt102);
	        System.out.println(mt102);
	        com.project.common_types.Status _return = new com.project.common_types.Status();
	        _return.setStatusText("Clearing proccessed.");
	        return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new ReceiveMT102Fault(ex.getMessage());
        }
        //throw new ReceiveMT102Fault("receiveMT102fault...");
    }

    /* (non-Javadoc)
     * @see com.project.bankaws.BankaPort#sendPresek(com.project.zahtev_za_izvod.ZahtevZaIzvod  zahtev )*
     */
    public com.project.presek.Presek sendPresek(com.project.zahtev_za_izvod.ZahtevZaIzvod zahtev) throws SendPresekFault    { 
        LOG.info("Executing operation sendPresek");
        System.out.println(zahtev);

        try {
            current_bank.formirajPresek(zahtev);
            com.project.presek.Presek _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new SendPresekFault(ex.getMessage());
        }
        //throw new SendPresekFault("sendPresekFault...");
    }

    /* (non-Javadoc)
     * @see com.project.bankaws.BankaPort#receiveMT103(com.project.mt103.Mt103  mt103 )*
     */
    public com.project.common_types.Status receiveMT103(com.project.mt103.Mt103 mt103) throws ReceiveMT103Fault   { 

        try {
            LOG.info("Executing operation receiveMT103");
            current_bank.obradiRTGSNalog(mt103);
			RESTUtil.objectToDB("BankaPoruke/MT103", mt103.getIDPoruke(), mt103);
            System.out.println(mt103);
            com.project.common_types.Status _return = new com.project.common_types.Status();
            _return.setStatusText("RTGS invoice recieved and proccessed.");
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new ReceiveMT103Fault(ex.getMessage());
        }
        //throw new ReceiveMT103Fault("receiveMT103fault...");
    }

    /* (non-Javadoc)
     * @see com.project.bankaws.BankaPort#receiveNalog(com.project.nalog_za_placanje.NalogZaPlacanje  nalog )*
     */
    public com.project.common_types.Status receiveNalog(NalogZaPlacanje nalog) throws ReceiveNalogFault    { 
        LOG.info("Executing operation receiveNalog...");
        //System.out.println(nalog);
        com.project.common_types.Status _return = new com.project.common_types.Status();
        try {
        	//proveriti validnost naloga
    		if(!checkNalog(nalog)){
                throw new ReceiveNalogFault("Invoice validation failed. Reason: "+
                		checkNalogEx);
    		}
    		RESTUtil.objectToDB("BankaPoruke/Nalozi", nalog.getId().toString(), nalog);
    		//provera da li je racun primaoca u istoj banci
    		String broj_rk_primaoca = nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna();
    		TBankarskiRacunKlijenta racun_primaoca = current_bank.getSpecificAccount(broj_rk_primaoca);
    		if(racun_primaoca != null){
    			//ako jeste, prebaciti odmah pare
    			Transakcija transakcijaPrimalac = current_bank.generisiTransakcijuUplate(nalog);
    			String broj_rk_duznika = nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna();
    			TBankarskiRacunKlijenta racun_duznika = current_bank.getSpecificAccount(broj_rk_duznika);
    			if(racun_duznika != null){
    				BigDecimal iznos = nalog.getPlacanje().getUplata().getIznos();
    				/* Generisanje podataka o transakciji*/
					Transakcija transakcijaDuznik = current_bank.generisiTransakcijuIsplate(nalog);
					
    				if(racun_duznika.getRaspolozivaSredstva().subtract(iznos).compareTo(new BigDecimal(0))>=0){
    					//duznik ima dovoljno para, skidamo pare
    					transakcijaDuznik.setStanjePreTransakcije(racun_duznika.getStanje());
    					racun_duznika.setStanje(racun_duznika.getStanje().subtract(iznos));
    					racun_duznika.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva().subtract(iznos));
    					transakcijaDuznik.setStanjePosleTransakcije(racun_duznika.getStanje());
    					//dodajemo primaocu
    					transakcijaPrimalac.setStanjePreTransakcije(racun_primaoca.getStanje());
    					racun_primaoca.setStanje(racun_primaoca.getStanje().add(iznos));
    					racun_primaoca.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva().add(iznos));
    					transakcijaPrimalac.setStanjePosleTransakcije(racun_primaoca.getStanje());
    					
    					Racuni rac1 = new Racuni();
    					//Spustamo izmenjena stanja na racunima u bazu
    					rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "BankaRacuni/00"+current_bank.getId(), rac1);
    					
    					//Ovde uraditi update stanja na racunima, pa baciti u bazu ponovo
    	    			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
    	    				//Nasli smo koji je racun u pitanju
    	    				if(k.getRacun().getBrojRacuna().equals(racun_duznika.getRacun().getBrojRacuna())){
    	    					//skidamo mu novac
    	    					k.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva());
    	    					k.setStanje(racun_duznika.getStanje());
    	    				}else if(k.getRacun().getBrojRacuna().equals(racun_primaoca.getRacun().getBrojRacuna())){
    	    					//dodajemo mu novac
    	    					k.setRaspolozivaSredstva(racun_primaoca.getRaspolozivaSredstva());
    	    					k.setStanje(racun_primaoca.getStanje());
    	    				}
    	    			}
    	    			
    					//vracamo u bazu izmenjena raspoloziva sredstva
    					RESTUtil.doMarshall("BankaRacuni/00"+current_bank.getId(), rac1);
    					
    					//Status da je poruka obradjena bez greske
    	    	    	_return.setStatusCode(1);
    	    	    	_return.setStatusText("Your payment order has been received and proccessed.");
    	    	    	return _return;
    				} else {
    					//no-money exception
    					throw new NoMoneyException();
    				}
    			} else {
    				//wrong-bank exception
    				throw new WrongBankException();
    			}
    		}
        	if(!nalog.isHitno() || (nalog.getPlacanje().getUplata().getIznos().compareTo(BigDecimal.valueOf(250000.0)) == -1)){
        		current_bank.addNalogZaClearing(nalog);
        	} else {
        		//RTGS
        		Mt103 rtgsNalog = rtgsObrada.kreirajMT103(nalog);
        		//spustanje mt103 u bazu
        		RESTUtil.objectToDB("BankaPoruke/MT103", rtgsNalog.getId().toString(), rtgsNalog);
        		//slanje MT103
        		URL wsdl = new URL("http://localhost:8080/XML_CB/services/Banka?wsdl");
    	    	QName serviceName = new QName("http://www.project.com/CBws", "CBservice");
    	    	QName portName = new QName("http://www.project.com/CBws", "CBport");
    	    	
    	    	Service service = Service.create(wsdl, serviceName);
    	        CBport centralnaBanka = service.getPort(portName, CBport.class);
    	        try{
    	        	Mt900RTGS rtgsResponse = centralnaBanka.recieveMT103CB(rtgsNalog);
    	        	 //Spustanje odgovora u bazu
            		RESTUtil.objectToDB("BankaPoruke/MT900rtgs", rtgsResponse.getId().toString(), rtgsResponse);
        	        
            		//Status klijentu da je poruka obradjena bez greske
        	    	_return.setStatusCode(1);
        	    	_return.setStatusText("Your payment order has been received and sent to processing.");
    	        } catch(RecieveMT103Fault e){
    	        	TBankarskiRacunKlijenta racun_duznika = current_bank.getSpecificAccount(nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna());
					racun_duznika.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva().add(
							nalog.getPlacanje().getUplata().getIznos()));
					//Update stanja racuna u bazi
					Racuni rac1 = new Racuni();
	    			rac1 = (Racuni) RESTUtil.doUnmarshall("//Racuni", "BankaRacuni/00"+current_bank.getId(), rac1);
	    			//Ovde uraditi update stanja na racunima, pa baciti u bazu ponovo
	    			for(TBankarskiRacunKlijenta k: rac1.getRacun()){
	    				//Nasli smo koji je racun u pitanju
	    				if(k.getRacun().getBrojRacuna().equals(racun_duznika.getRacun().getBrojRacuna())){
	    					//dodajemo mu novac
	    					k.setRaspolozivaSredstva(racun_duznika.getRaspolozivaSredstva());
	    					break;
	    				}
	    			}
	    			//vracamo u bazu izmenjena raspoloziva sredstva
	    			RESTUtil.doMarshall("BankaRacuni/00"+current_bank.getId(), rac1);
	    			throw new ReceiveNalogFault("Payment order hasn't been processed by the central bank. Reason: "+
	    					e.getMessage());
    	        }
        	}
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new ReceiveNalogFault(ex.getMessage());
        }
    }
    
    public boolean checkNalog(NalogZaPlacanje nalog) throws Exception {
    	try {
    		//Validacija po semi
    		//Upisujemo u privremeni fajl, kako bi mogli da radimo validaciju nad xml fajlom, a ne java objektom
	    	File schemaFile = new File("WEB-INF/scheme/nalog_za_placanje.xsd");
			JAXBContext context1 = JAXBContext.newInstance(nalog.getClass());
			Marshaller marshaller1 = context1.createMarshaller();
			marshaller1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            OutputStream os = new FileOutputStream("src/resource/test.xml" );
            marshaller1.marshal( nalog, os );
			IOUtils.closeQuietly(os);
				
				
	    	Source xmlFile = new StreamSource(new File("src/resource/test.xml"));
	    	SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    	Schema schema = schemaFactory.newSchema(schemaFile);
	    	Validator validator = schema.newValidator();
	    	validator.validate(xmlFile);
	    	
	    	//Provera da li su sva polja ispunjena
	    	boolean filled = true;
	    	if(nalog.getDatumValute() == null){
	    		filled = false;
	    	}
	    	if(nalog.getId() == null){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getId() == null){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getSifraValute() == null){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getDatumNaloga() == null){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getIznos() == null || nalog.getPlacanje().getUplata().getIznos().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getPozivNaBrojOdobrenja() == null || 
	    			nalog.getPlacanje().getUplata().getPozivNaBrojOdobrenja().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getPozivNaBrojZaduzenja()== null || 
	    			nalog.getPlacanje().getUplata().getPozivNaBrojZaduzenja().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getSvrhaPlacanja() == null || 
	    			nalog.getPlacanje().getUplata().getSvrhaPlacanja().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna() == null || 
	    			nalog.getPlacanje().getUplata().getRacunDuznika().getBrojRacuna().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna() == null || 
	    			nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getRacunDuznika().getVlasnik() == null || 
	    			nalog.getPlacanje().getUplata().getRacunDuznika().getVlasnik().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getRacunPrimaoca().getVlasnik() == null || 
	    			nalog.getPlacanje().getUplata().getRacunPrimaoca().getVlasnik().equals("")){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getRacunDuznika().getId() == null){
	    		filled = false;
	    	}
	    	if(nalog.getPlacanje().getUplata().getRacunPrimaoca().getId() == null){
	    		filled = false;
	    	}
	    	
	    	if(!filled){
				checkNalogEx = "One of the fields was empty.";
		    	System.out.println(checkNalogEx);
		    	FileOutputStream writer = new FileOutputStream("src/resource/test.xml");
		    	writer.write((new String()).getBytes());
		    	writer.close();
				return false;
			}
	    	
	    	//Provera da li ta druga banka postoji
	    	String cbOznakaBankePoverioca = nalog.getPlacanje().getUplata().getRacunPrimaoca().getBrojRacuna().substring(0, 3);
	    	RacuniBanaka rac = new RacuniBanaka();
			rac = (RacuniBanaka) RESTUtil.doUnmarshall("//racuni_banaka", "Banke/Podaci", rac);
			
			
			boolean found = false;
			for(KodBanke k: rac.getKodBanke()){
				if(k.getId().equals(cbOznakaBankePoverioca)){
					found = true;
					break;
				}
			}
			if(!found){
				checkNalogEx = "Recipient's bank does not exist.";
		    	System.out.println(checkNalogEx);
		    	FileOutputStream writer = new FileOutputStream("src/resource/test.xml");
		    	writer.write((new String()).getBytes());
		    	writer.close();
				return false;
			}

	    	FileOutputStream writer = new FileOutputStream("src/resource/test.xml");
	    	writer.write((new String()).getBytes());
	    	writer.close();
	    	checkNalogEx = "";
	    	return true;
    	} catch (Exception e) {
	    	System.out.println("Xml is NOT valid");
	    	System.out.println("Reason: " + e.getLocalizedMessage());
			checkNalogEx = e.getLocalizedMessage();
	    	FileOutputStream writer = new FileOutputStream("src/resource/test.xml");
	    	writer.write((new String()).getBytes());
	    	writer.close();
	    	return false;
    	}
    }
    
    public static void main(String[] args) {
    	//test area
    	
    	BankaPortImpl b = new BankaPortImpl();
    	b.init();
    	
    	NalogZaPlacanje novi = new NalogZaPlacanje();
    	try {
			novi.setDatumValute(Util.getXMLGregorianCalendarNow());
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	novi.setHitno(true);
    	novi.setId(Long.parseLong("1"));
    	Placanje p = new Placanje();
    	p.setId(Long.parseLong("1"));
    	p.setIDPoruke("1");
    	p.setSifraValute("RSD");
    	Uplata u = new Uplata();
    	try {
			u.setDatumNaloga(Util.getXMLGregorianCalendarNow());
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	u.setIznos(BigDecimal.valueOf(350.0));
    	u.setModelOdobrenja(12);
    	u.setModelZaduzenja(12);
    	u.setPozivNaBrojOdobrenja("12335678912345678912");
    	u.setPozivNaBrojZaduzenja("12335678912345678912");
    	TRacunKlijenta t1 = new TRacunKlijenta();
    	t1.setBrojRacuna("001-0000000000001-00");
    	t1.setId(Long.parseLong("1"));
    	t1.setVlasnik("Pera Peric");
    	u.setRacunDuznika(t1);
    	TRacunKlijenta t2 = new TRacunKlijenta();
    	t2.setBrojRacuna("001-0000000000002-00");
    	t2.setId(Long.parseLong("2"));
    	t2.setVlasnik("Mika Mikic");
    	u.setRacunPrimaoca(t2);
    	u.setSvrhaPlacanja("Eto 'nako");
    	p.setUplata(u);
    	novi.setPlacanje(p);
    	//Status s;
		try {
			//s = b.receiveNalog(novi);
	    	//System.out.println(s.getStatusText());
			//System.out.println(b.checkNalog(novi));
			b.receiveNalog(novi);
			System.out.println("Done.");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
