package rs.ac.uns.ftn.xws.services.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import rs.ac.uns.ftn.xws.entities.service.Service;
import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.services.ServiceDaoLocal;
import rs.ac.uns.ftn.xws.sessionbeans.users.UserDaoLocal;
import rs.ac.uns.ftn.xws.util.ServiceException;

@Path("/service")
public class RoleService {

	private static Logger log = Logger.getLogger(RoleService.class);

	@EJB
	private ServiceDaoLocal serviceDao;

	@GET
	@Path("{serviceName}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Service findServiceWithName(@PathParam("serviceName") String serviceName) throws Exception {
        return serviceDao.findServiceWithName(serviceName);
	}

    @GET
    @Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<String> findAllRoles() throws Exception {
        Set<String> serviceNames = serviceDao.findAllRoles();
        return new ArrayList<String>(serviceNames);
    }
    
    @POST
    @Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addNewService(Service service) throws Exception {
        serviceDao.persist(service);
    }
    
    
    @PUT
    @Path("{serviceName}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void modifyService(Service service) throws Exception {
        serviceDao.merge(service, service.getId());
    }

    @DELETE
    @Path("{serviceName}")
    public void deleteService(String serviceId) throws Exception {
        serviceDao.remove(Long.parseLong(serviceId)); 
    }
}
