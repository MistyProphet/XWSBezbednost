package rs.ac.uns.ftn.xws.services.payments;


import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;

@Path("/invoice")
public class InvoiceService {

	private static Logger log = Logger.getLogger(Invoice.class);

	@EJB
	private InvoiceDaoLocal invoiceDao;

	@GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Invoice> findByAll() {
		List<Invoice> retVal = null;
		try {
			retVal = invoiceDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Invoice findById(@PathParam("id") String id) {
		Invoice retVal = null;
		try {
			retVal = invoiceDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Invoice create(Invoice entity) {
		Invoice retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = invoiceDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Invoice update(Invoice entity) {
    	Invoice retVal = null;
        try {
        	retVal = invoiceDao.merge(entity, entity.getId());
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @DELETE 
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public String remove(@PathParam("id") Long id) {
    	try {
        	invoiceDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }

    /*
     * Invoice items within an invoice.
     */

    @POST
    @Path("{id}/item")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Invoice createInvoiceItem(@PathParam("id") Long invoiceId, InvoiceItem item) {
		Invoice retVal = null;
		try {
			retVal = invoiceDao.createInvoiceItem(invoiceId, item);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @GET 
	@Path("{id}/item/{itemId}")
    @Produces(MediaType.APPLICATION_XML)
    public InvoiceItem findItemInInvoice(@PathParam("id") Long invoiceId, @PathParam("itemId") Long itemId) {
    	InvoiceItem retVal = null;
		try {
			retVal = invoiceDao.findItemInInvoice(invoiceId, itemId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @PUT
    @Path("{id}/item/{itemId}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Invoice updateInvoiceItem(@PathParam("id") Long invoiceId, InvoiceItem item) {
    	Invoice retVal = null;
        try {
        	retVal = invoiceDao.updateInvoiceItem(invoiceId, item);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @DELETE 
    @Path("{id}/item/{itemId}")
    @Produces(MediaType.TEXT_HTML)
    public String removeItemFromInvoice(@PathParam("id") Long invoiceId, @PathParam("itemId") Long itemId) {
    	try {
    		invoiceDao.removeItemFromInvoice(invoiceId, itemId);
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    	}
    	return "ok";
    }
    
}
