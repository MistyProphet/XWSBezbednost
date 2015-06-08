package com.project.dao;

import java.io.IOException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import com.project.common_types.TBanka;

@Stateless
@Local(TBankaDaoLocal.class)
public class TBankaDao extends GenericDao<TBanka, Long> implements TBankaDaoLocal{

	public static final String contextPath = "com.project.dao";
	
	public static final String schemaName = "TBanka";
	
	public TBankaDao() {
		super(contextPath, schemaName);
	}

	@Override
	public TBanka getBankByName(String name) throws IOException, JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

}
