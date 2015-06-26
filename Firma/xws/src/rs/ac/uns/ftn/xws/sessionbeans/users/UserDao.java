package rs.ac.uns.ftn.xws.sessionbeans.users;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBException;
import javax.servlet.http.HttpServletRequest;

import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDao;

@Stateless
@Local(UserDaoLocal.class)
public class UserDao extends GenericDao<User, Long> implements UserDaoLocal{
    
    @Context
	private HttpServletRequest request;

	public static final String contextPath = "rs.ac.uns.ftn.xws.entities.user";
	
	public static final String schemaName = "user";
	
	public UserDao() {
		super(contextPath, schemaName);
	}

    @Override
    public User findUserWithUsername(String username) throws IOException, JAXBException {
        List<User> users = em.runQuery(schemaName, "(//user[username=\""+username+"\"])");
        if (users.size() == 1) 
            return users.get(0);
        else if (users.size() > 1) 
            throw new IllegalStateException("Multiple users with the username: " + username + " found. ");
		return null;
    }

	@Override
	public User login(String username, String password) throws IOException, JAXBException, NotFoundException, NotAuthorizedException {
        User user = findUserWithUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            request.getSession().setAttribute("user", user);
            return user;
        }
        else if (user == null)
            throw new NotFoundException("User not found "); 
        else 
            throw new NotAuthorizedException("Wrong password ");
	}

	@Override
	public void logout() {
        request.getSession().invalidate();
	}
}
