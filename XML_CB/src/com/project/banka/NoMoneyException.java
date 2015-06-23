package com.project.banka;

@SuppressWarnings("serial")
public class NoMoneyException extends Exception {
	
	private static String message = "Not enough money in the account to make the payment.";
	
	public NoMoneyException() {
		super(message);
	}

}
