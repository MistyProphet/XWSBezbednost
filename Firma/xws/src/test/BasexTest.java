package test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import rs.ac.uns.ftn.xws.entities.partner.Partner;
import rs.ac.uns.ftn.xws.entities.payments.Invoice;
import rs.ac.uns.ftn.xws.entities.payments.InvoiceItem;
import rs.ac.uns.ftn.xws.sessionbeans.partners.PartnerDao;
import rs.ac.uns.ftn.xws.sessionbeans.partners.PartnerDaoLocal;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDao;
import rs.ac.uns.ftn.xws.sessionbeans.payments.InvoiceDaoLocal;

public class BasexTest {
	InvoiceDaoLocal invoiceDao;
    PartnerDaoLocal partnerDao;

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
        invoiceDao = new InvoiceDao();
        partnerDao = new PartnerDao();
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

    @Test
    public void testFindItemByID() throws IOException, JAXBException {
        List<Invoice> invoices = invoiceDao.findAll();
        Assert.assertNotNull(invoices);
        for (Invoice testInvoice : invoices) {
            for (InvoiceItem testItem : testInvoice.getInvoiceItems().getInvoiceItem()) {
                Long id = testItem.getId();
                InvoiceItem result = invoiceDao.findItemInInvoice(testInvoice.getId(), id);
                Assert.assertNotNull(result);
                Assert.assertEquals(testItem.getId(), result.getId());
            }
        }
    }            

    @Test
    public void testUpdateInvoiceItem() throws IOException, JAXBException {
        List<Invoice> invoices = invoiceDao.findAll();
        Assert.assertNotNull(invoices);
        for (Invoice testInvoice : invoices) {
            for (InvoiceItem testItem : testInvoice.getInvoiceItems().getInvoiceItem()) {
                Random random = new Random();
                double val = random.nextDouble();
                testItem.setAmount(val);
                invoiceDao.updateInvoiceItem(testInvoice.getId(), testItem.getId(), testItem);

                InvoiceItem result = invoiceDao.findItemInInvoice(testInvoice.getId(), testItem.getId());
                Assert.assertNotNull(result);
                Assert.assertEquals(val, result.getAmount(), 0.01);
            }
        }
    }

//    @Test void testCreateAndDeleteItem() throws IOException, JAXBException {
//        List<Invoice> invoices = invoiceDao.findAll();
//        Assert.assertNotNull(invoices);
//
//        InvoiceItem testItem = new InvoiceItem();
//
//        for (Invoice testInvoice : invoices) {
//            invoiceDao.createInvoiceItem(testInvoice.getId(), testItem);
//            InvoiceItem pulledItem = invoiceDao.
//            for (InvoiceItem testItem : testInvoice.getInvoiceItems().getInvoiceItem()) {
//                Random random = new Random();
//                double val = random.nextDouble();
//                testItem.setAmount(val);
//                invoiceDao.updateInvoiceItem(testInvoice.getId(), testItem.getId(), testItem);
//
//                InvoiceItem result = invoiceDao.findItemInInvoice(testInvoice.getId(), testItem.getId());
//                Assert.assertNotNull(result);
//                Assert.assertEquals(val, result.getAmount(), 0.01);
//            }
//        }
//    }

    @Test 
    public void getAllPartners() throws IOException, JAXBException {
        List<Partner> partners = partnerDao.findAll();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() > 0);
    }        

    @Test
    public void addPartner() throws IOException, JAXBException {
        Partner partner = new Partner();
        partner.setTIN("19583492057");
        partner.setURL("192.168.1.144");
        partner.setName("Toster");

        partnerDao.persist(partner);

        List<Partner> partners = partnerDao.findAll();
        Boolean foundPartner = false;
        for (Partner p : partners) 
            if (p.getTIN().equals(partner.getTIN()))
                foundPartner = true;

        Assert.assertTrue(foundPartner);
    }

    @Test 
    public void findAllTINs() throws IOException {
        List<String> tins = partnerDao.findAllTINs();
        for (String s : tins)  {
            System.out.println(s);
            Assert.assertEquals(11, s.length());
        }
    }

    @Test
    public void checkIfPartnerExists() throws IOException, JAXBException {
        Assert.assertTrue(partnerDao.isBusinessPartner("19583492057"));
    }
}
