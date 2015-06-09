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

import com.project.dao.Mt900RTGSDaoLocal;


@Path("/mt900rtgs")
public class Mt900RTGSService {

	private static Logger log = Logger.getLogger(Mt900RTGS.class);

	@EJB
	private Mt900RTGSDaoLocal Mt900RTGSDao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<Mt900RTGS> findByAll() {
		List<Mt900RTGS> retVal = null;
		try {
			retVal = Mt900RTGSDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Mt900RTGS findById(@PathParam("id") String id) {
		Mt900RTGS retVal = null;
		try {
			retVal = Mt900RTGSDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt900RTGS create(Mt900RTGS entity) {
    	Mt900RTGS retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = Mt900RTGSDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Mt900RTGS update(Mt900RTGS entity) {
    	Mt900RTGS retVal = null;
        try {
        	retVal = Mt900RTGSDao.merge(entity, entity.getId());
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
        	Mt900RTGSDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}