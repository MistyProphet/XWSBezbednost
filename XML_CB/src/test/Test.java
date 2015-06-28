package test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.project.cbws.CBport;
import com.project.cbws.ReceiveMT102Fault;
import com.project.common_types.TBanka;
import com.project.common_types.TRacunKlijenta;
import com.project.mt102.Mt102;
import com.project.mt103.Mt103;
import com.project.mt103.Mt103.PodaciOBankama;
import com.project.mt900.Mt900RTGS;
import com.project.nalog_za_placanje.Placanje;
import com.project.nalog_za_placanje.Uplata;

public class Test {

	public void validanRTGS() {

		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			Mt103 request = new Mt103();
			request.setIDPoruke("1");
			PodaciOBankama pb = new PodaciOBankama();
			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("AAAARS01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("BBBBRS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));
			pb.setBankaDuznika(duznik);
			pb.setBankaPoverioca(poverioc);
			request.setPodaciOBankama(pb);

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(300000));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			u.setRacunPrimaoca(tr);
			u.setRacunDuznika(tr);
			request.setUplata(u);

			request.setSifraValute("RSD");

			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				request.setDatumValute(now);
				request.getUplata().setDatumNaloga(now);

			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Mt900RTGS mt900 = RTGS.receiveMT103CB(request);
			System.out.println("vracen mt900 sa ID=" + mt900.getIDPoruke());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void nemaParaRTGS() {

		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			Mt103 request = new Mt103();
			request.setIDPoruke("1");
			PodaciOBankama pb = new PodaciOBankama();
			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("AAAARS01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("BBBBRS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));
			pb.setBankaDuznika(duznik);
			pb.setBankaPoverioca(poverioc);
			request.setPodaciOBankama(pb);

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(400000));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			u.setRacunPrimaoca(tr);
			u.setRacunDuznika(tr);
			request.setUplata(u);

			request.setSifraValute("RSD");

			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				request.setDatumValute(now);
				request.getUplata().setDatumNaloga(now);

			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Mt900RTGS mt900 = RTGS.receiveMT103CB(request);
			System.out.println("vracen mt900 sa ID=" + mt900.getIDPoruke());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	public void losSWIFTRTGS() {

		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			Mt103 request = new Mt103();
			request.setIDPoruke("1");
			PodaciOBankama pb = new PodaciOBankama();
			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("FALSES01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("AAAARS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));
			pb.setBankaDuznika(duznik);
			pb.setBankaPoverioca(poverioc);
			request.setPodaciOBankama(pb);

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(323));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			u.setRacunPrimaoca(tr);
			u.setRacunDuznika(tr);
			request.setUplata(u);

			request.setSifraValute("RSD");

			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				request.setDatumValute(now);
				request.getUplata().setDatumNaloga(now);

			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Mt900RTGS mt900 = RTGS.receiveMT103CB(request);
			System.out.println("vracen mt900 sa ID=" + mt900.getIDPoruke());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void validanClearing() {
		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("AAAARS01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("AAAARS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(323));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			TRacunKlijenta primaoc = new TRacunKlijenta();
			primaoc.setBrojRacuna(poverioc.getBrojRacunaBanke());
			primaoc.setId(new Long(111));
			primaoc.setVlasnik("Primaoc");
			u.setRacunPrimaoca(primaoc);
			u.setRacunDuznika(tr);

			
			

			
			Mt102 mt102 = new Mt102();
			mt102.setBankaDuznika(duznik);
			mt102.setBankaPoverioca(poverioc);
			mt102.setIDPoruke("434");

			mt102.setUkupanIznos((new BigDecimal(646)));
			ArrayList<Placanje> placanja = new ArrayList<Placanje>();
			Placanje placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);

			placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);
			
			mt102.setPlacanje(placanja);
			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				mt102.setDatum(now);
				mt102.getPlacanje().get(0).getUplata().setDatumNaloga(now);
				// mt102.getPlacanje().get(1).getUplata().setDatumNaloga(now);
				mt102.setDatumValute(now);
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mt102.setSifraValute("RSD");

			RTGS.receiveMT102CB(mt102);
		} catch (ReceiveMT102Fault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public void losSWIFTClearing() {
		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("FALSES01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("AAAARS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(323));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			TRacunKlijenta primaoc = new TRacunKlijenta();
			primaoc.setBrojRacuna(poverioc.getBrojRacunaBanke());
			primaoc.setId(new Long(111));
			primaoc.setVlasnik("Primaoc");
			u.setRacunPrimaoca(primaoc);
			u.setRacunDuznika(tr);

			
			

			
			Mt102 mt102 = new Mt102();
			mt102.setBankaDuznika(duznik);
			mt102.setBankaPoverioca(poverioc);
			mt102.setIDPoruke("434");

			mt102.setUkupanIznos((new BigDecimal(646)));
			ArrayList<Placanje> placanja = new ArrayList<Placanje>();
			Placanje placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);

			placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);
			
			mt102.setPlacanje(placanja);
			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				mt102.setDatum(now);
				mt102.getPlacanje().get(0).getUplata().setDatumNaloga(now);
				// mt102.getPlacanje().get(1).getUplata().setDatumNaloga(now);
				mt102.setDatumValute(now);
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mt102.setSifraValute("RSD");

			RTGS.receiveMT102CB(mt102);
		} catch (ReceiveMT102Fault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void losUkupanIznosClearing() {
		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("AAAARS01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("AAAARS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(323));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			TRacunKlijenta primaoc = new TRacunKlijenta();
			primaoc.setBrojRacuna(poverioc.getBrojRacunaBanke());
			primaoc.setId(new Long(111));
			primaoc.setVlasnik("Primaoc");
			u.setRacunPrimaoca(primaoc);
			u.setRacunDuznika(tr);

			
			

			
			Mt102 mt102 = new Mt102();
			mt102.setBankaDuznika(duznik);
			mt102.setBankaPoverioca(poverioc);
			mt102.setIDPoruke("434");

			mt102.setUkupanIznos((new BigDecimal(600)));
			ArrayList<Placanje> placanja = new ArrayList<Placanje>();
			Placanje placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);

			placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);
			
			mt102.setPlacanje(placanja);
			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				mt102.setDatum(now);
				mt102.getPlacanje().get(0).getUplata().setDatumNaloga(now);
				// mt102.getPlacanje().get(1).getUplata().setDatumNaloga(now);
				mt102.setDatumValute(now);
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mt102.setSifraValute("RSD");

			RTGS.receiveMT102CB(mt102);
		} catch (ReceiveMT102Fault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void razliciteBankeClearing() {
		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8080/projCB/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("001-0000000000001-00");
			duznik.setSWIFTKod("AAAARS01");
			duznik.setId(new Long(111));
			duznik.setNazivBanke("UniCredit");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("001-0000000000001-00");
			poverioc.setSWIFTKod("AAAARS01");
			poverioc.setNazivBanke("Raiffeisen");
			poverioc.setId(new Long(112));

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(323));
			u.setModelOdobrenja(new Long(97));
			u.setModelZaduzenja(new Long(97));
			u.setPozivNaBrojOdobrenja("22222222222222222222");
			u.setPozivNaBrojZaduzenja("22222222222222222222");
			u.setSvrhaPlacanja("svrha placanja");
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			tr.setId(new Long(111));
			tr.setVlasnik("Vlasnik");
			TRacunKlijenta primaoc = new TRacunKlijenta();
			primaoc.setBrojRacuna(poverioc.getBrojRacunaBanke());
			primaoc.setId(new Long(111));
			primaoc.setVlasnik("Primaoc");
			u.setRacunPrimaoca(primaoc);
			u.setRacunDuznika(tr);

			
			Mt102 mt102 = new Mt102();
			mt102.setBankaDuznika(duznik);
			mt102.setBankaPoverioca(poverioc);
			mt102.setIDPoruke("434");

			mt102.setUkupanIznos((new BigDecimal(646)));
			ArrayList<Placanje> placanja = new ArrayList<Placanje>();
			Placanje placanje = new Placanje();
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);

			placanje = new Placanje();
			primaoc = new TRacunKlijenta();
			primaoc.setBrojRacuna("011-0000000000001-00");
			primaoc.setId(new Long(111));
			primaoc.setVlasnik("Primaoc");
			u.setRacunPrimaoca(primaoc);
			placanje.setUplata(u);
			placanje.setIDPoruke("1");
			placanje.setSifraValute("RSD");
			placanja.add(placanje);
			
			mt102.setPlacanje(placanja);
			GregorianCalendar cal = new GregorianCalendar();
			DatatypeFactory datatypeFactory;
			try {
				datatypeFactory = DatatypeFactory.newInstance();

				XMLGregorianCalendar now = datatypeFactory
						.newXMLGregorianCalendar(cal);
				mt102.setDatum(now);
				mt102.getPlacanje().get(0).getUplata().setDatumNaloga(now);
				// mt102.getPlacanje().get(1).getUplata().setDatumNaloga(now);
				mt102.setDatumValute(now);
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mt102.setSifraValute("RSD");

			RTGS.receiveMT102CB(mt102);
		} catch (ReceiveMT102Fault e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		Test client = new Test();

		client.validanRTGS();
		client.validanClearing();
		client.losSWIFTRTGS();
		client.losSWIFTClearing();
		client.losUkupanIznosClearing();
		client.razliciteBankeClearing();
	}

}