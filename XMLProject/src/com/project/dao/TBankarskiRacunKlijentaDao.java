package com.project.dao;

import com.project.common_types.TBankarskiRacunKlijenta;

public class TBankarskiRacunKlijentaDao extends GenericDao<TBankarskiRacunKlijenta, Long> implements TBankarskiRacunKlijentaDaoLocal{

	public static final String contextPath = "com.project.dao";
	
	public static final String schemaName = "TBankarskiRacunKlijenta";
	
	public TBankarskiRacunKlijentaDao() {
		super(contextPath, schemaName);
	}
}
