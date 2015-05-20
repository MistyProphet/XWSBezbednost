package yu.ac.ns.ftn.informatika.ws.service;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService(portName = "RTGSServicePort", 
			serviceName = "RTGSServiceService", 
			targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/service",
			endpointInterface = "yu.ac.ns.ftn.informatika.ws.service.RTGSService", 
			wsdlLocation = "WEB-INF/wsdl/RTGSService.wsdl", 
			name = "RTGSService")
public class RTGSServiceImpl implements RTGSService {
	public RTGSServiceImpl() {
		System.out.println("Create RTGSServiceImpl object");
	}

	public String sayHello(String firstName, String lastName) {
		System.out.println("Invoked RTGSServiceImpl sayHello() method");
		return "Hello world " + firstName + " " + lastName;
	}
}
