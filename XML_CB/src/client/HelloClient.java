package client;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.project.cbws.CBport;
import com.project.common_types.TBanka;
import com.project.common_types.TRacunKlijenta;
import com.project.mt102.Mt102;
import com.project.mt103.Mt103;
import com.project.mt103.Mt103.PodaciOBankama;
import com.project.mt900.Mt900RTGS;
import com.project.nalog_za_placanje.Placanje;
import com.project.nalog_za_placanje.Uplata;

public class HelloClient {

	public void testIt1() {
		System.out.println(CBport.class.getAnnotation(WebService.class).name());

		URL wsdlLocation;
		try {
			wsdlLocation = new URL(
					"http://localhost:8081/vezbe-wsdl-ws/services/CB?wsdl");

			QName serviceName = new QName("http://www.project.com/CBws",
					"CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);

			CBport RTGS = service.getPort(portName, CBport.class);

			Mt103 request = new Mt103();
			request.setIDPoruke("1");
			PodaciOBankama pb = new PodaciOBankama();
			TBanka duznik = new TBanka();
			duznik.setBrojRacunaBanke("111-1231231231231-32");
			duznik.setSWIFTKod("AAAARS01");
			TBanka poverioc = new TBanka();
			poverioc.setBrojRacunaBanke("111-1231555551231-32");
			poverioc.setSWIFTKod("BBBBRS01");

			pb.setBankaDuznika(duznik);
			pb.setBankaPoverioca(poverioc);
			request.setPodaciOBankama(pb);

			Uplata u = new Uplata();
			u.setIznos(new BigDecimal(323));
			TRacunKlijenta tr = new TRacunKlijenta();
			tr.setBrojRacuna(duznik.getBrojRacunaBanke());
			u.setRacunPrimaoca(tr);
		
			request.setUplata(u);
			Mt900RTGS response;

			response = RTGS.receiveMT103CB(request);
			System.out.println("Response from WS: " + response.getIDPoruke());

			Mt102 mt102 = new Mt102();
			mt102.setIDPoruke("2");
			mt102.setBankaDuznika(duznik);
			mt102.setBankaPoverioca(poverioc);
			mt102.setIDPoruke("434");
			mt102.setSifraValute("din");
			mt102.setUkupanIznos((new BigDecimal(646)));
		ArrayList<Placanje> placanja = new ArrayList<Placanje>();
		Placanje placanje = new Placanje();
		placanje.setUplata(u);
		placanja.add(placanje);
		placanje = new Placanje(); 
		u.getRacunPrimaoca().setBrojRacuna("111-1231555551231-32");
		placanje.setUplata(u);
		placanja.add(placanje);
		mt102.setPlacanje(placanja);
		RTGS.receiveMT102CB(mt102);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	
	}

	public static void main(String[] args) {

		HelloClient client = new HelloClient();
		client.testIt1();
		// client.testIt2();
	}

}