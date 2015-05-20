package yu.ac.ns.ftn.informatika.ws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import yu.ac.ns.ftn.informatika.ws.service.RTGSService;

public class RTGSServiceClient {
public void testIt() {
    	
		try {
			//Kreiranje web servisa
			URL wsdlLocation = new URL("http://localhost:8080/proj/services/RTGSService?wsdl");
			QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/service", "RTGSServiceService");
			QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/service", "RTGSServicePort");

			Service service = Service.create(wsdlLocation, serviceName);
			
			RTGSService hello = service.getPort(portName, RTGSService.class); 
			
			//Poziv web servisa
			String response = hello.sayHello("Mitar", "Miric");
			System.out.println("Response 1 from WS: " + response);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		
		RTGSServiceClient client = new RTGSServiceClient();
		client.testIt();
    }
}
