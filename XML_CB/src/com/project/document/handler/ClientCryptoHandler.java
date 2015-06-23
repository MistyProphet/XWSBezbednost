package com.project.document.handler;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import misc.DocumentUtil;

import org.w3c.dom.Document;

import com.project.document.crypto.enc.Decrypt;
import com.project.document.crypto.enc.Encrypt;

public class ClientCryptoHandler implements LogicalHandler<LogicalMessageContext> {

	@Override
	public boolean handleMessage(LogicalMessageContext context) {

		System.out.println("\n*** Handler za kriptovanje kod Klijenta ***");

		Boolean isResponse = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		Source source = context.getMessage().getPayload();
		Document document = DocumentUtil.convertToDocument(source);
		
		if (!isResponse) {
			System.err.println("\n-- Kriptovanje --");
			Document encryptedDoc = Encrypt.encryptDocument(document);
			try {
				DocumentUtil.printDocument(encryptedDoc);
			} catch (Exception e) {}
			context.getMessage().setPayload(new DOMSource(encryptedDoc));
		} else {
			System.err.println("\n-- Dekriptovanje --");	
			
			Document decryptedDoc = Decrypt.decryptDocument(document);
			try {
				DocumentUtil.printDocument(decryptedDoc);
			} catch (Exception e) {}
			context.getMessage().setPayload(new DOMSource(decryptedDoc));
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
