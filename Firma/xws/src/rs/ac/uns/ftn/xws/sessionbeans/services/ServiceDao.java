package rs.ac.uns.ftn.xws.sessionbeans.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBException;
import javax.servlet.http.HttpServletRequest;

import misc.RESTUtil;

import rs.ac.uns.ftn.xws.entities.service.Service;
import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDao;

@Stateless
@Local(ServiceDaoLocal.class)
public class ServiceDao extends GenericDao<Service, Long> implements ServiceDaoLocal{
	
    public static final String contextPath = "rs.ac.uns.ftn.xws.entities.service";
	
    public static final String schemaName = "service";

    public ServiceDao() {
        super(contextPath, schemaName);
    }

    public Service findServiceWithName(String serviceName) throws IOException, JAXBException, NoSuchMethodException {
        List<Service> services = (List<Service>) em.runQuery(schemaName, "(//service[name=\""+serviceName+"\"])");
        if (services.size() > 1)
            throw new IllegalStateException("Multiple services with the same name. ");
        if (services.size() == 0)
            throw new NoSuchMethodException("Method " + serviceName + " was not found in the database. ");
        return services.get(0);
    }

    public Set<String> findAllRoles() throws IOException, JAXBException {
        List<Service> services = (List<Service>) em.findAll(schemaName);
        Set<String> roleNames = new HashSet<String>();

        for (Service s : services)
            roleNames.addAll(s.getRolesAllowed().getRole());

        return roleNames;
    }
    
}
