
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.project.cbws;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-05-27T22:41:37.689+02:00
 * Generated source version: 2.6.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "BankaService",
                      portName = "BankaPort",
                      targetNamespace = "http://www.project.com/CBws",
                      wsdlLocation = "file:/C:/Users/Lillyput/Desktop/tim6/XWS_CB/WEB-INF/wsdl/Banka.wsdl",
                      endpointInterface = "com.project.cbws.BankaPort")
                      
public class BankaPortImpl implements BankaPort {

    private static final Logger LOG = Logger.getLogger(BankaPortImpl.class.getName());

    /* (non-Javadoc)
     * @see com.project.cbws.BankaPort#recieveMT102(com.project.mt102.Mt102  mt102 )*
     */
    public com.project.mt900.Mt900 recieveMT102(com.project.mt102.Mt102 mt102) throws SendMT102Fault    { 
        LOG.info("Executing operation recieveMT102");
        System.out.println(mt102);
        try {
            com.project.mt900.Mt900 _return = new com.project.mt900.Mt900();
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new SendMT102Fault("sendMT102fault...");
    }

    /* (non-Javadoc)
     * @see com.project.cbws.BankaPort#recieveMT103(com.project.mt103.Mt103  mt103 )*
     */
    public com.project.mt900.Mt900 recieveMT103(com.project.mt103.Mt103 mt103) throws SendMT103Fault    { 
        LOG.info("Executing operation recieveMT103");
        System.out.println(mt103);
        try {
            com.project.mt900.Mt900 _return = new com.project.mt900.Mt900();
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new SendMT103Fault("sendMT103fault...");
    }

}
