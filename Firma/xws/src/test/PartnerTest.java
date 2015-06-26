package test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import rs.ac.uns.ftn.xws.entities.partner.Partner;
import rs.ac.uns.ftn.xws.sessionbeans.partners.PartnerDao;
import rs.ac.uns.ftn.xws.sessionbeans.partners.PartnerDaoLocal;

public class PartnerTest {
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
        partnerDao = new PartnerDao();
    }


    @Test 
    public void getAllPartners() throws IOException, JAXBException {
        List<Partner> partners = partnerDao.findAll();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() > 0);
    }        

    @Test
    public void addPartner() throws IOException, JAXBException {
        Partner partner = new Partner();
        partner.setTIN("supplierTIfN4");
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

    @Ignore
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
        Assert.assertTrue(partnerDao.isBusinessPartner("supplierTIfN4"));
    }
}
