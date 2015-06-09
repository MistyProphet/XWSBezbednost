package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.mt900.Mt900Clearing;

@Stateless
@Local(Mt900ClearingDaoLocal.class)
public class Mt900ClearingDao extends GenericDao<Mt900Clearing, Long> implements Mt900ClearingDaoLocal{
	
	public static final String contextPath = "com.project.mt900";
	
	public static final String schemaName = "mt900clearing";
	
	public Mt900ClearingDao(String contextPath, String schemaName) {
		super(contextPath, schemaName);
	}

}
