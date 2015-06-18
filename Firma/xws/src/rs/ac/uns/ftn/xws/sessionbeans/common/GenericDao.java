package rs.ac.uns.ftn.xws.sessionbeans.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import rs.ac.uns.ftn.xws.entities.payments.Identifiable;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

public abstract class GenericDao<T extends Identifiable, ID extends Serializable> implements GenericDaoLocal<T, ID> {
	
	protected String contextPath;
	
	protected JAXBContext context;
	
	protected EntityManagerBaseX<T, ID> em;
	
	public GenericDao(String contextPath, String schemaName) {
		
		try {
			context = JAXBContext.newInstance(contextPath);
			em = new EntityManagerBaseX<T, ID>(schemaName, contextPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public T persist(T entity) throws JAXBException, IOException {
		Long id = em.getIdentity();
		entity.setId(id);
		em.persist(entity, id);
		return entity;
	}
	
	public T findById(ID id) throws IOException, JAXBException {
		T entity;
		entity = em.find(id);
		return entity;
	}

	public InputStream findBy(String xQuery, boolean wrap) throws IOException {
		InputStream result;
		result = em.executeQuery(xQuery, wrap);
		return result;
	}
	
	public List<T> findAll() throws IOException, JAXBException {
		List<T> result;
		result = em.findAll();
		return result;
	}
	
	public void remove(ID id) throws IOException {
		em.delete(id);
	}

	public T merge(T entity, ID id) throws IOException, JAXBException {
		em.update(entity, id);
		return entity;
	}
	
} 