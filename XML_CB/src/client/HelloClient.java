package client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.project.cbws.BankaPort;
import com.project.cbws.SendMT103Fault;
import com.project.mt103.Mt103;
import com.project.mt103.Mt103.PodaciOBankama;
import com.project.mt103.Mt103.PodaciOUplati;
import com.project.mt103.Mt103.PodaciOUplati.OpstiDeo;
import com.project.mt900.Mt900;


public class HelloClient {

	public void testIt1() {

		try {
			URL wsdlLocation = new URL("http://localhost:8080/vezbe-wsdl-ws/services/CB?wsdl");
			QName serviceName = new QName("http://www.project.com/CBws","BankaService");
			QName portName = new QName("http://www.project.com/CBws", "BankaPort");
			Service service = Service.create(wsdlLocation, serviceName);
		
			
			BankaPort	RTGS = service.getPort(portName,BankaPort.class);

			Mt103 request = new Mt103();
			request.setIDPoruke("idPoruke");
			request.setPodaciOBankama(new PodaciOBankama());
			PodaciOUplati uplata = new PodaciOUplati();
			OpstiDeo opstiDeo = new OpstiDeo();
			opstiDeo.setDuznik("duznik");
			uplata.setOpstiDeo(opstiDeo);
			request.setPodaciOUplati(uplata);
			 Mt900 response;
			try {
				response = RTGS.recieveMT103(request);
				System.out.println("Response from WS: " + response.getIDPoruke());

			} catch (SendMT103Fault e) {
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