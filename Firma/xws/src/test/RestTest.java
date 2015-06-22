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
import rs.ac.uns.ftn.xws.services.payments.InvoiceService;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDao;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

public class RestTest {

    InvoiceService service;
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
        service = new InvoiceService();
    }

    @Test
    public void testGetAllInvoices() {
        List<Invoice> invoices = service.findByPIB("supplierTIfN4");
        Assert.assertNotNull(invoices);
        for (Invoice i : invoices)
            Assert.assertEquals(i.getSupplierTIN(), "supplierTIfN4");
    }

}
