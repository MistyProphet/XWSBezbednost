package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import rs.ac.uns.ftn.informatika.ib.GUI.FileChooserDemo;
import rs.ac.uns.ftn.informatika.ib.security.IssuerData;
import rs.ac.uns.ftn.informatika.ib.security.SubjectData;

public class NewCertificateAction implements ActionListener{

	 String text2; String text3;
	String text4; String text5; String text6; String text7;
	String text8; String text9; String text10; String text11; File saveAs;
	private CertificateForm certificateForm;
	private X509Certificate cert;
	protected KeyPair keyPair;

	
	public KeyPair getKeyPair() {
		return keyPair;
	}
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public NewCertificateAction(CertificateForm certificateForm){
		this.certificateForm = certificateForm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(certificateForm.getCommonName().getText().equals("") ||
				certificateForm.getName2().getText().equals("") ||
				certificateForm.getSurname().getText().equals("") ||
				certificateForm.getValidity().getText().equals("") ||
				certificateForm.getFile()==null){
					return;
				}
				
		certificateForm.setVisible(false);
		setTexts(certificateForm);
		 keyPair = generateKeyPair();
		//datumi
	//	SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDate = new Date();
		Date endDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, Integer.parseInt(text2));  // number of days to add
		endDate = c.getTime();
		
		//podaci o vlasniku i izdavacu posto je self signed 
		//klasa X500NameBuilder pravi X500Name objekat koji nam treba
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, text3);
	    builder.addRDN(BCStyle.SURNAME, text5);
	    builder.addRDN(BCStyle.GIVENNAME, text4);
	    builder.addRDN(BCStyle.O, text7);
	    builder.addRDN(BCStyle.OU, text6);
	    builder.addRDN(BCStyle.C, text10);
	    builder.addRDN(BCStyle.E, text11);
	    builder.addRDN(BCStyle.ST, text9);
	    builder.addRDN(BCStyle.L, text8);
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, "123445");
		
		//Serijski broj sertifikata
		String sn="1";
		//kreiraju se podaci za issuer-a
		IssuerData issuerData = new IssuerData(keyPair.getPrivate(), builder.build());
		//kreiraju se podaci za vlasnika
		SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);
		
		//generise se sertifikat
		 cert = generateCertificate(issuerData, subjectData);
		System.out.println("ISSUER: " + cert.getIssuerX500Principal().getName());
		System.out.println("SUBJECT: " + cert.getSubjectX500Principal().getName());
		System.out.println("Sertifikat:");
		System.out.println("-------------------------------------------------------");
		System.out.println(cert);
		System.out.println("-------------------------------------------------------");
		//ako validacija nije uspesna desice se exception
		
		//ovde bi trebalo da prodje
		try {
			cert.verify(keyPair.getPublic());
		} catch (InvalidKeyException e) {
			JOptionPane.showMessageDialog(certificateForm, "Wrong password");
			System.exit(0);
			
		} catch (CertificateException e) {
			JOptionPane.showMessageDialog(certificateForm, "Wrong password");
			System.exit(0);
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(certificateForm, "Wrong password");
			System.exit(0);
		} catch (NoSuchProviderException e) {
			JOptionPane.showMessageDialog(certificateForm, "Wrong password");
			System.exit(0);
		} catch (SignatureException e) {
			JOptionPane.showMessageDialog(certificateForm, "Wrong password");
			System.exit(0);
		}
		System.out.println("VALIDACIJA USPESNA....");
		
		AliasForm alias = new AliasForm(this,saveAs,certificateForm);
		
	}
	public X509Certificate getCert() {
		return cert;
	}

	public void setCert(X509Certificate cert) {
		this.cert = cert;
	}

	private void setTexts(CertificateForm certificatForm) {
		this.text2 = certificatForm.getValidity().getText();
		this.text3 = certificatForm.getCommonName().getText();
		this.text4 = certificatForm.getName2().getText();
		this.text5 = certificatForm.getSurname().getText();
		this.text6 = certificatForm.getOrganisationUnit().getText();
		this.text7 = certificatForm.getOrganisationName().getText();
		this.text8 = certificatForm.getLocalityName().getText();
		this.text9 = certificatForm.getStateName().getText();
		this.text10 = certificatForm.getCountry().getText();
		this.text11 = certificatForm.getEmail().getText();
		this.saveAs = certificatForm.getFile();
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
			e.printStackTrace();
			return null;
		}
	}

	
}
