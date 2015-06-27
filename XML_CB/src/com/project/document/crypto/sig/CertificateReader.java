package com.project.document.crypto.sig;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import misc.RESTUtil;

import org.w3c.dom.Document;

import com.project.security.CRL;
import misc.DocumentUtil;

/**
 *
 *Cita sertifikat iz fajla
 */
public class CertificateReader {

	public static final String BASE64_ENC_CERT_FILE = "./data/jovan2.cer";
	public static final String BIN_ENC_CERT_FILE = "./data/jovan1.cer";
	public static final String testCert = "./data/crlTest.cer";
	
	/* za konacnu verziju, sa pravim fajovima
	 * String propFile = "deploy"+ID_Instance_Banke;
	 * public static final String BASE64_ENC_CERT_FILE = ResourceBundle.getBundle(propFile).getString("cert.file");
	 * public static final String CLR_FILE = ResourceBundle.getBundle(propFile).getString("crl.file");
	 */
	
	//private static List<Certificate> certificateList = new ArrayList<Certificate>();
	private static CRL crl = new CRL();
	private static List<Certificate> certificateList = new ArrayList<Certificate>();
	
	public void testIt() {
		System.out.println("Cita sertifikat iz Base64 formata");
		Certificate test = loadBase64CertificateFromPath(testCert);
		readFromBase64EncFile();

		saveCRL();
		crl = null;
		loadCRL();
		
		sign();
		
	}
	
	//pocetno citanje certifikata iz Base64 fajla
	//poziva se po kreiranju resursa
	public static void readFromBase64EncFile() {
		try {
			FileInputStream fis = new FileInputStream(BASE64_ENC_CERT_FILE);
			 BufferedInputStream bis = new BufferedInputStream(fis);

			 CertificateFactory cf = CertificateFactory.getInstance("X.509");

			 //cita sertifikat po sertifikat
			 //i vrsi se pozicioniranje na pocetak sledeceg
			 //svaki certifikat je izmedju 
			 //-----BEGIN CERTIFICATE-----, 
			 //i
			 //-----END CERTIFICATE-----. 

			 Certificate cert = null;
			 while (bis.available() > 0) {
			    cert = cf.generateCertificate(bis);
			    certificateList.add(cert);
			    crl.getCertificate().add(cert.toString());
			    System.out.println(cert.toString());	   
			 }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void saveCRL() {
		if (crl.getCertificate().size() > 0) {
			RESTUtil.doMarshall("/crl", crl);
		}
	}
	
	private static void loadCRL() {
		crl = (CRL)RESTUtil.doUnmarshall("//*:CRL", "/crl", new CRL());
	}
	
	public void sign() {
		try {
			JAXBContext jc = JAXBContext.newInstance(CRL.class);
		
		 	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document document = db.newDocument();

	        // Marshal the Object to a Document
	        Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(crl, document);
			DocumentUtil.printDocument(document);
			Document signedDocument = SignEnveloped.signDocument(document, false);	
			
			RESTUtil.objectToDB("", "crl", signedDocument);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	        
	}
	
	private Certificate loadBase64CertificateFromPath(String path) {
		try{
		FileInputStream fis = new FileInputStream(path);
		 BufferedInputStream bis = new BufferedInputStream(fis);

		 CertificateFactory cf = CertificateFactory.getInstance("X.509");

		 //cita sertifikat po sertifikat
		 //i vrsi se pozicioniranje na pocetak sledeceg
		 //svaki certifikat je izmedju 
		 //-----BEGIN CERTIFICATE-----, 
		 //i
		 //-----END CERTIFICATE-----. 

			Certificate cert = null;

		 while (bis.available() > 0) {
		    cert = cf.generateCertificate(bis);
		    System.out.println(cert.toString());
			return cert;
		 }

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (CertificateException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		return null;
	}
		

	private static void convertCRLtoCertificateList() {
		try {
        
			if (crl.getCertificate().size()==0) {
				loadCRL();
			}
			 CertificateFactory cf = CertificateFactory.getInstance("X.509");
			 Certificate cert = null;
			 for (String s : crl.getCertificate()) {
			    cert = cf.generateCertificate(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
			    certificateList.add(cert);
			    System.out.println(cert.toString());	   
			 }

		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		CertificateReader test = new CertificateReader();
		test.testIt();
	}


	public static boolean checkIfWithdrown(Certificate cert) {

		if (certificateList.size()==0) {
			convertCRLtoCertificateList();
		}
		
		for (Certificate certificate : certificateList) {
			
			if (certificate.equals(cert)) {
				return true;
			}
		}
		return false;
		
	}
	

}
