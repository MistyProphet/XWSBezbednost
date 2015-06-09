package com.project.mt103;


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

import com.project.dao.Mt103DaoLocal;


@Path("/mt103")
public class Mt103Service {

	private static Logger log = Logger.getLogger(Mt103.class);

	@EJB
	private Mt103DaoLocal Mt103Dao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<Mt103> findByAll() {
		List<Mt103> retVal = null;
		try {
			retVal = Mt103Dao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Mt103 findById(@PathParam("id") String id) {
		Mt103 retVal = null;
		try {
			retVal = Mt103Dao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt103 create(Mt103 entity) {
    	Mt103 retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = Mt103Dao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt103 update(Mt103 entity) {
    	Mt103 retVal = null;
        try {
        	retVal = Mt103Dao.merge(entity, entity.getId());
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
        	Mt103Dao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
