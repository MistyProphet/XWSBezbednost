
package com.project.cbws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-05-26T20:07:50.481+02:00
 * Generated source version: 2.6.5
 */

@WebFault(name = "status", targetNamespace = "http://www.project.com/common_types")
public class SendMT102Fault extends Exception {
    
    private com.project.common_types.Status status;

    public SendMT102Fault() {
        super();
    }
    
    public SendMT102Fault(String message) {
        super(message);
    }
    
    public SendMT102Fault(String message, Throwable cause) {
        super(message, cause);
    }

    public SendMT102Fault(String message, com.project.common_types.Status status) {
        super(message);
        this.status = status;
    }

    public SendMT102Fault(String message, com.project.common_types.Status status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public com.project.common_types.Status getFaultInfo() {
        return this.status;
    }
}