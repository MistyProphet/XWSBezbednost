package com.project.banka;

@SuppressWarnings("serial")
public class WrongTimestampException extends Exception {
	
	private static String message = "The timestamp is wrong or missing.";
	
	public WrongTimestampException() {
		super(message);
	}
}
