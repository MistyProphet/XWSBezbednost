package client;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.project.cbws.CBport;
import com.project.cbws.CBportImpl;
import com.project.cbws.RecieveMT103Fault;
import com.project.common_types.TBanka;
import com.project.mt103.Mt103;
import com.project.mt103.Mt103.PodaciOBankama;
import com.project.mt900.Mt900RTGS;
import com.project.nalog_za_placanje.Uplata;


public class HelloClient {

	public void testIt1() {

		try {
			URL wsdlLocation = new URL("http://localhost:8081/vezbe-wsdl-ws/services/CB?wsdl");
			QName serviceName = new QName("http://www.project.com/CBws","CBservice");
			QName portName = new QName("http://www.project.com/CBws", "CBport");
			Service service = Service.create(wsdlLocation, serviceName);
		
			
			CBport	RTGS = service.getPort(portName,CBport.class);

			Mt103 request = new Mt103();
			request.setIDPoruke("CCtRS04");
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
			u.setIznos(new BigDecimal(4354));
			
			request.setUplata(u);
			

			
			
			 Mt900RTGS response;
			try {
				response = RTGS.recieveMT103CB(request);
				System.out.println("Response from WS: " + response.getIDPoruke());

			} catch (RecieveMT103Fault e) {
				System.out.println("fault");
				e.printStackTrace();
			}
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		HelloClient client = new HelloClient();
		client.testIt1();
		// client.testIt2();
	}

}