package com.project.document.crypto.sig;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 *Cita sertifikat iz fajla
 */
public class CertificateReader {

	public static final String BASE64_ENC_CERT_FILE = "./data/jovan2.cer";
	public static final String BIN_ENC_CERT_FILE = "./data/jovan1.cer";
	public static final String CLR_FILE = "./data/clr.xml";
	
	/* za konacnu verziju, sa pravim fajovima
	 * String propFile = "deploy"+ID_Instance_Banke;
	 * public static final String BASE64_ENC_CERT_FILE = ResourceBundle.getBundle(propFile).getString("cert.file");
	 * public static final String CLR_FILE = ResourceBundle.getBundle(propFile).getString("crl.file");
	 */
	
	private static List<Certificate> certificateList = new ArrayList<Certificate>();
 	
	public void testIt() {
		System.out.println("Cita sertifikat iz Base64 formata");
		readFromBase64EncFile();
	//	System.out.println("\n\nCita sertifikat iz binarnog formata");
	//	readFromBinEncFile();
	}
	
	
	private static void readFromBase64EncFile() {
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
	
	private void certificateListToCRL() {
		
	}
	
	private void readFromBinEncFile() {
		//	try {
		//	FileInputStream fis = new FileInputStream(BASE64_ENC_CERT_FILE);
		/*	 BufferedInputStream bis = new BufferedInputStream(fis);

			 CertificateFactory cf = CertificateFactory.getInstance("X.509");

			 //cita sertifikat po sertifikat
			 //i vrsi se pozicioniranje na pocetak sledeceg
			 //svaki certifikat je izmedju 
			 //-----BEGIN CERTIFICATE-----, 
			 //i
			 //-----END CERTIFICATE-----. 
			 while (bis.available() > 0) {
			    Certificate cert = cf.generateCertificate(bis);
			    System.out.println(cert.toString());
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	

	}
	
	public static void main(String[] args) {
		CertificateReader test = new CertificateReader();
		test.testIt();
	}


	public static boolean checkIfWithdrown(Certificate cert) {
		readFromBase64EncFile();
		for (Certificate certificate : certificateList) {
			if (certificate.equals(cert)) {
				return true;
			}
		}
		return false;
		
	}
	
	
}
