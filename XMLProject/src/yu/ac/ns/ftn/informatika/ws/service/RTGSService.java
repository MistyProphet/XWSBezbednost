package yu.ac.ns.ftn.informatika.ws.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "RTGSService",
targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/service")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface RTGSService {
	@RequestWrapper(className="yu.ac.ns.ftn.informatika.ws.service.type.SayHello")
	@ResponseWrapper(className="yu.ac.ns.ftn.informatika.ws.service.type.SayHelloResponse")
	public String sayHello(@WebParam(name = "firstName") String firstName, 
						   @WebParam(name = "lastName") String lastName);
}
