package com.project.banka;

@SuppressWarnings("serial")
public class WrongIdSignatureException extends Exception {
	
	private static String message = "The message number is repeated (replay attack).";
	
	public WrongIdSignatureException() {
		super(message);
	}
}

