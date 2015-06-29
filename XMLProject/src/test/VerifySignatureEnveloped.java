package test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

import com.project.bankaws.PortHelper;
import com.project.exceptions.WithdrawnCertificateException;
import com.project.exceptions.WrongIdSignatureException;
import com.project.exceptions.WrongTimestampException;

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

			Node timestamp = doc.getElementsByTagName("Timestamp").item(0);
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
			} else {
				//uklanjanje timestampa
				signatureEl.removeChild(timestamp);
			}

			
			//kreira se signature objekat od elementa
			XMLSignature signature = new XMLSignature(signatureEl, null);
			
			String documentName = "";
			if (doc.getChildNodes().item(0).getNodeName().split(":").length<2) {
				documentName = doc.getChildNodes().item(0).getNodeName().split(":")[0];
			} else {
				documentName = doc.getChildNodes().item(0).getNodeName().split(":")[1];
			}
			
			
			String idPoruke = "";
			if(doc.getElementsByTagName("ns5:ID_poruke").item(0) != null){
				idPoruke = doc.getElementsByTagName("ns5:ID_poruke").item(0).getTextContent();
			}else{
				if(doc.getElementsByTagName("ns4:ID_poruke").item(0) != null){
					idPoruke = doc.getElementsByTagName("ns4:ID_poruke").item(0).getTextContent();
				}else{
					if(doc.getElementsByTagName("ns3:ID_poruke").item(0) != null){
						idPoruke = doc.getElementsByTagName("ns3:ID_poruke").item(0).getTextContent();
					}else{
						if(doc.getElementsByTagName("ns2:ID_poruke").item(0) != null){
							idPoruke = doc.getElementsByTagName("ns2:ID_poruke").item(0).getTextContent();
						}else{
							if(doc.getElementsByTagName("ns1:ID_poruke").item(0) != null){
								idPoruke = doc.getElementsByTagName("ns1:ID_poruke").item(0).getTextContent();
							}else{
								idPoruke="0";
							}
						}
					}
				}
			}
			
			if (Integer.parseInt(signature.getElement().getAttribute("Id"))<=getLastId(documentName,idPoruke)) {
				WrongIdSignatureException ex = new WrongIdSignatureException();
				ex.printStackTrace();
				System.out.println("ID ===== " + Integer.parseInt(signature.getElement().getAttribute("Id")));
				System.out.println(getLastId(documentName,idPoruke));
				return false;
			} else {
				setLastId(documentName, signature.getElement().getAttribute("Id"), idPoruke);
			}
			
			try {
				DocumentUtil.printDocument(doc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	public static boolean verifySignatureSimple(Document doc) throws WithdrawnCertificateException {
		try {
			//Pronalazi se prvi Signature element 
			NodeList signatures = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
			Element signatureEl = (Element) signatures.item(0);
			
			//kreira se signature objekat od elementa
			XMLSignature signature = new XMLSignature(signatureEl, null);			
			
			try {
				DocumentUtil.printDocument(doc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			stream = RESTUtil.retrieveResource(xQuery, "Firma/00"+PortHelper.current_bank.getId()+"/indeksiPoruka", "UTF-8", false);
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
	
	private static boolean setLastId(String documentName, String docId, String idPoruke) {

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
	    		listaCvorova.item(i).setTextContent(docId.trim());
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
	      
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Source xmlSource = new DOMSource(doc);
		Result outputTarget = new StreamResult(outputStream);
		try {
		TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
		InputStream is2 = new ByteArrayInputStream(outputStream.toByteArray());
		RESTUtil.updateResource("Firma/00"+PortHelper.current_bank.getId(), "indeksiPoruka", is2);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(getLastId("mt103", "1"));
		setLastId("mt103","4", "2");
		
	}
}
