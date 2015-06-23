package com.project.banka;

@SuppressWarnings("serial")
public class WrongBankException extends Exception {
	
	private static String message = "Payer does not have an account in the bank that was proccessing the payment.";
	
	public WrongBankException() {
		super(message);
	}
}
