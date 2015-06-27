package rs.ac.uns.ftn.xws.services;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.users.UserDaoLocal;
import rs.ac.uns.ftn.xws.util.ServiceException;

@Path("/user")
public class UserService {

	private static Logger log = Logger.getLogger(UserService.class);

	@EJB
	private UserDaoLocal userDao;

	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<User> allUsers() throws IOException, JAXBException { 
	    return userDao.findAll("user");
	}

    @GET
    @Path("{username}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User findByName(@PathParam("username") String username) throws IOException, JAXBException { 
        return userDao.findUserWithUsername(username);
    }
	
	@POST
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void newUser(User sentUser) throws IOException, JAXBException {
		userDao.persist(sentUser);
	}
	
	@POST
    @Path("login")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User login(User sentUser) throws IOException, JAXBException {
        User user = userDao.login(sentUser.getUsername(), sentUser.getPassword());
		if(user==null) {
			throw new ServiceException("Wrong username or password", Status.FORBIDDEN);
		}
        return user;
    }
	
	@GET
    @Path("logout")
    public void logout() {
		userDao.logout();
    }

}
