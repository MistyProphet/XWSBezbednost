package rs.ac.uns.ftn.xws.sessionbeans.users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDaoLocal;

public interface UserDaoLocal extends GenericDaoLocal<User, Long>{
	
	public User login(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
	
	public void logout();
}
