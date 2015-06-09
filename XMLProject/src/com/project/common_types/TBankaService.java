package com.project.common_types;


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

import com.project.dao.TBankaDaoLocal;


@Path("/t_banka")
public class TBankaService {

	private static Logger log = Logger.getLogger(TBankaService.class);

	@EJB
	private TBankaDaoLocal TBankaDao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<TBanka> findByAll() {
		List<TBanka> retVal = null;
		try {
			retVal = TBankaDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public TBanka findById(@PathParam("id") String id) {
		TBanka retVal = null;
		try {
			retVal = TBankaDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public TBanka create(TBanka entity) {
    	TBanka retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = TBankaDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public TBanka update(TBanka entity) {
    	TBanka retVal = null;
        try {
        	retVal = TBankaDao.merge(entity, entity.getId());
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
        	TBankaDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
