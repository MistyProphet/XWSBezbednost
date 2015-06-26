package rs.ac.uns.ftn.xws.sessionbeans.services;

import java.io.IOException;
import java.util.Set;

import javax.xml.bind.JAXBException;

import rs.ac.uns.ftn.xws.entities.service.Service;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDaoLocal;

public interface ServiceDaoLocal extends GenericDaoLocal<Service, Long>{
    public Service findServiceWithName(String serviceName) throws IOException, JAXBException, NoSuchMethodException;
    public Set<String> findAllRoles() throws IOException, JAXBException;
}
