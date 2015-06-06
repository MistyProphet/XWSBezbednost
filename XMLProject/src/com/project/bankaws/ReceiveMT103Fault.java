
package com.project.bankaws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-06T03:02:46.266+02:00
 * Generated source version: 2.6.5
 */

@WebFault(name = "status", targetNamespace = "http://www.project.com/common_types")
public class ReceiveMT103Fault extends Exception {
    
    private com.project.common_types.Status status;

    public ReceiveMT103Fault() {
        super();
    }
    
    public ReceiveMT103Fault(String message) {
        super(message);
    }
    
    public ReceiveMT103Fault(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiveMT103Fault(String message, com.project.common_types.Status status) {
        super(message);
        this.status = status;
    }

    public ReceiveMT103Fault(String message, com.project.common_types.Status status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public com.project.common_types.Status getFaultInfo() {
        return this.status;
    }
}
