package yu.ac.ns.ftn.informatika.ws.service.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sayHello", namespace = "http://informatika.ftn.ns.ac.yu/ws/service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHello", namespace = "http://informatika.ftn.ns.ac.yu/ws/service", propOrder = {"firstName","lastName"})

public class SayHello {
	
	@XmlElement(name = "firstName")
    private java.lang.String firstName;
    @XmlElement(name = "lastName")
    private java.lang.String lastName;

    public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String newFirstName)  {
        this.firstName = newFirstName;
    }

    public java.lang.String getLastName() {
        return this.lastName;
    }

    public void setLastName(java.lang.String newLastName)  {
        this.lastName = newLastName;
    }
}
