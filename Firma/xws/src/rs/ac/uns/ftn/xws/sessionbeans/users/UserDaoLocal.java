package rs.ac.uns.ftn.xws.sessionbeans.users;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.xml.bind.JAXBException;

import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDaoLocal;

public interface UserDaoLocal extends GenericDaoLocal<User, Long> {
	
    public User findUserWithUsername(String username) throws IOException, JAXBException;
	public User login(String username, String password) throws IOException, JAXBException, NotFoundException, NotAuthorizedException;
	public void logout();
}
