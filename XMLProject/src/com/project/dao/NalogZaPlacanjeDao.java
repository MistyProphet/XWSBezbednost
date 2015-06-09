package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.nalog_za_placanje.NalogZaPlacanje;

@Stateless
@Local(NalogZaPlacanjeDaoLocal.class)
public class NalogZaPlacanjeDao extends GenericDao<NalogZaPlacanje, Long> implements NalogZaPlacanjeDaoLocal{

	public static final String contextPath = "com.project.nalog_za_placanje";
	
	public static final String schemaName = "nalog_za_placanje";
	
	public NalogZaPlacanjeDao() {
		super(contextPath, schemaName);
	}
	
}
