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

import com.project.dao.TBankarskiRacunKlijentaDaoLocal;


@Path("/t_bankarski_racun_klijenta")
public class TBankarskiRacunKlijentaService {

	private static Logger log = Logger.getLogger(TBankarskiRacunKlijenta.class);

	@EJB
	private TBankarskiRacunKlijentaDaoLocal TBankarskiRacunKlijentaDao;

	@GET 
    @Produces(MediaType.APPLICATION_XML)
    public List<TBankarskiRacunKlijenta> findByAll() {
		List<TBankarskiRacunKlijenta> retVal = null;
		try {
			retVal = TBankarskiRacunKlijentaDao.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
	
	@GET 
	@Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public TBankarskiRacunKlijenta findById(@PathParam("id") String id) {
		TBankarskiRacunKlijenta retVal = null;
		try {
			retVal = TBankarskiRacunKlijentaDao.findById(Long.parseLong(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public TBankarskiRacunKlijenta create(TBankarskiRacunKlijenta entity) {
    	TBankarskiRacunKlijenta retVal = null;
		try {
			System.out.println("entity: "+entity);
			retVal = TBankarskiRacunKlijentaDao.persist(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retVal;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public TBankarskiRacunKlijenta update(TBankarskiRacunKlijenta entity) {
    	TBankarskiRacunKlijenta retVal = null;
        try {
        	retVal = TBankarskiRacunKlijentaDao.merge(entity, entity.getId());
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
        	TBankarskiRacunKlijentaDao.remove(id);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
    	return "ok";
    }
    
}
