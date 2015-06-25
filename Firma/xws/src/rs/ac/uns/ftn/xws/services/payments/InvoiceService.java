package rs.ac.uns.ftn.xws.services.payments;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;
import rs.ac.uns.ftn.xws.util.ValidateSupplier;

@ValidateSupplier
@Path("/partneri/{PIB}/fakture")
public class InvoiceService {

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
    public List<Invoice> findByPIB(@PathParam("PIB") String PIB) throws Exception {
		List<Invoice> retVal = null;
        retVal = invoiceDao.findInvoicesByTIN(PIB);
		return retVal;
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
    public Invoice findById(@PathParam("PIB") String PIB, @PathParam("id") String id) throws Exception {
		Invoice retVal = null;
	    retVal = invoiceDao.findById(Long.parseLong(id));
        if (retVal.getSupplierTIN().equals(PIB))
            return retVal;
        return null;
    }
    
	@GET 
	@Path("{id}/stavke")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<InvoiceItem> findAllItems(@PathParam("PIB") String PIB, @PathParam("id") String id) throws Exception {
		Invoice retVal = null;
		retVal = invoiceDao.findById(Long.parseLong(id));
        if (retVal.getSupplierTIN().equals(PIB))
            return retVal.getInvoiceItems().getInvoiceItem();
		return null;
    }

	@GET 
	@Path("{id}/stavke/{item_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InvoiceItem findItemByID(@PathParam("PIB") String PIB, @PathParam("id") String id, @PathParam("item_id") String item_id) throws Exception {
		return invoiceDao.findItemInInvoice(Long.parseLong(id), Long.parseLong(item_id));
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
    public void createInvoice(Invoice entity) throws Exception {
		invoiceDao.persist(entity);
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
    public void createInvoiceItem(InvoiceItem newItem, @PathParam("id") String id) throws Exception {
        invoiceDao.createInvoiceItem(Long.parseLong(id), newItem);
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
    public void updateInvoiceItem(InvoiceItem newItem, @PathParam("id") String id, @PathParam("item_id") String item_id) throws Exception {
        invoiceDao.updateInvoiceItem(Long.parseLong(id), Long.parseLong(item_id), newItem);
    }
    
    /**
     * Deletes an invoice item with the ID in the URL.
     * @return If the supplier is a business partner and the item exists, deletes it and returns 204 No Content.
     * If the supplier is not a business partner, returns 403 Forbidden.
     * If the invoice or the invoice item is not found, returns 404 Not Found.
     */
    @DELETE
    @Path("{id}/stavke/{item_id}")
    public void deleteInvoiceItemWithID(@PathParam("id") String id, @PathParam("item_id") String item_id) throws Exception {
        invoiceDao.removeItemFromInvoice(Long.parseLong(id), Long.parseLong(item_id));
    }
}
