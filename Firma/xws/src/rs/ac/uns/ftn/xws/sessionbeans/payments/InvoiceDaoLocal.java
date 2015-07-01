package rs.ac.uns.ftn.xws.sessionbeans.payments;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDaoLocal;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

public interface InvoiceDaoLocal extends GenericDaoLocal<Invoice, Long>{

    public List<Invoice> findIncomingInvoices() throws IOException, JAXBException;

    public List<Invoice> findOutgoingInvoices() throws IOException, JAXBException;

    public List<Invoice> findInvoicesByTIN(String TIN) throws IOException, JAXBException;

	public InvoiceItem findItemInInvoice(Long invoiceId, Long itemId) throws IOException, JAXBException;
	
	public Invoice removeItemFromInvoice(Long invoiceId, Long itemId) throws IOException, JAXBException;

	public Invoice createInvoiceItem(Long invoiceId, InvoiceItem item) throws IOException, JAXBException;

	public Invoice updateInvoiceItem(Long invoiceId, Long itemID, InvoiceItem item) throws IOException, JAXBException;

    public EntityManagerBaseX getEm();
}
