package com.project.mt102;


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

import com.project.dao.Mt102DaoLocal;


@Path("/mt102")
public class Mt102Service {

	private static Logger log = Logger.getLogger(Mt102.class);

	@EJB
	private Mt102DaoLocal mt102Dao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<Mt102> findByAll() {
		List<Mt102> retVal = null;
		try {
			retVal = mt102Dao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Mt102 findById(@PathParam("id") String id) {
		Mt102 retVal = null;
		try {
			retVal = mt102Dao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt102 create(Mt102 entity) {
    	Mt102 retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = mt102Dao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt102 update(Mt102 entity) {
    	Mt102 retVal = null;
        try {
        	retVal = mt102Dao.merge(entity, entity.getId());
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
        	mt102Dao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
