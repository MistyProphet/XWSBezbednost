package rs.ac.uns.ftn.informatika.ib.security;

import java.io.File;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateGenerator {
	
	//registracija providera
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public X509Certificate generateCertificate(IssuerData issuerData, SubjectData subjectData) {
		 try {
			 
			 //posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc
			 //pravi se builder za objekat koji ce sadrzati privatni kljuc i koji 
			 //ce se koristitit za potpisivanje sertifikata
			 //parametar je koji algoritam se koristi za potpisivanje sertifiakta
			 JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			 //koji provider se koristi
			 builder = builder.setProvider("BC");
			 
			 //objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			 ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
			 
			 //postavljaju se podaci za generisanje sertifiakta
			 X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					 															new BigInteger(subjectData.getSerialNumber()),
					 															subjectData.getStartDate(),
					 															subjectData.getEndDate(),
					 															subjectData.getX500name(),
					 															subjectData.getPublicKey());
			 //generise se sertifikat
			 X509CertificateHolder certHolder = certGen.build(contentSigner);
			 
			 //certGen generise sertifikat kao objekat klase X509CertificateHolder
			 //sad je potrebno certHolder konvertovati u sertifikat
			 //za to se koristi certConverter
			 JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			 certConverter = certConverter.setProvider("BC");
			 
			 //konvertuje objekat u sertifikat i vraca ga
			 return certConverter.getCertificate(certHolder);
			 
		 } catch (CertificateEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (OperatorCreationException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public KeyPair generateKeyPair() {
		try {
			//generator para kljuceva
			KeyPairGenerator   keyGen = KeyPairGenerator.getInstance("RSA");
			//inicijalizacija generatora, 1024 bitni kljuc
			keyGen.initialize(1024);
			
			//generise par kljuceva
			KeyPair pair = keyGen.generateKeyPair();
			
			return pair;
			
        } catch (NoSuchAlgorithmException e) {
        	JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
			return null;
		}
	}
	
	public void testIt() {
		//kreira se self signed sertifikat
		try {
			//par kljuceva
			KeyPair keyPair = generateKeyPair();
			
			//datumi
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2007-12-31");
			Date endDate = iso8601Formater.parse("2017-12-31");
			
			//podaci o vlasniku i izdavacu posto je self signed 
			//klasa X500NameBuilder pravi X500Name objekat koji nam treba
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, "Goran Sladic");
		    builder.addRDN(BCStyle.SURNAME, "Sladic");
		    builder.addRDN(BCStyle.GIVENNAME, "Goran");
		    builder.addRDN(BCStyle.O, "UNS-FTN");
		    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, "123445");
			
			
			//Serijski broj sertifikata
			String sn="1";
			//kreiraju se podaci za issuer-a
			IssuerData issuerData = new IssuerData(keyPair.getPrivate(), builder.build());
			//kreiraju se podaci za vlasnika
			SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);
			
			//generise se sertifikat
			X509Certificate cert = generateCertificate(issuerData, subjectData);
			
			System.out.println("ISSUER: " + cert.getIssuerX500Principal().getName());
			System.out.println("SUBJECT: " + cert.getSubjectX500Principal().getName());
			System.out.println("Sertifikat:");
			System.out.println("-------------------------------------------------------");
			System.out.println(cert);
			System.out.println("-------------------------------------------------------");
			//ako validacija nije uspesna desice se exception
			
			//ovde bi trebalo da prodje
			cert.verify(keyPair.getPublic());
			System.out.println("VALIDACIJA USPESNA....");
			
			//ovde bi trebalo da se desi exception, jer validaciju vrsimo drugim kljucem
			//KeyPair anotherPair = generateKeyPair();
			//cert.verify(anotherPair.getPublic());
			
			KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
			keyStoreWriter.loadKeyStore(null,null);
			keyStoreWriter.write("test", keyPair.getPrivate(), "test10".toCharArray(), cert);
			keyStoreWriter.saveKeyStore("./data/test.jks", "test10".toCharArray());
			
			System.out.println("CA upisan u keyStore");
			
			KeyStoreReader keyStoreReader = new KeyStoreReader();
			X509Certificate parentCert = (X509Certificate) keyStoreReader.getCertificate("test","test10",new File("./data/test.jks"));
			PrivateKey parentPrivateKey = keyStoreReader.getPrivateKey("test","test10");
			
			KeyPair keyPair2 = generateKeyPair();
			
			//datumi
			 iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			 startDate = iso8601Formater.parse("2007-12-31");
			 endDate = iso8601Formater.parse("2017-12-31");
			
			//podaci o vlasniku i izdavacu posto je self signed 
			//klasa X500NameBuilder pravi X500Name objekat koji nam treba
			 builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, "Ljiljana");
		    builder.addRDN(BCStyle.SURNAME, "Miljesic");
		    builder.addRDN(BCStyle.GIVENNAME, "Ljiljana");
		    builder.addRDN(BCStyle.O, "UNS-FTN");
		    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, "sdsddsd@uns.ac.rs");
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, "4444");
			KeyStoreReader keyStoreReader2 = new KeyStoreReader();
			
			
			X509Certificate parentCert2 = (X509Certificate) keyStoreReader2.getCertificate("test","test10",new File("./data/test.jks"));
			PrivateKey parentPrivateKey2 = keyStoreReader2.getPrivateKey("test","test10");
			
			//Serijski broj sertifikata
			 sn="2";
			//kreiraju se podaci za issuer-a
			 issuerData = new IssuerData(parentPrivateKey2, new JcaX509CertificateHolder(parentCert2).getSubject());
			//kreiraju se podaci za vlasnika
			 subjectData = new SubjectData(keyPair2.getPublic(), builder.build(), sn, startDate, endDate);
			
			//generise se sertifikat
			 cert = generateCertificate(issuerData, subjectData);
			
			System.out.println("ISSUER: " + cert.getIssuerX500Principal().getName());
			System.out.println("SUBJECT: " + cert.getSubjectX500Principal().getName());
			System.out.println("Sertifikat:");
			System.out.println("-------------------------------------------------------");
			System.out.println(cert);
			System.out.println("-------------------------------------------------------");
			//ako validacija nije uspesna desice se exception
			
			//
			//ovde bi trebalo da prodje
			cert.verify(parentCert2.getPublicKey());
			System.out.println("VALIDACIJA USPESNA....");
			
			//ovde bi trebalo da se desi exception, jer validaciju vrsimo drugim kljucem
			//KeyPair anotherPair = generateKeyPair();
			//cert.verify(anotherPair.getPublic());
			
			keyStoreWriter = new KeyStoreWriter();
			keyStoreWriter.loadKeyStore(null, null);
			keyStoreWriter.write("test", keyPair2.getPrivate(), "test10".toCharArray(), cert);
			keyStoreWriter.saveKeyStore("./data/test2.jks", "test10".toCharArray());
			
			System.out.println("drugi korisnik upisan u keyStore 2");
			
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CertificateGenerator gen = new CertificateGenerator();
		gen.testIt();
	}

}
