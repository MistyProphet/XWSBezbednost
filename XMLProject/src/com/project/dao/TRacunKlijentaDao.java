package com.project.dao;

import com.project.common_types.TRacunKlijenta;

public class TRacunKlijentaDao extends GenericDao<TRacunKlijenta, Long> implements TRacunKlijentaDaoLocal {

	public static final String contextPath = "com.project.dao";
	
	public static final String schemaName = "TRacunKlijenta";
	
	public TRacunKlijentaDao() {
		super(contextPath, schemaName);
	}
}