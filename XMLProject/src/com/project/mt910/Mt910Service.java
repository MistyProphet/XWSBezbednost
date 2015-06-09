package com.project.mt910;


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

import com.project.dao.Mt910DaoLocal;


@Path("/mt910")
public class Mt910Service {

	private static Logger log = Logger.getLogger(Mt910.class);

	@EJB
	private Mt910DaoLocal Mt910Dao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<Mt910> findByAll() {
		List<Mt910> retVal = null;
		try {
			retVal = Mt910Dao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Mt910 findById(@PathParam("id") String id) {
		Mt910 retVal = null;
		try {
			retVal = Mt910Dao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt910 create(Mt910 entity) {
    	Mt910 retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = Mt910Dao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt910 update(Mt910 entity) {
    	Mt910 retVal = null;
        try {
        	retVal = Mt910Dao.merge(entity, entity.getId());
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
        	Mt910Dao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
