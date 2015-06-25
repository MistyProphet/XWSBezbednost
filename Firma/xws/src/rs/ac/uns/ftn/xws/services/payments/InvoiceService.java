package rs.ac.uns.ftn.xws.services.payments;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;
import rs.ac.uns.ftn.xws.util.ValidateSupplier;

@ValidateSupplier
@Path("/partneri/{PIB}/fakture")
public class InvoiceService {

	private static Logger log = Logger.getLogger(Invoice.class);

    @Context
    private HttpServletResponse response;

	@EJB
	private InvoiceDaoLocal invoiceDao;

// ########## Gets for the invoices and the items
    
    /**
     * Returns a list of invoices of the supplier the PIN in the URL.
     * @return A list of suppliers invoices, as an XML or JSON.
     * In case the supplier isn't a business partner, returns 403 Forbidden.
     */
	@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Invoice> findByPIB(@PathParam("PIB") String PIB) {
		List<Invoice> retVal = null;
        try {
            retVal = invoiceDao.findInvoicesByTIN(PIB);
		    return retVal;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    
    /**
     * Returns an invoice with the ID in the URL.
     * @param id The ID of the invoice we're adding the item to
     * @return The invoice with the selected ID.
     * In case the supplier isn't a business partner, returns 403 Forbidden.
     * In case the invoice doesn't exist, returns 404 Not Found.
     */
	@GET 
	@Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Invoice findById(@PathParam("PIB") String PIB, @PathParam("id") String id) {
		Invoice retVal = null;
        try {
		    retVal = invoiceDao.findById(Long.parseLong(id));
            if (retVal.getSupplierTIN().equals(PIB))
                return retVal;
		    return null;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
    
	@GET 
	@Path("{id}/stavke")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<InvoiceItem> findAllItems(@PathParam("PIB") String PIB, @PathParam("id") String id) {
		Invoice retVal = null;
		try {
			retVal = invoiceDao.findById(Long.parseLong(id));
            if (retVal.getSupplierTIN().equals(PIB))
                return retVal.getInvoiceItems().getInvoiceItem();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }

	@GET 
	@Path("{id}/stavke/{item_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InvoiceItem findItemByID(@PathParam("PIB") String PIB, @PathParam("id") String id, @PathParam("item_id") String item_id) {
		try {
			return invoiceDao.findItemInInvoice(Long.parseLong(id), Long.parseLong(item_id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }

// ############### Puts and Posts and the Deletes
    
    /**
     * Adds a new invoice.
     * @param entity The invoice passed as either an xml or a json file.
     * @return Returns 201 Created, with Content-Location pointing to the newly created invoice.
     * In case the supplier isn't a business partner, returns 403 Forbidden.
     * In case the invoice passed is invalid, returns 400 Bad Request.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createInvoice(Invoice entity) {
		try {
			System.out.println("entity: "+entity);
			invoiceDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
    
    /**
     * Adds a new item to the existing invoice with the ID in the URL.
     * @param newItem The invoice item to be added
     * @param id The ID of the invoice we're adding the item to
     * @return Returns 201 if the supplier is a business partner and the invoice exists, 
     * and sets Content-Location to the URL of the new invoice item.
     * In case the supplier isn't a business partner, returns 403 Forbidden.
     * In case the invoice doesn't exist, returns 404 Not Found.
     * In case the invoice item passed is invalid, returns 400 Bad Request.
     */
    @POST
    @Path("{id}/stavke/")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createInvoiceItem(InvoiceItem newItem, @PathParam("id") String id) {
        //TODO business logic
        try {
            invoiceDao.createInvoiceItem(Long.parseLong(id), newItem);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }

    /**
     * Modifies an existing invoice item with the invoice and the item IDs in the URL
     * @return If the supplier is a business partner and the invoice an the item exist,
     * returns 200 OK.
     * In case the supplier isn't a business partner, returns 403 Forbidden.
     * In case the invoice or the item doesn't exist, returns 404 Not Found.
     * In case the invoice item passed is invalid, returns 400 Bad Request.
     */
    @PUT
    @Path("{id}/stavke/{item_id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateInvoiceItem(InvoiceItem newItem, @PathParam("id") String id, @PathParam("item_id") String item_id) {
        try {
            invoiceDao.updateInvoiceItem(Long.parseLong(id), Long.parseLong(item_id), newItem);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
    
    /**
     * Deletes an invoice item with the ID in the URL.
     * @return If the supplier is a business partner and the item exists, deletes it and returns 204 No Content.
     * If the supplier is not a business partner, returns 403 Forbidden.
     * If the invoice or the invoice item is not found, returns 404 Not Found.
     */
    @DELETE
    @Path("{id}/stavke/{item_id}")
    public void deleteInvoiceItemWithID(@PathParam("id") String id, @PathParam("item_id") String item_id) {
        try {
            invoiceDao.removeItemFromInvoice(Long.parseLong(id), Long.parseLong(item_id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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
