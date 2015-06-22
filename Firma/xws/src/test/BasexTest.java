package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import misc.RESTUtil;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDao;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

public class BasexTest {
	InvoiceDaoLocal invoiceDao;

	@BeforeClass
	public static void clear() {
        // CLEAR THE SCREEN
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        // CLEAR THE SCREEN ESCAPE SEQUENCE. May now work on Windows
	}

    @Before
    public void setUp() {
        clear();
        invoiceDao = new InvoiceDao();
    }

	@Test
	public void testFindByID() throws JAXBException, IOException {
        Invoice invoice = invoiceDao.findById(1L);
        Assert.assertNotNull(invoice);
	}

    @Test
    public void testFindAllInvoices() throws IOException, JAXBException {
        List<Invoice> invoices = invoiceDao.findAll("invoice");
        if(invoices != null)
            Assert.assertTrue(invoices.size() > 1);
        else
            Assert.fail();
    } 

    @Test
    public void testFindAllInvoicesWithTIN() throws IOException, JAXBException {
        List<Invoice> invoices = invoiceDao.findInvoicesByTIN("supplierTIfN4");
        if(invoices != null) {
            Assert.assertTrue(invoices.size() > 1);
            for (Invoice invoice : invoices)
                Assert.assertEquals(invoice.getSupplierTIN(), "supplierTIfN4");
        }
    } 
}
