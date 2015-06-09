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

import com.project.dao.TRacunKlijentaDaoLocal;


@Path("/t_racun_klijenta")
public class TRacunKlijentaService {

	private static Logger log = Logger.getLogger(TRacunKlijenta.class);

	@EJB
	private TRacunKlijentaDaoLocal TRacunKlijentaDao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<TRacunKlijenta> findByAll() {
		List<TRacunKlijenta> retVal = null;
		try {
			retVal = TRacunKlijentaDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public TRacunKlijenta findById(@PathParam("id") String id) {
		TRacunKlijenta retVal = null;
		try {
			retVal = TRacunKlijentaDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public TRacunKlijenta create(TRacunKlijenta entity) {
    	TRacunKlijenta retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = TRacunKlijentaDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public TRacunKlijenta update(TRacunKlijenta entity) {
    	TRacunKlijenta retVal = null;
        try {
        	retVal = TRacunKlijentaDao.merge(entity, entity.getId());
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
        	TRacunKlijentaDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
