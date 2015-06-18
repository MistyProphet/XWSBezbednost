package rs.ac.uns.ftn.xws.sessionbeans.payments;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDaoLocal;

public interface InvoiceDaoLocal extends GenericDaoLocal<Invoice, Long>{

	public InvoiceItem findItemInInvoice(Long invoiceId, Long itemId) throws IOException, JAXBException;
	
	public Invoice removeItemFromInvoice(Long invoiceId, Long itemId) throws IOException, JAXBException;

	public Invoice createInvoiceItem(Long invoiceId, InvoiceItem item) throws IOException, JAXBException;

	public Invoice updateInvoiceItem(Long invoiceId, InvoiceItem item) throws IOException, JAXBException;
}
