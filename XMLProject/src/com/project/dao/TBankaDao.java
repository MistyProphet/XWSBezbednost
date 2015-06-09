package com.project.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.common_types.TBanka;

@Stateless
@Local(TBankaDaoLocal.class)
public class TBankaDao extends GenericDao<TBanka, Long> implements TBankaDaoLocal{

	public static final String contextPath = "com.project.common_types";
	
	public static final String schemaName = "t_banka";
	
	public TBankaDao() {
		super(contextPath, schemaName);
	}
	
}
