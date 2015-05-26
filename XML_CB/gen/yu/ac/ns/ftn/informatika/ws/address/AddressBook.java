package yu.ac.ns.ftn.informatika.ws.address;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-05-26T13:07:48.814+02:00
 * Generated source version: 2.6.5
 * 
 */
@WebService(targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address", name = "AddressBook")
@XmlSeeAlso({yu.ac.ns.ftn.informatika.ws.address.types.ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AddressBook {

    @WebResult(name = "result", targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address", partName = "result")
    @WebMethod
    public yu.ac.ns.ftn.informatika.ws.address.types.ContactsType getAllContacts();

    @WebResult(name = "result", targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address", partName = "result")
    @WebMethod
    public yu.ac.ns.ftn.informatika.ws.address.types.ContactType getContact(
        @WebParam(partName = "index", name = "index")
        int index
    );

    @WebResult(name = "result", targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address", partName = "result")
    @WebMethod
    public yu.ac.ns.ftn.informatika.ws.address.types.ContactType getContactByName(
        @WebParam(partName = "firstName", name = "firstName")
        java.lang.String firstName,
        @WebParam(partName = "lastName", name = "lastName")
        java.lang.String lastName
    );

    @WebMethod
    public void addContact(
        @WebParam(partName = "contact", name = "contact")
        yu.ac.ns.ftn.informatika.ws.address.types.ContactType contact
    );
}
