package com.project.document.handler;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.project.document.crypto.sig.SignEnveloped;
import com.project.document.crypto.sig.VerifySignatureEnveloped;
import com.project.exceptions.WithdrawnCertificateException;
import com.project.exceptions.WrongIdSignatureException;
import com.project.exceptions.WrongTimestampException;
import com.project.util.DocumentUtil;

public class WSSignatureHandler implements LogicalHandler<LogicalMessageContext> {

	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		System.out.println("\n*** Handler za digitalno potpisivanje kod Web Servisa ***");

		Boolean isResponse = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		Source source = context.getMessage().getPayload();
		Document document = DocumentUtil.convertToDocument(source);

		if (isResponse) {
			System.err.println("\nDokument koji je stigao");
			try {
				DocumentUtil.printDocument(document);
			} catch (Exception e) {}
			System.err.println("\n-- Potpisivanje --");			
			
			Document signedDocument = SignEnveloped.signDocument(document, true);		
			Source signedSource = new DOMSource(signedDocument);
			try {
				DocumentUtil.printDocument(signedDocument);
			} catch (Exception e) {}
			context.getMessage().setPayload(signedSource);			
		} else {
			System.err.println("\nValidacija i skidanje potpisa...");

			boolean signatureValid = false;
			try {
				try {
					signatureValid = VerifySignatureEnveloped.verifySignature(document);
				} catch (WithdrawnCertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (WrongTimestampException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (WrongIdSignatureException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(!signatureValid) {
				return false; // potpis nije validan
			}
			// uklanjanje potpisa
			Element element =  (Element) document.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature").item(0);  
			element.getParentNode().removeChild(element);
			try {
				DocumentUtil.printDocument(document);
			} catch (Exception e) {}
			context.getMessage().setPayload(new DOMSource(document));
		}
		return true;
	}

	@Override
	public boolean handleFault(LogicalMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

}
