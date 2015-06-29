
package com.project.cbws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-08T17:48:04.805+02:00
 * Generated source version: 2.6.5
 * 
 */
 
public class BankaPort_BankaPort_Server{

    protected BankaPort_BankaPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new BankaPortImpl();
        String address = "http://localhost:8081/BankaPort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new BankaPort_BankaPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}