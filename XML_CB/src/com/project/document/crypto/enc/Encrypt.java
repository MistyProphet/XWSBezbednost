package com.project.document.crypto.enc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ResourceBundle;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;

import misc.DocumentUtil;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.KeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//Generise tajni kljuc
//Kriptije sadrzaj dokumenta tajnim kljucem
//Kriptuje tajni kljuc javnim kljucem
//Kriptovani tajni kljuc se stavlja kao KeyInfo kriptovanog elementa
public class Encrypt {
	private static String KEY_STORE_FILE = "";
	
    static {
    	ResourceBundle b = ResourceBundle.getBundle ("resources.deploy");
        
    	KEY_STORE_FILE = (String) b.getObject("keystore.file");
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
	
	public static Document encryptDocument(Document doc) {
		//generise tajni kljuc
		SecretKey secretKey = generateDataEncryptionKey();
		//ucitava sertifikat za kriptovanje tajnog kljuca
		Certificate cert = readCertificate();
		//kriptuje se dokument
		return encrypt(doc, secretKey, cert);
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
	 * Generise tajni kljuc
	 */
	private static SecretKey generateDataEncryptionKey() {

        try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			return keyGenerator.generateKey();
		
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	/**
	 * Kriptuje sadrzaj prvog elementa odsek
	 */
	private static Document encrypt(Document doc, SecretKey key, Certificate certificate) {
		
		try {
			//cipher za kriptovanje tajnog kljuca,
			//Koristi se Javni RSA kljuc za kriptovanje
			XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
		      //inicijalizacija za kriptovanje tajnog kljuca javnim RSA kljucem
		    keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
		    EncryptedKey encryptedKey = keyCipher.encryptKey(doc, key);
			
		    //cipher za kriptovanje XML-a
		    XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128);
		    //inicijalizacija za kriptovanje
		    xmlCipher.init(XMLCipher.ENCRYPT_MODE, key);
		    
		    //u EncryptedData elementa koji se kriptuje kao KeyInfo stavljamo kriptovan tajni kljuc
		    EncryptedData encryptedData = xmlCipher.getEncryptedData();
	        //kreira se KeyInfo
		    KeyInfo keyInfo = new KeyInfo(doc);
		    keyInfo.addKeyName("Kriptovani tajni kljuc");
	        //postavljamo kriptovani kljuc
		    keyInfo.add(encryptedKey);
		    //postavljamo KeyInfo za element koji se kriptuje
	        encryptedData.setKeyInfo(keyInfo);
			
			//trazi se element ciji sadrzaj se kriptuje
			Element root = (Element) doc.getChildNodes().item(0);
			
			xmlCipher.doFinal(doc, root, true); //kriptuje sa sadrzaj
			
			return doc;
			
		} catch (XMLEncryptionException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		javax.xml.parsers.DocumentBuilderFactory dbf=javax.xml.parsers.DocumentBuilderFactory.newInstance();
		File f=new File("src/resources/banke.xml");
	    DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
		    Document doc = db.parse(new java.io.FileInputStream(f));
		    Document encryptedDoc = encryptDocument(doc);
		    DocumentUtil.printDocument(encryptedDoc);
		    
		    Document decryptedDoc = Decrypt.decryptDocument(encryptedDoc);
		    DocumentUtil.printDocument(decryptedDoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
