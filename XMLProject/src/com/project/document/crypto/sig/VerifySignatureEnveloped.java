package com.project.document.crypto.sig;

import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

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

//Vrsi proveru potpisa
public class VerifySignatureEnveloped {
    static {
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
	
	public static boolean verifySignature(Document doc) {
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
				//greska kod timestampa
			}
			
			if (Integer.parseInt(signature.getElement().getAttribute("Id"))<=getLastId()) {
				//greska kod id-ja
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
	
	private static int getLastId() {
		return 0;

	}
}
