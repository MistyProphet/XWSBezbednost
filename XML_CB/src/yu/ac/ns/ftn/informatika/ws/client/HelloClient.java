package yu.ac.ns.ftn.informatika.ws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import yu.ac.ns.ftn.informatika.ws.hello.RTGSDocument;
import yu.ac.ns.ftn.informatika.ws.hello.types.RTGSnalog;
import yu.ac.ns.ftn.informatika.ws.hello.types.RTGSnalog.PodaciOBankama;
import yu.ac.ns.ftn.informatika.ws.hello.types.RTGSnalog.PodaciOUplati;
import yu.ac.ns.ftn.informatika.ws.hello.types.RTGSnalog.PodaciOUplati.OpstiDeo;
import yu.ac.ns.ftn.informatika.ws.hello.types.ResponseRTGS;



public class HelloClient {

	public void testIt1() {

		try {
			URL wsdlLocation = new URL("http://localhost:8080/vezbe-wsdl-ws/services/HelloDocument?wsdl");
			QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "RTGSDocumentService");
			QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "RTGSDocumentPort");
			Service service = Service.create(wsdlLocation, serviceName);

			RTGSDocument RTGS = service.getPort(portName,RTGSDocument.class);

			RTGSnalog request = new RTGSnalog();
			request.setIDPoruke("idPoruke");
			request.setPodaciOBankama(new PodaciOBankama());
			PodaciOUplati uplata = new PodaciOUplati();
			OpstiDeo opstiDeo = new OpstiDeo();
			opstiDeo.setDuznik("duznik");
			uplata.setOpstiDeo(opstiDeo);
			request.setPodaciOUplati(uplata);
			 ResponseRTGS response = RTGS.requestRTGS(request);
			System.out.println("Response from WS: " + response.getIDPoruke());

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