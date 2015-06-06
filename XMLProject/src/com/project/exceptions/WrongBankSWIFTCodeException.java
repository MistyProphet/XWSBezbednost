package com.project.exceptions;

public class WrongBankSWIFTCodeException extends Exception {
	
	private static String message = "Bank's SWIFT code does not match the SWIFT code from received document.";
	
	public WrongBankSWIFTCodeException() {
		super(message);
	}
}
