package com.project.document.crypto.sig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import misc.DocumentUtil;
import misc.RESTUtil;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.keyresolver.implementations.RSAKeyValueResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509CertificateResolver;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.project.banka.WithdrawnCertificateException;
import com.project.banka.WrongIdSignatureException;
import com.project.banka.WrongTimestampException;

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
			
			java.util.Date now = new java.util.Date();
			
			//kreira se signature objekat od elementa
			XMLSignature signature = new XMLSignature(signatureEl, null);
			
			Node timestamp = signature.getElement().getElementsByTagName("Timestamp").item(0);
			NodeList list = timestamp.getChildNodes();
			java.util.Date expires = null;
			java.util.Date created = null;
			if (list.item(0).getNodeName().equals("Created")) {
				created = new java.util.Date(list.item(0).getNodeValue().trim());
			} else if (list.item(0).getNodeName().equals("Expires")) {
				expires = new java.util.Date(list.item(0).getNodeValue().trim());
			} 
			
			if (list.item(1).getNodeName().equals("Created")) {
				created = new java.util.Date(list.item(0).getNodeValue().trim());
			} else if (list.item(1).getNodeName().equals("Expires")) {
				expires = new java.util.Date(list.item(0).getNodeValue().trim());
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
			String documentName = doc.getElementsByTagName("body").item(0).getChildNodes().item(0).getNodeName();
			System.out.println("!!!!!!!!!!!! DOCUMENT NAME: " + documentName);
			String idPoruke = doc.getElementsByTagName("body").item(0).getChildNodes().item(0).getAttributes().getNamedItem("id").toString();
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
			stream = RESTUtil.retrieveResource(xQuery, "indeksiPoruka", "UTF-8", false);
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
