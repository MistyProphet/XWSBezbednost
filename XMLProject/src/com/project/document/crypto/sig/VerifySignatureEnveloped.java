package com.project.document.crypto.sig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.project.util.DocumentUtil;
import misc.RESTUtil;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.keyresolver.implementations.RSAKeyValueResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509CertificateResolver;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.project.banka.Banka;
import com.project.exceptions.WithdrawnCertificateException;
import com.project.exceptions.WrongIdSignatureException;
import com.project.exceptions.WrongSignatureException;
import com.project.exceptions.WrongTimestampException;
import com.project.transakcije.Transakcije;

//Vrsi proveru potpisa
public class VerifySignatureEnveloped {
    static {
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
	
	public static boolean verifySignature(Document doc) throws WrongTimestampException, WrongIdSignatureException, WithdrawnCertificateException {
		try {
			//Pronalazi se prvi Signature element 
			NodeList signatures = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
			Element signatureEl = (Element) signatures.item(0);
			System.out.println(signatureEl.getLocalName());
			System.out.println();
			java.util.Date now = new java.util.Date();
			
			//kreira se signature objekat od elementa
			XMLSignature signature = new XMLSignature(signatureEl, null);
			
			Node timestamp = signature.getElement().getElementsByTagName("Timestamp").item(0);
			System.out.println(timestamp.getLocalName());
			NodeList list = timestamp.getChildNodes();
			System.out.println("LIST LEN = " + list.getLength());
			java.util.Date expires = null;
			java.util.Date created = null;
			System.out.println(list.item(0).getTextContent());
			System.out.println(list.item(1).getTextContent());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			if (list.item(0).getNodeName().equals("Created")) {
				try {
					created = formatter.parse(list.item(0).getTextContent().trim());
				} catch (DOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			
			if (list.item(1).getNodeName().equals("Expires")) {
				try {
					expires = formatter.parse(list.item(1).getTextContent().trim());
				} catch (DOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			
			if (expires==null || created==null || list.getLength()!=2 || created.after(now) || expires.after(now)) {
				WrongTimestampException ex = new WrongTimestampException();
				ex.printStackTrace();
				return false;
			}
			
			try {
				DocumentUtil.printDocument(doc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String documentName = "";
			if (doc.getChildNodes().item(0).getNodeName().split(":").length<2) {
				documentName = doc.getChildNodes().item(0).getNodeName().split(":")[0];
			} else {
				documentName = doc.getChildNodes().item(0).getNodeName().split(":")[1];
			}
			
			System.out.println("!!!!!!!!!!!! DOCUMENT NAME: " + documentName);
			System.out.println(doc.getElementsByTagName("ns5:ID_poruke").getLength());
			String idPoruke = doc.getElementsByTagName("ns5:ID_poruke").item(0).getTextContent();
			System.out.println("!!!!!!!!!!!! ID PORUKE: " + idPoruke);
			
			if (Integer.parseInt(signature.getElement().getAttribute("Id"))<=getLastId(documentName,idPoruke)) {
				WrongIdSignatureException ex = new WrongIdSignatureException();
				ex.printStackTrace();
				return false;
			}
			
			//preuzima se key info
			KeyInfo keyInfo = signature.getKeyInfo();
			//ako postoji
			if(keyInfo != null) {
				//registruju se resolver-i za javni kljuc i sertifikat
				keyInfo.registerInternalKeyResolver(new RSAKeyValueResolver());
			    keyInfo.registerInternalKeyResolver(new X509CertificateResolver());
			    
			    //ako sadrzi sertifikat
			    if(keyInfo.containsX509Data() && keyInfo.itemX509Data(0).containsCertificate()) { 
			        Certificate cert = keyInfo.itemX509Data(0).itemCertificate(0).getX509Certificate();
			        //provera da li je sertifikat povucen
			      
			        if (CertificateReader.checkIfWithdrown(cert)) {
			        	WithdrawnCertificateException ex = new WithdrawnCertificateException();
			        	ex.printStackTrace();
			        	return false;
			        }
			        //ako postoji sertifikat, provera potpisa
			        if(cert != null) 
			        	return signature.checkSignatureValue((X509Certificate) cert);
			        else
			        	return false;
			    }
			    else
			    	return false;
			}
			else
				return false;
		
		} catch (XMLSignatureException e) {
			e.printStackTrace();
			return false;
		} catch (XMLSecurityException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static int getLastId(String documentName, String docId) {

        String xQuery = "//" + documentName + "[@id=\"" + docId + "\"]/text()";
        
        InputStream stream = null;
		try {
			stream = RESTUtil.retrieveResource(xQuery, "Banka/001/indeksiPoruka", "UTF-8", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
    	int result = -1;
		String line;
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		if (line != null) 
			result = Integer.parseInt(line);

		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getLastId("mt103", "1"));
	}
}
