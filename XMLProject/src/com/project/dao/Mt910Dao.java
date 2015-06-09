package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.mt910.Mt910;

@Stateless
@Local(Mt910DaoLocal.class)
public class Mt910Dao extends GenericDao<Mt910, Long> implements Mt910DaoLocal{

	public static final String contextPath = "com.project.mt910";
	
	public static final String schemaName = "mt910";
	
	public Mt910Dao() {
		super(contextPath, schemaName);
	}
	
}
