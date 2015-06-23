package com.project.banka;

@SuppressWarnings("serial")
public class WrongSignatureException extends Exception {
	
	private static String message = "The signature is wrong.";
	
	public WrongSignatureException() {
		super(message);
	}
}

