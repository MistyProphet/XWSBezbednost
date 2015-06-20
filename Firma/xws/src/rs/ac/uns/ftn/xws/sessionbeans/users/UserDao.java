package rs.ac.uns.ftn.xws.sessionbeans.users;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletRequest;

import misc.RESTUtil;

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
        try {
            List<User> users = new ArrayList<User>();
            try {
                users = (List<User>) RESTUtil.doUnmarshall(
                        "(//user)[username=\"" + username + "\"]",
                        "user",
                        users);

                if (users.size() == 1) {
                    User user = users.get(0);
                    request.getSession().setAttribute("user", user);
                    return user;
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
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
