package com.project.exceptions;

@SuppressWarnings("serial")
public class NoSignatureException extends Exception {
	
	private static String message = "The signature is missing.";
	
	public NoSignatureException() {
		super(message);
	}
}
