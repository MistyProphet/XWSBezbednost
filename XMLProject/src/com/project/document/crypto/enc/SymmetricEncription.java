package com.project.document.crypto.enc;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.project.util.Base64;


//Primer simetricnog siforvanja
//Moze da se sifruje podatak prozivoljne duzine
public class SymmetricEncription {
	
	private SecretKey secretKey;
	private String data = "Ovo su podaci koji se kriptuju simetricnim DES algoritmom, duzina podataka nije bitna, tj. DES moze da se koristi za proizvoljnu duzinu podataka";


	public SymmetricEncription() {
	}
	
	public void testIt() {
		System.out.println("Generisanje kljuca:");
		generateKey();
		System.out.println("Radi kriptovanje/dekriptovanje:");
		transfer();
		
	}
	
	private void generateKey() {
        try {
			//generator para kljuceva za DES algoritam
			KeyGenerator   keyGen = KeyGenerator.getInstance("AES"); 
			//generise kljuc za DES 
	        keyGen.init(128);
	        secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private void transfer() {
			
		try {
			System.out.println("Kriptuje se: " + data);	
			//klasa za sifrovanje
			Cipher rsaCipherEnc = Cipher.getInstance("AES");
			//inicijalizacija za sifrovanje, 
			rsaCipherEnc.init(Cipher.ENCRYPT_MODE, secretKey);
			
			//sifrovanje
			byte[] ciphertext = rsaCipherEnc.doFinal(data.getBytes());
			System.out.println("Kriptovan text: " + Base64.encodeToString(ciphertext));
			
			//desifrovanje poruke 
			//algoritam MORA biti isti kao i kod sifrovanja, provider moze da se razlikuje
			Cipher rsaCipherDec = Cipher.getInstance("AES");
			//inicijalizacija za dekriptovanje
			rsaCipherDec.init(Cipher.DECRYPT_MODE, secretKey);
			
			//dekriptovanje
			byte[] receivedTxt = rsaCipherDec.doFinal(ciphertext);
			System.out.println("Primljeni text: " + new String(receivedTxt));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		SymmetricEncription test = new SymmetricEncription();
		test.testIt();
	}
}
