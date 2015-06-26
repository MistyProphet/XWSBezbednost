package test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rs.ac.uns.ftn.xws.entities.service.Service;
import rs.ac.uns.ftn.xws.entities.service.Service.RolesAllowed;
import rs.ac.uns.ftn.xws.sessionbeans.services.ServiceDao;
import rs.ac.uns.ftn.xws.sessionbeans.services.ServiceDaoLocal;

public class ServiceTest {
    private ServiceDaoLocal serviceDao;

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
        serviceDao = new ServiceDao();
    }

    @Test 
    public void addService() throws Exception {
        Service service = new Service();
        service.setName("testService");

        RolesAllowed roles = new RolesAllowed();
        List<String> rolesList = roles.getRole();
        rolesList.add("admin");
        rolesList.add("H1V3M1ND");
        service.setRolesAllowed(roles);

        serviceDao.persist(service);
        Assert.assertNotNull(serviceDao.findServiceWithName("testService"));
    }

    @Test 
    public void getAllServices() throws Exception {
        List<Service> services = serviceDao.findAll();
        Assert.assertNotNull(services);
        Assert.assertTrue(services.size() > 0);
    }

    @Test
    public void findServiceWithName() throws Exception {
        Service service = serviceDao.findServiceWithName("testService");
        Assert.assertNotNull(service);
    }

    @Test
    public void modifyService() throws Exception {
        Service service = serviceDao.findServiceWithName("testService");
        service.setName("newTestService");
        serviceDao.merge(service, service.getId());
        Assert.assertNotNull(serviceDao.findServiceWithName("newTestService"));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deleteService() throws IOException, JAXBException, NoSuchMethodException {
        Service serviceToDelete = serviceDao.findServiceWithName("newTestService");
        serviceDao.remove(serviceToDelete.getId());
        
        exception.expect(NoSuchMethodException.class);
        Service service = serviceDao.findServiceWithName("newTestService");
        Assert.assertNull(service);
    }
}
