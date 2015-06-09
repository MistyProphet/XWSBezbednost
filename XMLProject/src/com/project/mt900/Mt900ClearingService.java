package com.project.mt900;


import java.util.List;

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

import org.apache.log4j.Logger;

import com.project.dao.Mt900ClearingDaoLocal;


@Path("/mt900clearing")
public class Mt900ClearingService {

	private static Logger log = Logger.getLogger(Mt900Clearing.class);

	@EJB
	private Mt900ClearingDaoLocal Mt900ClearingDao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<Mt900Clearing> findByAll() {
		List<Mt900Clearing> retVal = null;
		try {
			retVal = Mt900ClearingDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Mt900Clearing findById(@PathParam("id") String id) {
		Mt900Clearing retVal = null;
		try {
			retVal = Mt900ClearingDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt900Clearing create(Mt900Clearing entity) {
    	Mt900Clearing retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = Mt900ClearingDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt900Clearing update(Mt900Clearing entity) {
    	Mt900Clearing retVal = null;
        try {
        	retVal = Mt900ClearingDao.merge(entity, entity.getId());
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @DELETE 
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public String remove(@PathParam("id") Long id) {
    	try {
        	Mt900ClearingDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}