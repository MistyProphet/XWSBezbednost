
package yu.ac.ns.ftn.informatika.ws.hello;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-05-26T11:11:30.656+02:00
 * Generated source version: 2.6.5
 * 
 */
public final class RTGSDocument_RTGSDocumentPort_Client {

    private static final QName SERVICE_NAME = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "RTGSDocumentService");

    private RTGSDocument_RTGSDocumentPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RTGSDocumentService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        RTGSDocumentService ss = new RTGSDocumentService(wsdlURL, SERVICE_NAME);
        RTGSDocument port = ss.getRTGSDocumentPort();  
        
        {
        System.out.println("Invoking requestRTGS...");
        yu.ac.ns.ftn.informatika.ws.hello.types.RTGSnalog _requestRTGS_requestRTGS = new yu.ac.ns.ftn.informatika.ws.hello.types.RTGSnalog();
        yu.ac.ns.ftn.informatika.ws.hello.types.ResponseRTGS _requestRTGS__return = port.requestRTGS(_requestRTGS_requestRTGS);
        System.out.println("requestRTGS.result=" + _requestRTGS__return);


        }

        System.exit(0);
    }

}
