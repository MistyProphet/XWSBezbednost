package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import misc.RESTUtil;

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
import org.w3c.dom.NodeList;

import com.project.bankaws.PortHelper;
import com.project.util.DocumentUtil;

//Potpisuje dokument, koristi se enveloped tip
public class SignEnveloped {
	
	public static final String KEY_STORE_FILE =  "C:/Users/Geek/Documents/XWSBezbednost/XMLProject/cert/firma1.jks";
	public static final String KEY_STORE_PASSWORD =  "firma1";
	
    static {
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
	
	public static Document signDocument(Document doc, boolean external) {
		//ucitava privatni kljuc
		PrivateKey pk = readPrivateKey();
		//ucitava sertifikat
		Certificate cert = readCertificate();
		//potpisuje
		return signDocument(doc, pk, cert, external);
	}
	
	public static Document signDocumentSimple(Document doc) {
		//ucitava privatni kljuc
		PrivateKey pk = readPrivateKey();
		//ucitava sertifikat
		Certificate cert = readCertificate();
		//potpisuje
		return signDocumentSimple(doc, pk, cert);
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
			//password?
			ks.load(in, KEY_STORE_PASSWORD.toCharArray());
			
			if(ks.isKeyEntry(KEY_STORE_PASSWORD)) {
				Certificate cert = ks.getCertificate(KEY_STORE_PASSWORD);
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
			ks.load(in, KEY_STORE_PASSWORD.toCharArray());
			
			if(ks.isKeyEntry(KEY_STORE_PASSWORD)) {
				PrivateKey pk = (PrivateKey) ks.getKey(KEY_STORE_PASSWORD, KEY_STORE_PASSWORD.toCharArray());
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
	
	private static Document signDocument(Document doc, PrivateKey privateKey, Certificate cert, boolean externalMessage) {
        
        try {
			Element rootEl = doc.getDocumentElement();
			//kreira se signature objekat
			XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
			//PROVERITI
			String docType = "";
			if (rootEl.getNodeName().split(":").length<2) {
				docType = rootEl.getNodeName().split(":")[0];
			} else {
				docType = rootEl.getNodeName().split(":")[1];
			}
			
			if (externalMessage) {
				sig.setId(getNextId(docType, rootEl.getFirstChild().getFirstChild().getTextContent())+"");
			}

			//Dodavanje timestampa
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:dd.SSS'Z'");
		      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));;
		        
		        //This is for TimeStamp element value
		        java.util.Date created = new java.util.Date();
		        java.util.Date expires = new java.util.Date(created.getTime() + (5l * 60l * 1000l));
		        //This is for TimeStamp value ends
		        
		        Element timestampElem = doc.createElement("Timestamp");
		        Element createdElem = doc.createElement("Created");
		        createdElem.setTextContent(formatter.format(created));
		        
		        Element expiresElem = doc.createElement("Expires");
		        expiresElem.setTextContent(formatter.format(expires));
		        
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
			
			incrementNextId(docType, rootEl.getFirstChild().getFirstChild().getTextContent()+"");
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
	
	private static Document signDocumentSimple(Document doc, PrivateKey privateKey, Certificate cert) {
        
        try {
			Element rootEl = doc.getDocumentElement();
			//kreira se signature objekat
			XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
			
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
	
	private static int getNextId(String documentName, String docId) {

        String xQuery = "//" + documentName + "[@id=\"" + docId + "\"]/text()";
        
        InputStream stream = null;
		try {
			stream = RESTUtil.retrieveResource(xQuery, "Firma/00" + PortHelper.current_bank.getId() + "/indeksiPoruka", "UTF-8", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
    	int result = -1;
		String line;
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		if (line != null) 
			result = Integer.parseInt(line);

		return result+1;
	}
	
	private static void incrementNextId(String documentName, String idPoruke) {
		InputStream is = null;
		try {
			is = RESTUtil.retrieveResource("/*", "Firma/00"+PortHelper.current_bank.getId()+"/indeksiPoruka", "UTF-8", true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	      dbf.setValidating(false);
	      dbf.setIgnoringComments(false);
	      dbf.setIgnoringElementContentWhitespace(true);
	      dbf.setNamespaceAware(true);


	      DocumentBuilder db = null;
	      Document doc = null;
	      try {
			db = dbf.newDocumentBuilder();
		     doc = db.parse(is);
		     DocumentUtil.printDocument(doc);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    NodeList listaCvorova = doc.getElementsByTagName(documentName);
	    boolean postoji = false;
	    for (int i=0; i<listaCvorova.getLength(); i++) {
	    	if (listaCvorova.item(i).getAttributes().getNamedItem("id").getTextContent().trim().equals(idPoruke.trim())) {
	    		int rbrPoruke = Integer.parseInt(listaCvorova.item(i).getTextContent().trim())+1;
	    		listaCvorova.item(i).setTextContent(rbrPoruke+"");
	    		postoji = true;
	    		break;
	    	}
	    }
	    
	    //dodaje se novi element
	    if (!postoji) {
	    	Element newEl = doc.createElement(documentName);
	    	newEl.setAttribute("id", idPoruke);
	    	newEl.setTextContent(0+"");
	    	doc.getFirstChild().appendChild(newEl);
	    }
	    
	    try {
			DocumentUtil.printDocument(doc);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Source xmlSource = new DOMSource(doc);
		Result outputTarget = new StreamResult(outputStream);
		try {
		TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
		InputStream is2 = new ByteArrayInputStream(outputStream.toByteArray());
		RESTUtil.updateResource("Firma/00"+PortHelper.current_bank.getId(), "indeksiPoruka", is2);     
	      
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(getNextId("mt102", "2"));
		incrementNextId("mt102", "2");
	}
}
