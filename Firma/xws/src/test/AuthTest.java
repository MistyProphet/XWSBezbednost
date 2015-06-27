package test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.interceptors.AuthenticationInterceptor;
import rs.ac.uns.ftn.xws.services.service.RoleService;
import rs.ac.uns.ftn.xws.sessionbeans.users.UserDao;
import rs.ac.uns.ftn.xws.sessionbeans.users.UserDaoLocal;


public class AuthTest {

    private AuthenticationInterceptor auth;

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
        auth = new AuthenticationInterceptor();
        System.setOut(null);
    }

    @Test
    public void testGetService() throws Exception {
        Method method = AuthenticationInterceptor.class.getDeclaredMethod("getService", Method.class);
        method.setAccessible(true);

        Method serviceMethod = RoleService.class.getDeclaredMethod("findAllRoles", null);

        Service service = (Service) method.invoke(auth, serviceMethod);
        Assert.assertNotNull(service);
        Assert.assertEquals("findAllRoles", service.getName());
    } 

    @Test
    public void testUserHasPrivileges() throws Exception {
        Method method = AuthenticationInterceptor.class.getDeclaredMethod("userHasPrivileges", User.class, Service.class);
        method.setAccessible(true);

        User user = makeUser();
        User admin = makeAdmin();
        Service adminService = makeAdminService();
        Service userService = makeUserService();
        
        Assert.assertTrue((boolean) method.invoke(auth, user, userService));
        Assert.assertFalse((boolean) method.invoke(auth, user, adminService));
        Assert.assertTrue((boolean) method.invoke(auth, admin, userService));
        Assert.assertTrue((boolean) method.invoke(auth, admin, adminService));
    }

    private Service makeUserService() {
        Service service = new Service();
        Service.RolesAllowed roles = new Service.RolesAllowed();
        roles.getRole().add("employee");
        service.setRolesAllowed(roles);
        return service;
    }
    
    private Service makeAdminService() {
        Service service = new Service();
        Service.RolesAllowed roles = new Service.RolesAllowed();
        roles.getRole().add("H1V3M1ND");
        roles.getRole().add("admin");
        service.setRolesAllowed(roles);
        return service;
    }

    private User makeUser() {
        User user = new User();
        User.Roles roles = new User.Roles();
        //List<String> newRoles = new ArrayList<String>();
        List<String> newRoles = roles.getRole();
        newRoles.add("employee");
        user.setRoles(roles);
   
        return user;
    }

    private User makeAdmin() {
        User user = new User();
        User.Roles roles = new User.Roles();
        //List<String> newRoles = new ArrayList<String>();
        List<String> newRoles = roles.getRole();
        newRoles.add("admin");
        newRoles.add("H1V3M1ND");
        newRoles.add("employee");
        user.setRoles(roles);
   
        return user;
    }
}
