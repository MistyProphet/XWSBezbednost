package rs.ac.uns.ftn.xws.sessionbeans.users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.management.Query;
import javax.ws.rs.core.Context;
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
	public User login(String username, String password) {
        //TODO find the user by querying the database. This is suicide
        
        try {
            List<User> users = this.findAll("user");
            for (User user : users)
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    request.getSession().setAttribute("user", user);
                    return user;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return null;
	}

	@Override
	public void logout() {
        request.getSession().invalidate();
	}
}
