package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.common_types.TRacunKlijenta;

@Stateless
@Local(TRacunKlijentaDaoLocal.class)
public class TRacunKlijentaDao extends GenericDao<TRacunKlijenta, Long> implements TRacunKlijentaDaoLocal{

	public static final String contextPath = "com.project.common_types";
	
	public static final String schemaName = "t_racun_klijenta";
	
	public TRacunKlijentaDao() {
		super(contextPath, schemaName);
	}
	
}
