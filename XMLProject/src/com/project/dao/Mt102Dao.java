package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.mt102.Mt102;

@Stateless
@Local(Mt102DaoLocal.class)
public class Mt102Dao extends GenericDao<Mt102, Long> implements Mt102DaoLocal{

	public static final String contextPath = "com.project.mt102";
	
	public static final String schemaName = "mt102";
	
	public Mt102Dao() {
		super(contextPath, schemaName);
	}
	
}
