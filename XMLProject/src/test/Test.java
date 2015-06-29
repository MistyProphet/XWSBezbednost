package test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.project.bankaws.BankaPort;
import com.project.bankaws.BankaPortImpl;
import com.project.bankaws.ReceiveNalogFault;
import com.project.common_types.TBanka;
import com.project.common_types.TRacunKlijenta;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.nalog_za_placanje.Uplata;

public class Test {

	public void ispravanNalogUnutarBanke(){
		
		URL wsdlLocation;
		  try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	BankaPortImpl bankaPort = new BankaPortImpl();

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
		
		URL wsdlLocation;
		  try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	    	BankaPortImpl bankaPort = new BankaPortImpl();

				bankaPort.receiveNalog(getNalog());
			} catch (ReceiveNalogFault e) {
				System.out.println(e.getMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void ispravanNalogVanBankeRTGS(){
		
		URL wsdlLocation;
		  try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	        BankaPortImpl bankaPort = new BankaPortImpl();
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
		
		URL wsdlLocation;
		  try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	        BankaPortImpl bankaPort = new BankaPortImpl();
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
		
		URL wsdlLocation;
		  try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
	    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
	    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
	    	Service service = Service.create(wsdlLocation, serviceName);
	        BankaPortImpl bankaPort = new BankaPortImpl();
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
	
	URL wsdlLocation;
	  try {
		wsdlLocation = new URL(
				"http://localhost:8080/projCB/services/CB?wsdl");

		wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
    	Service service = Service.create(wsdlLocation, serviceName);
        BankaPortImpl bankaPort = new BankaPortImpl();
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
	
	URL wsdlLocation;
	  try {
		wsdlLocation = new URL(
				"http://localhost:8080/projCB/services/CB?wsdl");

		wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
    	Service service = Service.create(wsdlLocation, serviceName);
        BankaPortImpl bankaPort = new BankaPortImpl();
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
	
	URL wsdlLocation;
	  try {
		wsdlLocation = new URL(
				"http://localhost:8080/projCB/services/CB?wsdl");

		wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
    	Service service = Service.create(wsdlLocation, serviceName);
        BankaPortImpl bankaPort = new BankaPortImpl();
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
	
	URL wsdlLocation;
	  try {
		wsdlLocation = new URL(
				"http://localhost:8080/projCB/services/CB?wsdl");

		wsdlLocation = new URL("http://localhost:8080/proj/services/Banka?wsdl");
    	QName serviceName = new QName("http://www.project.com/BankaWS", "BankaService");
    	QName portName = new QName("http://www.project.com/BankaWS", "BankaPort");
    	Service service = Service.create(wsdlLocation, serviceName);
        BankaPortImpl bankaPort = new BankaPortImpl();
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


	public NalogZaPlacanje getNalog(){
        NalogZaPlacanje nalog = new NalogZaPlacanje();
        nalog.setHitno(false);
        Placanje placanje = new Placanje();
        placanje.setIDPoruke("1");
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
		primaoc.setId(new Long(111));
		primaoc.setVlasnik("Primaoc");
		
		TRacunKlijenta tr = new TRacunKlijenta();
		tr.setBrojRacuna("001-0000000000001-00");
		tr.setId(new Long(111));
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
	
	
	public static void main(String[] args) {
		Test test = new Test();
		test.ispravanNalogUnutarBanke();
		test.ispravanNalogVanBankeClearing();
		test.ispravanNalogVanBankeRTGS();
		test.bankeNePostojiNalog();
		test.losXMLNaloga();
		test.nemaTolikoParaNalog();
		test.racunUBanciNePostojiNalog();
		test.racunVanBankeNePostojiNalog();
		
	}
}
