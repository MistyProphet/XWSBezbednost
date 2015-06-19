package rs.ac.uns.ftn.xws.services.users;

import java.util.ArrayList;
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
	public List<User> allUsers() {
		List<User> userList = new ArrayList<User>();
		try {
			userList = userDao.findAll("user");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return userList;
	}

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public User findById(@PathParam("id") String id) {
        User user = null;
        try {
            user = userDao.findById(Long.parseLong(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return user;
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
    public User findByName(@PathParam("username") String username) {
        List<User> users = null;
        try {
            users = userDao.findAll("user");
            for (User user : users)
                if (user.getUsername().equals(username))
                    return user;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
	
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String newUser(User sentUser) {
		try {
            System.out.println(sentUser); 
			userDao.persist(sentUser);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "ok";
	}
	
    //Loging in by GET is pretty dangerous
	//@GET 
    //@Path("login")
    //@Produces(MediaType.APPLICATION_JSON)
    //public User loginGet(@QueryParam("username") String username, @QueryParam("password") String password) {
    //	User user = null;
	//	try {
    //    	user = userDao.login(username, password);
    //    } catch (Exception e) {
    //    	log.error(e.getMessage(), e);
    //    }
	//	if(user==null){
	//		throw new ServiceException("Wrong username or password", Status.FORBIDDEN);
	//	}
	//	log.info("USER: "+user);
    //	return user;
    //}
    
	@POST
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(User sentUser) {
    	User user = null;
		try {
        	user = userDao.login(sentUser.getUsername(), sentUser.getPassword());
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
		if(user==null) {
			throw new ServiceException("Wrong username or password", Status.FORBIDDEN);
		}
    	return "ok";
    }
	
//	@GET
//    @Path("logout")
//    @Produces(MediaType.TEXT_HTML)
//    public String logout() {
//		try {
//			userDao.logout();
//        } catch (Exception e) {
//        	log.error(e.getMessage(), e);
//        }
//    	return "ok";
//    }

}
