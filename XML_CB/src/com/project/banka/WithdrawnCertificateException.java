package com.project.banka;

@SuppressWarnings("serial")
public class WithdrawnCertificateException extends Exception {
	
	private static String message = "The certificate has been withdrawn.";
	
	public WithdrawnCertificateException() {
		super(message);
	}
}
