package test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import misc.RESTUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDao;

public class BasexTest {
	InvoiceDaoLocal invoiceDao;

	@BeforeClass
	public static void clear() throws Exception {
        // CLEAR THE SCREEN
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        // CLEAR THE SCREEN ESCAPE SEQUENCE. May now work on Windows
	}

    @Before
    public void setUp() {
        invoiceDao = new InvoiceDao();
    }

	@Test
	public void testFindByID() throws JAXBException, IOException {
        Invoice invoice = invoiceDao.findById(1L);
        Assert.assertNotNull(invoice);
	}

    @Test
    public void testFindAllInvoices() throws IOException {
        List<Invoice> invoices = invoiceDao.findInvoicesByTIN("supplierTIN0");
        Assert.assertTrue(invoices.size() > 1);
    } 

}
