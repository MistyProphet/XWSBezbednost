package com.project.nalog_za_placanje;


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

import com.project.dao.NalogZaPlacanjeDaoLocal;


@Path("/NalogZaPlacanje")
public class NalogZaPlacanjeService {

	private static Logger log = Logger.getLogger(NalogZaPlacanje.class);

	@EJB
	private NalogZaPlacanjeDaoLocal NalogZaPlacanjeDao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<NalogZaPlacanje> findByAll() {
		List<NalogZaPlacanje> retVal = null;
		try {
			retVal = NalogZaPlacanjeDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public NalogZaPlacanje findById(@PathParam("id") String id) {
		NalogZaPlacanje retVal = null;
		try {
			retVal = NalogZaPlacanjeDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public NalogZaPlacanje create(NalogZaPlacanje entity) {
    	NalogZaPlacanje retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = NalogZaPlacanjeDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public NalogZaPlacanje update(NalogZaPlacanje entity) {
    	NalogZaPlacanje retVal = null;
        try {
        	retVal = NalogZaPlacanjeDao.merge(entity, entity.getId());
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
        	NalogZaPlacanjeDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
