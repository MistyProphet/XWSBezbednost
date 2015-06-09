package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.mt103.Mt103;

@Stateless
@Local(Mt103DaoLocal.class)
public class Mt103Dao extends GenericDao<Mt103, Long> implements Mt103DaoLocal{

	public static final String contextPath = "com.project.mt103";
	
	public static final String schemaName = "mt103";
	
	public Mt103Dao() {
		super(contextPath, schemaName);
	}
	
}
