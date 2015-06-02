package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

import rs.ac.uns.ftn.informatika.ib.security.IssuerData;
import rs.ac.uns.ftn.informatika.ib.security.KeyStoreReader;
import rs.ac.uns.ftn.informatika.ib.security.SubjectData;

import java.security.Key;
public class SignedCertificateAction extends NewCertificateAction {

	 String text2; String text3;
		String text4; String text5; String text6; String text7;
		String text8; String text9; String text10; String text11; File saveAs;
		private CertificateForm certificateForm;
		private X509Certificate cert;
		X509Certificate parentCertificate;
		private SelectCAForm frame;
		private JFrame frame2;
		private JTextField pass;
		File file;
	
	public SignedCertificateAction(CertificateForm certificateForm,
			SelectCAForm frame) {
		super(certificateForm);
		this.certificateForm=certificateForm;
		this.frame = frame;
		try {
			this.parentCertificate = (X509Certificate)frame.getKs().getCertificate((String)frame.getModel().getValueAt(frame.getTable().getSelectedRow(),1));
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static {
		Security.addProvider(new BouncyCastleProvider());
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
		
		file = certificateForm.getFile();
		 frame2 = new JFrame();
		 frame2.setSize(300, 100);
		 frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame2.setLocationRelativeTo(null);
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBackground(Color.WHITE);
			FlowLayout flow = new FlowLayout();
			frame2.add(panel);
			panel.setLayout(flow);
			JLabel lbl = new JLabel("Enter CA Password");
			pass = new JTextField();
			pass.setPreferredSize(new Dimension(150,30));
			JButton ok = new JButton();
			ok.setText("OK");
			
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					frame2.setVisible(false);
					action();
					
						
				}
			});
			panel.add(lbl);
			panel.add(pass);
			panel.add(ok);
			frame2.setVisible(true);
		
	}
	public X509Certificate getCert() {
		return cert;
	}

	public void action(){
		certificateForm.setVisible(false);
		setTexts(certificateForm);
		
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
	    builder.addRDN(BCStyle.UID, "4444");
		
		//Serijski broj sertifikata
		String sn="12";
		//kreiraju se podaci za issuer-a
		
		KeyStoreReader keyStoreReader = new KeyStoreReader();
		X509Certificate parentCert = (X509Certificate) keyStoreReader.getCertificate((String)frame.getModel().getValueAt(frame.getTable().getSelectedRow(),0),certificateForm.getFrame().pass,certificateForm.getFrame().getFile());
		PrivateKey parentPrivateKey = keyStoreReader.getPrivateKey((String)frame.getModel().getValueAt(frame.getTable().getSelectedRow(),0),pass.getText());
		
	 keyPair = generateKeyPair();
		 IssuerData issuerData;
		try {
			issuerData = new IssuerData(parentPrivateKey, new JcaX509CertificateHolder(parentCert).getSubject());
		
			//kreiraju se podaci za vlasnika
		SubjectData	 subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);
			
		//generise se sertifikat
		 cert = generateCertificate(issuerData, subjectData);
		} catch (CertificateEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 System.out.println("ISSUER: " + cert.getIssuerX500Principal().getName());
		System.out.println("SUBJECT: " + cert.getSubjectX500Principal().getName());
		System.out.println("Sertifikat:");
		System.out.println("-------------------------------------------------------");
		System.out.println(cert);
		System.out.println("-------------------------------------------------------");
		//ako validacija nije uspesna desice se exception
		
		//ovde bi trebalo da prodje
		try {
			cert.verify(parentCert.getPublicKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("VALIDACIJA USPESNA....");
		
		AliasForm alias = new AliasForm(this,saveAs,certificateForm);
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
