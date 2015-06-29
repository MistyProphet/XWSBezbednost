package test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import com.project.bankaws.BankaPort;
import com.project.bankaws.BankaPortImpl;
import com.project.bankaws.PortHelper;
import com.project.bankaws.ReceiveNalogFault;
import com.project.common_types.TRacunKlijenta;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.nalog_za_placanje.Uplata;
import com.project.presek.Presek;
import com.project.stavka_preseka.StavkaPreseka;
import com.project.util.Util;
import com.project.zahtev_za_izvod.ZahtevZaIzvod;

public class Test {

	public void ispravanNalogUnutarBanke(){
		
		
		  try {
			  URL wsdlLocation = new URL(
						"http://localhost:8080/projCB/services/CB?wsdl");
		
				wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
		    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
		    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
		    	Service service = Service.create(wsdlLocation, serviceName);
		    	
		    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
	        	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
	        	/////                                                                    //////
			          Binding binding = ((BindingProvider)bankaPort).getBinding();
			          @SuppressWarnings("rawtypes")
			          List<Handler> handlerList = binding.getHandlerChain();
			          handlerList.clear();
			          handlerList.add(new WSSignatureHandler());
			          handlerList.add(new WSCryptoHandler());
			          binding.setHandlerChain(handlerList);  
	        	/////                                                                    //////
			    ///////////////////////////////////////////////////////////////////////////////
			bankaPort = new BankaPortImpl();
	        NalogZaPlacanje nalog = getNalog();
	        nalog.getPlacanje().getUplata().getRacunPrimaoca().setBrojRacuna("001-0000000000002-00");
				bankaPort.receiveNalog(nalog);
			} catch (ReceiveNalogFault e) {
				System.out.println(e.getMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void ispravanNalogVanBankeClearing(){
		
		
		  try {
			  URL wsdlLocation = new URL(
						"http://localhost:8080/projCB/services/CB?wsdl");
		
				wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
		    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
		    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
		    	Service service = Service.create(wsdlLocation, serviceName);
		    	
		    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
	        	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
	        	/////                                                                    //////
			          Binding binding = ((BindingProvider)bankaPort).getBinding();
			          @SuppressWarnings("rawtypes")
			          List<Handler> handlerList = binding.getHandlerChain();
			          handlerList.clear();
			          handlerList.add(new WSSignatureHandler());
			          handlerList.add(new WSCryptoHandler());
			          binding.setHandlerChain(handlerList);  
	        	/////                                                                    //////
			    ///////////////////////////////////////////////////////////////////////////////

				bankaPort.receiveNalog(getNalog());
			} catch (ReceiveNalogFault e) {
				System.out.println(e.getMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void ispravanNalogVanBankeRTGS(){
		
		
		  try {
			  URL wsdlLocation = new URL(
						"http://localhost:8080/projCB/services/CB?wsdl");
		
				wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
		    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
		    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
		    	Service service = Service.create(wsdlLocation, serviceName);
		    	
		    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
	        	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
	        	/////                                                                    //////
			          Binding binding = ((BindingProvider)bankaPort).getBinding();
			          @SuppressWarnings("rawtypes")
			          List<Handler> handlerList = binding.getHandlerChain();
			          handlerList.clear();
			          handlerList.add(new WSSignatureHandler());
			          handlerList.add(new WSCryptoHandler());
			          binding.setHandlerChain(handlerList);  
	        	/////                                                                    //////
			    ///////////////////////////////////////////////////////////////////////////////
	        NalogZaPlacanje nalog = getNalog();
	        nalog.getPlacanje().getUplata().setIznos(new BigDecimal(260000));
				bankaPort.receiveNalog(nalog);
			} catch (ReceiveNalogFault e) {
				System.out.println(e.getMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void losXMLNaloga(){
		
		
		  try {
			  URL wsdlLocation = new URL(
						"http://localhost:8080/projCB/services/CB?wsdl");
		
				wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
		    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
		    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
		    	Service service = Service.create(wsdlLocation, serviceName);
		    	
		    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
	        	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
	        	/////                                                                    //////
			          Binding binding = ((BindingProvider)bankaPort).getBinding();
			          @SuppressWarnings("rawtypes")
			          List<Handler> handlerList = binding.getHandlerChain();
			          handlerList.clear();
			          handlerList.add(new WSSignatureHandler());
			          handlerList.add(new WSCryptoHandler());
			          binding.setHandlerChain(handlerList);  
	        	/////                                                                    //////
			    ///////////////////////////////////////////////////////////////////////////////
	        NalogZaPlacanje nalog = getNalog();
	        nalog.getPlacanje().setUplata(null);
				bankaPort.receiveNalog(nalog);
			
			} catch (ReceiveNalogFault e) {
				System.out.println(e.getMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
public void nemaTolikoParaNalog(){
		
		
		  try {
			  URL wsdlLocation = new URL(
						"http://localhost:8080/projCB/services/CB?wsdl");
		
				wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
		    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
		    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
		    	Service service = Service.create(wsdlLocation, serviceName);
		    	
		    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
	        	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
	        	/////                                                                    //////
			          Binding binding = ((BindingProvider)bankaPort).getBinding();
			          @SuppressWarnings("rawtypes")
			          List<Handler> handlerList = binding.getHandlerChain();
			          handlerList.clear();
			          handlerList.add(new WSSignatureHandler());
			          handlerList.add(new WSCryptoHandler());
			          binding.setHandlerChain(handlerList);  
	        	/////                                                                    //////
			    ///////////////////////////////////////////////////////////////////////////////
	        NalogZaPlacanje nalog = getNalog();
	        nalog.getPlacanje().getUplata().setIznos(new BigDecimal(300000));
				bankaPort.receiveNalog(nalog);
			} catch (ReceiveNalogFault e) {
				System.out.println(e.getMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
public void racunUBanciNePostojiNalog(){
	
	
	  try {
		  URL wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");
	
			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	
	    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
      	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
      	/////                                                                    //////
		          Binding binding = ((BindingProvider)bankaPort).getBinding();
		          @SuppressWarnings("rawtypes")
		          List<Handler> handlerList = binding.getHandlerChain();
		          handlerList.clear();
		          handlerList.add(new WSSignatureHandler());
		          handlerList.add(new WSCryptoHandler());
		          binding.setHandlerChain(handlerList);  
      	/////                                                                    //////
		    ///////////////////////////////////////////////////////////////////////////////
        NalogZaPlacanje nalog = getNalog();
        nalog.getPlacanje().getUplata().getRacunPrimaoca().setBrojRacuna("001-9999999999999-00");
			bankaPort.receiveNalog(nalog);
		} catch (ReceiveNalogFault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

public void racunVanBankeNePostojiNalog(){
	
	
	  try {
		  URL wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");
	
			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	
	    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
      	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
      	/////                                                                    //////
		          Binding binding = ((BindingProvider)bankaPort).getBinding();
		          @SuppressWarnings("rawtypes")
		          List<Handler> handlerList = binding.getHandlerChain();
		          handlerList.clear();
		          handlerList.add(new WSSignatureHandler());
		          handlerList.add(new WSCryptoHandler());
		          binding.setHandlerChain(handlerList);  
      	/////                                                                    //////
		    ///////////////////////////////////////////////////////////////////////////////
        NalogZaPlacanje nalog = getNalog();
        nalog.getPlacanje().getUplata().getRacunPrimaoca().setBrojRacuna("002-9999999999999-00");
			bankaPort.receiveNalog(nalog);
		} catch (ReceiveNalogFault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

public void bankeNePostojiNalog(){
	
	
	  try {
		  URL wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");
	
			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	
	    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
      	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
      	/////                                                                    //////
		          Binding binding = ((BindingProvider)bankaPort).getBinding();
		          @SuppressWarnings("rawtypes")
		          List<Handler> handlerList = binding.getHandlerChain();
		          handlerList.clear();
		          handlerList.add(new WSSignatureHandler());
		          handlerList.add(new WSCryptoHandler());
		          binding.setHandlerChain(handlerList);  
      	/////                                                                    //////
		    ///////////////////////////////////////////////////////////////////////////////
        NalogZaPlacanje nalog = getNalog();
        nalog.getPlacanje().getUplata().getRacunPrimaoca().setBrojRacuna("999-9999999999999-00");
			bankaPort.receiveNalog(nalog);
		} catch (ReceiveNalogFault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

public void racunBlokiranNalog(){
	
	  try {
		  URL wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");
	
			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	
	    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
      	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
      	/////                                                                    //////
		          Binding binding = ((BindingProvider)bankaPort).getBinding();
		          @SuppressWarnings("rawtypes")
		          List<Handler> handlerList = binding.getHandlerChain();
		          handlerList.clear();
		          handlerList.add(new WSSignatureHandler());
		          handlerList.add(new WSCryptoHandler());
		          binding.setHandlerChain(handlerList);  
      	/////                                                                    //////
		    ///////////////////////////////////////////////////////////////////////////////
        NalogZaPlacanje nalog = getNalog();
        nalog.getPlacanje().getUplata().getRacunPrimaoca().setBrojRacuna("001-0000000000003-00");
			bankaPort.receiveNalog(nalog);
		} catch (ReceiveNalogFault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

	private Long id = (long) 1;
	public NalogZaPlacanje getNalog(){
        NalogZaPlacanje nalog = new NalogZaPlacanje();
        nalog.setHitno(false);
        Placanje placanje = new Placanje();
        placanje.setIDPoruke(id.toString());
        id++;
        placanje.setSifraValute("RSD");
    	Uplata u = new Uplata();
		u.setIznos(new BigDecimal(323));
		u.setModelOdobrenja(new Long(97));
		u.setModelZaduzenja(new Long(97));
		u.setPozivNaBrojOdobrenja("22222222222222222222");
		u.setPozivNaBrojZaduzenja("22222222222222222222");
		u.setSvrhaPlacanja("svrha placanja");
		
		TRacunKlijenta primaoc = new TRacunKlijenta();
		primaoc.setBrojRacuna("002-0000000000002-00");
		primaoc.setId(new Long(2));
		primaoc.setVlasnik("Primaoc");
		
		TRacunKlijenta tr = new TRacunKlijenta();
		tr.setBrojRacuna("001-0000000000001-00");
		tr.setId(new Long(1));
		tr.setVlasnik("Vlasnik");
		u.setRacunPrimaoca(primaoc);
		u.setRacunDuznika(tr);
        placanje.setUplata(u);
    	GregorianCalendar cal = new GregorianCalendar();
		DatatypeFactory datatypeFactory;
		try {
			datatypeFactory = DatatypeFactory.newInstance();

			XMLGregorianCalendar now = datatypeFactory
					.newXMLGregorianCalendar(cal);
			u.setDatumNaloga(now);
			nalog.setDatumValute(now);
			
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        nalog.setPlacanje(placanje);
        
        
        return nalog;
	}
	
	private void getPresek() {
		try{
			URL wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");
	
			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	
	    	BankaPort bankaPort = service.getPort(portName, BankaPort.class);
        	///////////////////////// DEO ZA KLIJENTSKI HANDLER ///////////////////////////
        	/////                                                                    //////
		          Binding binding = ((BindingProvider)bankaPort).getBinding();
		          @SuppressWarnings("rawtypes")
		          List<Handler> handlerList = binding.getHandlerChain();
		          handlerList.clear();
		          handlerList.add(new WSSignatureHandler());
		          handlerList.add(new WSCryptoHandler());
		          binding.setHandlerChain(handlerList);  
        	/////                                                                    //////
		    ///////////////////////////////////////////////////////////////////////////////
            ZahtevZaIzvod zahtev = new ZahtevZaIzvod();
            zahtev.setBrojRacuna("001-0000000000001-00");
            zahtev.setRedniBrojPreseka(1);
            zahtev.setDatum(Util.getXMLGregorianCalendarNow());
        	Presek s = bankaPort.sendPresek(zahtev);
        	System.out.println("Zaglavlje: "+s.getZaglavljePreseka().getBrojRacuna());
        	for(StavkaPreseka sp: s.getStavkaPreseka()){
        		System.out.println("-------------------");
        		System.out.println("SMER: "+sp.getSmer());
        		System.out.println("IZNOS: "+sp.getUplata().getIznos());
        		System.out.println("-------------------");
        	}
		}catch(Exception e){
			e.printStackTrace();
		}
		ResourceBundle b = ResourceBundle.getBundle ("resource.deploy"+PortHelper.current_bank.getId());
		PortHelper.KEY_STORE_FILE_CB =  (String) b.getObject("cb.file");
		PortHelper.KEY_STORE_PASSWORD_CB =  (String) b.getObject("cb.password");
		return;
	}
	
	
	public static void main(String[] args) {
		Test test = new Test();
		/*
		test.ispravanNalogUnutarBanke();
		test.ispravanNalogVanBankeClearing();
		test.ispravanNalogVanBankeRTGS();
		test.bankeNePostojiNalog();
		test.losXMLNaloga();
		test.nemaTolikoParaNalog();
		test.racunUBanciNePostojiNalog();
		test.racunVanBankeNePostojiNalog();
		*/
		test.getPresek();
	}

	
}
