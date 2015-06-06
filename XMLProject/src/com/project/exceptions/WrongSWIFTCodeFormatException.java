package com.project.exceptions;

public class WrongSWIFTCodeFormatException extends Exception {
	
	private static String message = "SWIFT code is not formated correctly.";
	
	public WrongSWIFTCodeFormatException() {
		super(message);
	}
}