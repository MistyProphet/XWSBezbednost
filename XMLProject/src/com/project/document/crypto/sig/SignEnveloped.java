package com.project.document.crypto.sig;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//Potpisuje dokument, koristi se enveloped tip
public class SignEnveloped {
	private static final String KEY_STORE_FILE = "./data/primer.jks";
	
    static {
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
	
	public static Document signDocument(Document doc) {
		//ucitava privatni kljuc
		PrivateKey pk = readPrivateKey();
		//ucitava sertifikat
		Certificate cert = readCertificate();
		//potpisuje
		return signDocument(doc, pk, cert);
	}
	
	/**
	 * Ucitava sertifikat is KS fajla
	 * alias primer
	 */
	private static Certificate readCertificate() {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			ks.load(in, "primer".toCharArray());
			
			if(ks.isKeyEntry("primer")) {
				Certificate cert = ks.getCertificate("primer");
				return cert;
				
			}
			else
				return null;
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * Ucitava privatni kljuc is KS fajla
	 * alias primer
	 */
	private static PrivateKey readPrivateKey() {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			ks.load(in, "primer".toCharArray());
			
			if(ks.isKeyEntry("primer")) {
				PrivateKey pk = (PrivateKey) ks.getKey("primer", "primer".toCharArray());
				return pk;
			}
			else
				return null;
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	private static Document signDocument(Document doc, PrivateKey privateKey, Certificate cert) {
        
        try {
			Element rootEl = doc.getDocumentElement();
			//kreira se signature objekat
			XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
			sig.setId(getNextId()+"");
			
			//Dodavanje timestampa
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:dd.SSS'Z'");
		      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		        //This is for UsernameToken element
		        int id = getNextId();
		        
		        //This is for TimeStamp element value
		        java.util.Date created = new java.util.Date();
		        java.util.Date expires = new java.util.Date(created.getTime() + (5l * 60l * 1000l));
		        //This is for TimeStamp value ends
		        
		        Element timestampElem = doc.createElement("Timestamp");
		        Element createdElem = doc.createElement("Created");
		        createdElem.setTextContent(formatter.format(created));
		        
		        Element expiresElem = doc.createElement("Expires");
		        createdElem.setTextContent(formatter.format(expires));
		        
		        timestampElem.appendChild(createdElem);
		        timestampElem.appendChild(expiresElem);
		        
		        sig.getElement().appendChild(timestampElem);

                
			//kreiraju se transformacije nad dokumentom
			Transforms transforms = new Transforms(doc);
			//iz potpisa uklanja Signature element
			//Ovo je potrebno za enveloped tip po specifikaciji
			transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
			//normalizacija
			transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);

			//potpisuje se citav dokument (URI "")
			sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
   
			//U KeyInfo se postavalja Javni kljuc samostalno i citav sertifikat
			sig.addKeyInfo(cert.getPublicKey());
			sig.addKeyInfo((X509Certificate) cert);

			    
			//poptis je child root elementa
			rootEl.appendChild(sig.getElement());
			    
			//potpisivanje
			sig.sign(privateKey);
			
			return doc;
			
		} catch (TransformationException e) {
			e.printStackTrace();
			return null;
		} catch (XMLSignatureException e) {
			e.printStackTrace();
			return null;
		} catch (DOMException e) {
			e.printStackTrace();
			return null;
		} catch (XMLSecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static int getNextId() {
		return 0;

	}
}
