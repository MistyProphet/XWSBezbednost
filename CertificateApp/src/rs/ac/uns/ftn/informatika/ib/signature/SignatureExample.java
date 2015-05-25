package rs.ac.uns.ftn.informatika.ib.signature;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import rs.ac.uns.ftn.informatika.ib.util.Base64;


/**
 * Generise i proverava digitalni potpis 
 * 
 */
public class SignatureExample {
	
	private PublicKey  publicKey;
	private PrivateKey privateKey;
	private String data = "Ovo su podaci koji se potpisuju digitalnim potpisom!";
	private byte[] signedData;
	
	
	public void testIt() {
		System.out.println("Generisanje kljuceva");
		generateKeys();
		System.out.println("\nGenerisanje potpisa");
		
		byte[] dat = data.getBytes();
		sign(dat);
		System.out.println("\nProvera potpisa (TRUE)");
		verify(dat, signedData);
		System.out.println("\nProvera potpisa (FALSE)");
		dat[0] = (byte)0xFA;
		verify(dat, signedData);
	}
	
	private void generateKeys() {
        try {
			System.out.println("Kreira generator kljuceva...");
        	
			//generator para kljuceva
			//KeyPairGenerator   keyGen = KeyPairGenerator.getInstance("DSA");
			KeyPairGenerator   keyGen = KeyPairGenerator.getInstance("RSA");
			//generator pseudo slucajnih brojeva
			SecureRandom       random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			//inicijalizacija generatora, 1024 bitni kljuc
			keyGen.initialize(1024, random);
			
			System.out.println("Kreira par kljuceva...");
			
			//generise par kljuceva
			KeyPair    pair = keyGen.generateKeyPair();
			//preuzimamo javni i privatni kljuc
			publicKey = pair.getPublic();
			privateKey = pair.getPrivate();

        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	
	private void sign(byte[] data) {
		try {
			System.out.println("Kreira generator za potpisivanje...");
			
			//kreiramo objekat za potpisivanje 
			//Signature sig = Signature.getInstance("SHA1withDSA");
			Signature sig = Signature.getInstance("SHA1withRSA");    
			//inicijalizacija privatnim kljucem 
			sig.initSign(privateKey);
			//postavljamo podatke koje potpisujemo
			sig.update(data);
			
			System.out.println("Vrsi potpisivanje...");
			
			//vrsimo potpisivanje
			signedData = sig.sign();
			
			System.out.println("Data: " + new String(data));
			System.out.println("Signed data: " + Base64.encodeToString(signedData));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
	
	private void verify(byte[] data, byte[] signature) {
		try {
			System.out.println("Kreira generator za proveru potpisa...");
			
			//kreiramo objekat za potpisivanje 
			//Signature sig = Signature.getInstance("SHA1withDSA");
			Signature sig = Signature.getInstance("SHA1withRSA");    
			//inicijalizacija privatnim kljucem 
			sig.initVerify(publicKey);
			//postavljamo podatke koje potpisujemo
			sig.update(data);
			
			System.out.println("Vrsi proveru potpisa...");
			
			//vrsimo proveru potpisa
			boolean res = sig.verify(signature);
			
			System.out.println("Rezultat provere je " + res);
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		SignatureExample sig = new SignatureExample();
		sig.testIt();
	}
}
