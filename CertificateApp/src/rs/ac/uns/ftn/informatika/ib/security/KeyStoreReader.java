package rs.ac.uns.ftn.informatika.ib.security;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.swing.JOptionPane;

/**
 * 
 * Cita is keystore fajla
 */
public class KeyStoreReader {

	//private static final String KEY_STORE_FILE = "./data/test.jks";
	//private char[] keyPass  = "test10".toCharArray();
	KeyStore ks;
	
	//public void testIt() {
	//	readKeyStore("test");
//	}
	
	public void readKeyStore(String certAlias,String keyPass,String password,File file){
		try {
			//kreiramo instancu KeyStore
			ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			ks.load(in, password.toCharArray());
			//citamo par sertifikat privatni kljuc
			System.out.println("Cita se Sertifikat i privatni kljuc CA");
			
			if(ks.isKeyEntry(certAlias)) {
				System.out.println("Sertifikat:");
				Certificate cert = ks.getCertificate(certAlias);
				System.out.println(cert);
				PrivateKey privKey = (PrivateKey)ks.getKey(certAlias, keyPass.toCharArray());
				System.out.println("Privatni kljuc:");
				System.out.println(privKey);
			}
			else
				System.out.println("Nema para kljuceva za Mariju");
			

		} catch (KeyStoreException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (NoSuchProviderException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (CertificateException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (UnrecoverableKeyException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		}

	}
	public Certificate getCertificate(String certAlias,String password, File file){
		try {
			//kreiramo instancu KeyStore
			ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			ks.load(in, password.toCharArray());
			//citamo par sertifikat privatni kljuc
			System.out.println("Cita se Sertifikat i privatni kljuc CA");
			
			if(ks.isKeyEntry(certAlias)) {
				System.out.println("Sertifikat:");
				Certificate cert = ks.getCertificate(certAlias);
				System.out.println(cert);
				return cert;
			}
			else
				System.out.println("Nema para kljuceva za Mariju");
			

		} catch (KeyStoreException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (NoSuchProviderException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (CertificateException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		}
		return null;

	}
	public PrivateKey getPrivateKey(String certAlias,String keyPass){
		try {
			//kreiramo instancu KeyStore

			if(ks.isKeyEntry(certAlias)) {
				
				PrivateKey privKey = (PrivateKey)ks.getKey(certAlias, keyPass.toCharArray());
				System.out.println("Privatni kljuc:");
				System.out.println(privKey);
				return privKey;
			}
			else
				System.out.println("Nema para kljuceva za Mariju");
			

		} catch (KeyStoreException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		} catch (UnrecoverableKeyException e) {
			JOptionPane.showMessageDialog(null, "Wrong password");
			System.exit(0);
		}
		return null;

	}
	
	public static void main(String[] args) {
		KeyStoreReader test = new KeyStoreReader();
		//test.testIt();
	}
}
