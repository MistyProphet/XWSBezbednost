
package yu.ac.ns.ftn.informatika.ws.hello.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the yu.ac.ns.ftn.informatika.ws.hello.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RequestRTGS_QNAME = new QName("http://informatika.ftn.ns.ac.yu/ws/hello/types", "RequestRTGS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: yu.ac.ns.ftn.informatika.ws.hello.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseRTGS }
     * 
     */
    public ResponseRTGS createResponseRTGS() {
        return new ResponseRTGS();
    }

    /**
     * Create an instance of {@link RTGSnalog }
     * 
     */
    public RTGSnalog createRTGSnalog() {
        return new RTGSnalog();
    }

    /**
     * Create an instance of {@link RTGSnalog.PodaciOUplati }
     * 
     */
    public RTGSnalog.PodaciOUplati createRTGSnalogPodaciOUplati() {
        return new RTGSnalog.PodaciOUplati();
    }

    /**
     * Create an instance of {@link ResponseRTGS.PodaciOZaduzenju }
     * 
     */
    public ResponseRTGS.PodaciOZaduzenju createResponseRTGSPodaciOZaduzenju() {
        return new ResponseRTGS.PodaciOZaduzenju();
    }

    /**
     * Create an instance of {@link RTGSnalog.PodaciOBankama }
     * 
     */
    public RTGSnalog.PodaciOBankama createRTGSnalogPodaciOBankama() {
        return new RTGSnalog.PodaciOBankama();
    }

    /**
     * Create an instance of {@link RTGSnalog.PodaciOUplati.OpstiDeo }
     * 
     */
    public RTGSnalog.PodaciOUplati.OpstiDeo createRTGSnalogPodaciOUplatiOpstiDeo() {
        return new RTGSnalog.PodaciOUplati.OpstiDeo();
    }

    /**
     * Create an instance of {@link RTGSnalog.PodaciOUplati.Uplata }
     * 
     */
    public RTGSnalog.PodaciOUplati.Uplata createRTGSnalogPodaciOUplatiUplata() {
        return new RTGSnalog.PodaciOUplati.Uplata();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RTGSnalog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://informatika.ftn.ns.ac.yu/ws/hello/types", name = "RequestRTGS")
    public JAXBElement<RTGSnalog> createRequestRTGS(RTGSnalog value) {
        return new JAXBElement<RTGSnalog>(_RequestRTGS_QNAME, RTGSnalog.class, null, value);
    }

}
