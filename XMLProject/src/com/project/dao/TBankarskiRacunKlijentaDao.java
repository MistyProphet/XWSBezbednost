package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.common_types.TBankarskiRacunKlijenta;

@Stateless
@Local(TBankarskiRacunKlijentaDaoLocal.class)
public class TBankarskiRacunKlijentaDao extends GenericDao<TBankarskiRacunKlijenta, Long> implements TBankarskiRacunKlijentaDaoLocal{

	public static final String contextPath = "com.project.common_types";
	
	public static final String schemaName = "t_bankarski_racun_klijenta";
	
	public TBankarskiRacunKlijentaDao() {
		super(contextPath, schemaName);
	}
	
}
