
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package yu.ac.ns.ftn.informatika.ws.address;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-08T17:48:10.062+02:00
 * Generated source version: 2.6.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "AddressBookService",
                      portName = "AddressBookPort",
                      targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address",
                      wsdlLocation = "file:/C:/Users/Lillyput/Desktop/5. VezebeWSDLWS/WEB-INF/wsdl/AddressBook.wsdl",
                      endpointInterface = "yu.ac.ns.ftn.informatika.ws.address.AddressBook")
                      
public class AddressBookImpl implements AddressBook {

    private static final Logger LOG = Logger.getLogger(AddressBookImpl.class.getName());

    /* (non-Javadoc)
     * @see yu.ac.ns.ftn.informatika.ws.address.AddressBook#getAllContacts(*
     */
    public yu.ac.ns.ftn.informatika.ws.address.types.ContactsType getAllContacts() { 
        LOG.info("Executing operation getAllContacts");
        try {
            yu.ac.ns.ftn.informatika.ws.address.types.ContactsType _return = new yu.ac.ns.ftn.informatika.ws.address.types.ContactsType();
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see yu.ac.ns.ftn.informatika.ws.address.AddressBook#getContact(int  index )*
     */
    public yu.ac.ns.ftn.informatika.ws.address.types.ContactType getContact(int index) { 
        LOG.info("Executing operation getContact");
        System.out.println(index);
        try {
            yu.ac.ns.ftn.informatika.ws.address.types.ContactType _return = new yu.ac.ns.ftn.informatika.ws.address.types.ContactType();
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see yu.ac.ns.ftn.informatika.ws.address.AddressBook#getContactByName(java.lang.String  firstName ,)java.lang.String  lastName )*
     */
    public yu.ac.ns.ftn.informatika.ws.address.types.ContactType getContactByName(java.lang.String firstName,java.lang.String lastName) { 
        LOG.info("Executing operation getContactByName");
        System.out.println(firstName);
        System.out.println(lastName);
        try {
            yu.ac.ns.ftn.informatika.ws.address.types.ContactType _return = new yu.ac.ns.ftn.informatika.ws.address.types.ContactType();
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see yu.ac.ns.ftn.informatika.ws.address.AddressBook#addContact(yu.ac.ns.ftn.informatika.ws.address.types.ContactType  contact )*
     */
    public void addContact(yu.ac.ns.ftn.informatika.ws.address.types.ContactType contact) { 
        LOG.info("Executing operation addContact");
        System.out.println(contact);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
