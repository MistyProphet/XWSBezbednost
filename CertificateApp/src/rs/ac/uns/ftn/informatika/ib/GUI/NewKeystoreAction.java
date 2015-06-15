package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Savepoint;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import rs.ac.uns.ftn.informatika.ib.security.KeyStoreReader;
import rs.ac.uns.ftn.informatika.ib.security.KeyStoreWriter;

public class NewKeystoreAction implements Action {

	X509Certificate certificate=  null;
	String password = null;
	String alias = null;
	File saveAs;
	String ksPassword;
	SetPasswordForm setPasswordForm;
	KeyPair keyPair ;
	public NewKeystoreAction(SetPasswordForm setPasswordForm, X509Certificate newCertificate,  String alias, File saveAs, String ksPassword, NewCertificateAction newCertificateAction) {
this.keyPair = newCertificateAction.getKeyPair();
	this.certificate = newCertificate;
	this.setPasswordForm = setPasswordForm;
	this.alias = alias;
	this.saveAs = saveAs;
this.ksPassword = ksPassword;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		this.password = setPasswordForm.getPassword().getText();	
		if(!setPasswordForm.getPassword().getText().equals(setPasswordForm.getPassword2().getText()))
			return;
		KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
		keyStoreWriter.loadKeyStore(null,null);
		keyStoreWriter.write(alias, keyPair.getPrivate(), password.toCharArray(), certificate);
		keyStoreWriter.saveKeyStore(saveAs.getAbsolutePath(),ksPassword.toCharArray());
		System.out.println("Upisan u keyStore");
		System.exit(0);
			}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void putValue(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
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
