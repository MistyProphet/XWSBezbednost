package com.project.banka;

public class WrongOverallSumException extends Exception {
	
	private static String message = "Overall sum of all invoices is not correct.";
	
	public WrongOverallSumException() {
		super(message);
	}
}
