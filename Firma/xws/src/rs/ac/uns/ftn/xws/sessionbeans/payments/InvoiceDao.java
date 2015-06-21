package rs.ac.uns.ftn.xws.sessionbeans.payments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import misc.RESTUtil;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDao;

@Stateless
@Local(InvoiceDaoLocal.class)
public class InvoiceDao extends GenericDao<Invoice, Long> implements InvoiceDaoLocal{

	public static final String contextPath = "rs.ac.uns.ftn.xws.entities.payments";
	
	public static final String schemaName = "invoice";
	
	public InvoiceDao() {
		super(contextPath, schemaName);
	}

    public List<Invoice> findInvoicesByTIN(String TIN) throws IOException {
        List<Invoice> invoices = new ArrayList<Invoice>();

        invoices = (List<Invoice>) RESTUtil.doUnmarshall(
                    "(/)[supplierTIN=\"" + TIN +"\"]",
                    "invoice",
                    invoices);
        return invoices;
    }


	public InvoiceItem findItemInInvoice(Long invoiceId, Long itemId) throws IOException, JAXBException {
		Invoice invoice = findById(invoiceId);
		
		if (invoice instanceof Invoice) 
			for (InvoiceItem item : invoice.getInvoiceItems().getInvoiceItem())
				if (item.getId().equals(itemId))
					return item;
				
		return null;
	}

	public Invoice removeItemFromInvoice(Long invoiceId, Long itemId) throws IOException, JAXBException {
		Invoice invoice = findById(invoiceId);
		
		if (invoice instanceof Invoice) 
			for (Iterator<InvoiceItem> iter = invoice.getInvoiceItems().getInvoiceItem().listIterator(); iter.hasNext(); ) {
			    InvoiceItem item = iter.next();
			    if (item.getId().equals(itemId)) {
			        iter.remove();
			    }
			}
		
		return merge(invoice, invoiceId);
	}

	public Invoice createInvoiceItem(Long invoiceId, InvoiceItem item) throws IOException, JAXBException {
		Invoice invoice = findById(invoiceId);
		
		if (invoice instanceof Invoice) { 
			item.setId(em.getIdentity());
			invoice.getInvoiceItems().getInvoiceItem().add(item);
		}
		
		return merge(invoice, invoiceId);
	}
	
	public Invoice updateInvoiceItem(Long invoiceId, InvoiceItem item) throws IOException, JAXBException {
		Invoice invoice = findById(invoiceId);
		
		if (invoice instanceof Invoice) { 
			for (Iterator<InvoiceItem> iter = invoice.getInvoiceItems().getInvoiceItem().listIterator(); iter.hasNext(); ) {
			    InvoiceItem invoiceItem = iter.next();
			    if (invoiceItem.getId().equals(item.getId())) {
			        iter.remove();
			    }
			}
			invoice.getInvoiceItems().getInvoiceItem().add(item);
		}
		
		return merge(invoice, invoiceId);
	}
}
