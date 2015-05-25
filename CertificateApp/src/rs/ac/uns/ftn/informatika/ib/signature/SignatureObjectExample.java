package rs.ac.uns.ftn.informatika.ib.signature;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;

import rs.ac.uns.ftn.informatika.ib.util.Base64;


public class SignatureObjectExample {
	
	private PublicKey  publicKey;
	private PrivateKey privateKey;
	private String data = "Ovo su podaci koji se potpisuju digitalnim potpisom!";
	private SignedObject signedObject;

	public void testIt() {
		System.out.println("Generisanje kljuceva");
		generateKeys();
		System.out.println("\nGenerisanje potpisa");
		sign();
		System.out.println("\nProvera potpisa");
		verify();
	}
	
	private void generateKeys() {
        try {
			System.out.println("Kreira generator kljuceva...");
        	
			//generator para kljuceva
			KeyPairGenerator   keyGen = KeyPairGenerator.getInstance("RSA"); 
			//inicijalizacija generatora, 1024 bitni kljuc
			keyGen.initialize(1024);
			
			System.out.println("Kreira par kljuceva...");
			
			//generise par kljuceva
			KeyPair    pair = keyGen.generateKeyPair();
			//preuzimamo javni i privatni kljuc
			publicKey = pair.getPublic();
			privateKey = pair.getPrivate();

        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private void sign() {
		try {
			System.out.println("Kreira generator za potpisivanje...");
			
			//kreiramo objekat za potpisivanje 
			Signature sig = Signature.getInstance("SHA1withRSA");    
			
			System.out.println("Vrsi potpisivanje...");
			//kreira se objekat koji sadrzi objekat koji se potpisuje i potpis
			signedObject = new SignedObject(data, privateKey, sig);
			
			System.out.println("Data: " + new String(data));
			System.out.println("Signed data: " + Base64.encodeToString(signedObject.getSignature()));
		
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void verify() {
		try {
			System.out.println("Kreira generator za proveru potpisa...");
			
			//kreiramo objekat za potpisivanje 
			Signature sig = Signature.getInstance("SHA1withRSA");    
			
			System.out.println("Vrsi proveru potpisa...");
			
			boolean res = signedObject.verify(publicKey, sig);
			
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
		SignatureObjectExample sig = new SignatureObjectExample();
		sig.testIt();
	}
}
