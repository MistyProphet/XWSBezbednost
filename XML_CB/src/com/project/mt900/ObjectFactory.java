
package com.project.mt900;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.project.mt900 package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.project.mt900
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Mt900Clearing }
     * 
     */
    public Mt900Clearing createMt900Clearing() {
        return new Mt900Clearing();
    }

    /**
     * Create an instance of {@link Mt900RTGS }
     * 
     */
    public Mt900RTGS createMt900RTGS() {
        return new Mt900RTGS();
    }

    /**
     * Create an instance of {@link Mt900Clearing.PodaciOZaduzenju }
     * 
     */
    public Mt900Clearing.PodaciOZaduzenju createMt900ClearingPodaciOZaduzenju() {
        return new Mt900Clearing.PodaciOZaduzenju();
    }

    /**
     * Create an instance of {@link Mt900RTGS.PodaciOZaduzenju }
     * 
     */
    public Mt900RTGS.PodaciOZaduzenju createMt900RTGSPodaciOZaduzenju() {
        return new Mt900RTGS.PodaciOZaduzenju();
    }

}
