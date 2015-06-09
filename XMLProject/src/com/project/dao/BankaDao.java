package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.banka.Banka;

@Stateless
@Local(BankaDaoLocal.class)
public class BankaDao extends GenericDao<Banka, Long> implements BankaDaoLocal{
	public static final String contextPath = "com.project.banka";
	
	public static final String schemaName = "banke";
	
	public BankaDao() {
		super(contextPath, schemaName);
	}
}
