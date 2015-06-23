package com.project.banka;

@SuppressWarnings("serial")
public class NonexistentAccountException extends Exception {

	private static String message = "Account does not exist in current bank.";
	
	public NonexistentAccountException() {
		super(message);
	}
}
