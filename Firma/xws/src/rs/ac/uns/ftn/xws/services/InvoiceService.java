package rs.ac.uns.ftn.xws.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.entities.self.Company;
import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;
import rs.ac.uns.ftn.xws.util.Authenticate;
import rs.ac.uns.ftn.xws.util.ServiceException;
import rs.ac.uns.ftn.xws.util.ValidateSupplier;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

@Authenticate
public class InvoiceService {

    @Context
    private HttpServletResponse response;

    @Context
    private HttpServletRequest request;

	@EJB
	private InvoiceDaoLocal invoiceDao;

// ########## Gets for the invoices and the items
    
    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Invoice> findAllInvoices() throws Exception {
        return invoiceDao.findAll();
    }

    @GET
    @Path("/pristigle")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Invoice> findAllIncoming() throws Exception {
        List<Invoice> retVal = invoiceDao.findIncomingInvoices();    
        return retVal;
    }

    @POST
    @Path("/pristigle")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createNewInvoice(Invoice invoice) {
        try {
            invoiceDao.persist(invoice);
            response.setStatus(201);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
        }
    } 

    @PUT
    @Path("/pristigle/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateInvoice(Invoice invoice, @PathParam("id") String id) {
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRoles().getRole().contains("shef") || !user.getRoles().getRole().contains("direktor"))
            throw new ServiceException("Not authorized to change the invoice", Status.FORBIDDEN);

        try {
            Invoice oldInvoice = invoiceDao.findById(invoice.getId());
            if (!checkIfIllegalChange(oldInvoice, invoice))
                throw new ServiceException("Illegal invoice change", Status.BAD_REQUEST);

            if (user.getRoles().getRole().contains("direktor")) {
                invoiceDao.remove(invoice.getId());
                send(invoice); 
                return;
            }
            if (user.getRoles().getRole().contains("shef")) {
                if (invoice.getTotalAmount() < Company.getInstance().getMaxAmount()) {
                    invoiceDao.remove(invoice.getId());
                    send(invoice); 
                    return;
                }
                invoiceDao.merge(invoice, invoice.getId());
            }

            response.setStatus(201);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
        }
    } 

    private HttpResponse send(Invoice invoice) throws IOException, JAXBException {
        HttpPost post = new HttpPost(invoice.getBuyerAddress() + "/Firma/api/pristigle");
        post.setHeader("Content-Type", "application/xml");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        invoiceDao.getEm().getMarshaller().marshal(invoice, stream);

        post.setEntity(new ByteArrayEntity(stream.toByteArray()));

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        return response;
    }

    private boolean checkIfIllegalChange(Invoice o, Invoice n) {
        if (o.getSupplierTIN().equals(n.getSupplierTIN()) &&
            o.getDate().equals(n.getDate()) &&
            o.getBuyerName().equals(n.getBuyerName()) &&
            o.getDate().equals(n.getDate()) &&
            o.getBuyerAddress().equals(n.getBuyerAddress()) &&
            o.getSupplierName().equals(n.getSupplierName()) &&
            o.getAccountNumber() == n.getAccountNumber())
            return true;
        return false;
    }

    @DELETE
    @Path("/pristigle/{id}")
    public void deleteInvoice(@PathParam("id") String id) {
        try {
            invoiceDao.remove(Long.parseLong(id));
        } catch (IOException e) {
            response.setStatus(400);
        }
    }

    @GET
    @Path("/pristigle/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Invoice findAllIncoming(@PathParam("id") String id) throws Exception {
        Invoice retVal = invoiceDao.findById(Long.parseLong(id));    
        return retVal;
    }

    //@PUT
    //@Path("/pristigle/{id}")
    //@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //public 
    
//    @GET
//    @Path("/pristigleStavke/{id}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public InvoiceItem findAllIncomingItems(@PathParam("id") String id) throws Exception {
//        InvoiceItem retVal = invoiceDao.findById(Long.parseLong(id));    
//        return retVal;
//    }

    /**
     * Returns a list of outgoing invoices not yet sent.
     * @return A list of invoices still being prepared.
     */
    @GET
    @Path("/slanje")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Invoice> findAllOutgoing() throws Exception {
        List<Invoice> retVal = invoiceDao.findOutgoingInvoices();
        return retVal;
    }



//####################################################################################################


    /**
     * Returns a list of invoices of the supplier the PIN in the URL.
     * @return A list of suppliers invoices, as an XML or JSON.
     * In case the supplier isn't a business partner, returns 403 Forbidden.
     */
    @ValidateSupplier
	@GET
    @Path("/partneri/{PIB}/fakture")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Invoice> findByPIB(@PathParam("PIB") String PIB) throws Exception {
		List<Invoice> retVal = null;
        //FIXME
        if(PIB.equals("svi"))
            retVal = invoiceDao.findIncomingInvoices();
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
    @ValidateSupplier
	@GET 
    @Path("/partneri/{PIB}/fakture/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Invoice findById(@PathParam("PIB") String PIB, @PathParam("id") String id) throws Exception {
		Invoice retVal = null;
	    retVal = invoiceDao.findById(Long.parseLong(id));
        if (retVal.getSupplierTIN().equals(PIB))
            return retVal;
        return null;
    }
    
    @ValidateSupplier
	@GET 
	@Path("/partneri/{PIB}/fakture/{id}/stavke")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<InvoiceItem> findAllItems(@PathParam("PIB") String PIB, @PathParam("id") String id) throws Exception {
		Invoice retVal = null;
		retVal = invoiceDao.findById(Long.parseLong(id));
        if (retVal.getSupplierTIN().equals(PIB))
            return retVal.getInvoiceItems().getInvoiceItem();
		return null;
    }

    @ValidateSupplier
	@GET 
	@Path("/partneri/{PIB}/fakture/{id}/stavke/{item_id}")
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
    @ValidateSupplier
    @POST
    @Path("/partneri/{PIB}/fakture")
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
    @ValidateSupplier
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
    @ValidateSupplier
    @PUT
    @Path("/partneri/{PIB}/fakture/{id}/stavke/{item_id}")
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
    @ValidateSupplier
    @DELETE
    @Path("/partneri/{PIB}/fakture/{id}/stavke/{item_id}")
    public void deleteInvoiceItemWithID(@PathParam("id") String id, @PathParam("item_id") String item_id) throws Exception {
        invoiceDao.removeItemFromInvoice(Long.parseLong(id), Long.parseLong(item_id));
    }
}
