package com.project.document.crypto.enc;

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
import java.security.cert.CertificateException;
import java.util.Properties;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//Dekriptuje tajni kljuc privatnim kljucem
//Tajnim kljucem dekriptuje podatke
public class Decrypt {
	private static String KEY_STORE_FILE = "";
	
    static {
    	Properties properties = new Properties();
	    try {
	      properties.load(new FileInputStream("deploy.properties"));
	      KEY_STORE_FILE = properties.getProperty("keystore.file");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
    
	public static Document decryptDocument(Document doc) {
		//ucitava se privatni kljuc
		PrivateKey pk = readPrivateKey();
		//dekriptuje se dokument
		return decrypt(doc, pk);
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
	
	/**
	 * Kriptuje sadrzaj prvog elementa odsek
	 */
	private static Document decrypt(Document doc, PrivateKey privateKey) {
		
		try {
			//cipher za dekritpovanje XML-a
			XMLCipher xmlCipher = XMLCipher.getInstance();
			//inicijalizacija za dekriptovanje
			xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
			//postavlja se kljuc za dekriptovanje tajnog kljuca
			xmlCipher.setKEK(privateKey);
			
			//trazi se prvi EncryptedData element
			NodeList encDataList = doc.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
			Element encData = (Element) encDataList.item(0);
			
			//dekriptuje se
			//pri cemu se prvo dekriptuje tajni kljuc, pa onda njime podaci
			xmlCipher.doFinal(doc, encData); 
			
			return doc;
		} catch (XMLEncryptionException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
