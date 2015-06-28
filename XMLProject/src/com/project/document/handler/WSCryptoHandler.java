package com.project.document.handler;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.project.document.crypto.enc.Decrypt;
import com.project.document.crypto.enc.Encrypt;
import com.project.util.DocumentUtil;

public class WSCryptoHandler implements LogicalHandler<LogicalMessageContext> {

	@Override
	public boolean handleMessage(LogicalMessageContext context) {

		System.out.println("\n*** Handler za kriptovanje kod Web Servisa ***");
		
		
		Boolean isResponse = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		Source source = context.getMessage().getPayload();
		Document document = DocumentUtil.convertToDocument(source);
		try {
			System.out.println("Pristigli dokument u bankin handler");
			DocumentUtil.printDocument(document);
		} catch (Exception e) {
			
		}
		NodeList nl = document.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
		if(nl.getLength() > 0){
			System.out.println("Handle-ovan fault!");
			return true;
		}
		if (isResponse) {
			System.err.println("\n-- Kriptovanje --");
			Document encryptedDoc = Encrypt.encryptDocument(document);
			
			context.getMessage().setPayload(new DOMSource(encryptedDoc));
		} else {
			System.err.println("\n-- Dekriptovanje --");	
			
			Document decryptedDoc = Decrypt.decryptDocument(document);
			context.getMessage().setPayload(new DOMSource(decryptedDoc));
			try {
				System.out.println("Dekriptovan dokument banka");
				DocumentUtil.printDocument(document);
			} catch (Exception e) {
				
			}
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
