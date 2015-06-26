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

import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.users.UserDao;
import rs.ac.uns.ftn.xws.sessionbeans.users.UserDaoLocal;


public class UserTest{
    private UserDaoLocal userDao;

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
        userDao = new UserDao();
    }
    
    @Test
    public void addUser() throws Exception {
        User user =  userDao.findUserWithUsername("testUser");
        if (user != null)
            userDao.remove(user.getId());

        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        User.Roles roles = new User.Roles();
        roles.getRole().add("test role 1");
        roles.getRole().add("test role 2");
        user.setRoles(roles);

        userDao.persist(user);
        User newUser = userDao.findUserWithUsername("testUser");

        Assert.assertEquals("testUser", newUser.getUsername());
    }
    
    @Ignore // request not working
    @Test
    public void testLogin() throws Exception {
        User user = userDao.login("testUser", "testPass");
        Assert.assertNotNull(user);
        Assert.assertEquals("testUser", user.getUsername());
    }
}
