package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.mt900.Mt900RTGS;

@Stateless
@Local(Mt900RTGSDaoLocal.class)
public class Mt900RTGSDao extends GenericDao<Mt900RTGS, Long> implements Mt900RTGSDaoLocal{
	
	public static final String contextPath = "com.project.mt900";
	
	public static final String schemaName = "mt900rtgs";
	
	public Mt900RTGSDao(String contextPath, String schemaName) {
		super(contextPath, schemaName);
	}
}
