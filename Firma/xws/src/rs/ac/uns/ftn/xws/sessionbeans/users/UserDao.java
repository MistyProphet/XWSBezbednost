package rs.ac.uns.ftn.xws.sessionbeans.users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.management.Query;

import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDao;

@Stateless
@Local(UserDaoLocal.class)
public class UserDao extends GenericDao<User, Long> implements UserDaoLocal{

	public static final String contextPath = "rs.ac.uns.ftn.xws.entities.user";
	
	public static final String schemaName = "user";
	
	public UserDao() {
		super(contextPath, schemaName);
	}

	@Override
	public User login(String username, String password) {
		return null;
	}

	@Override
	public void logout() {
	}
}
