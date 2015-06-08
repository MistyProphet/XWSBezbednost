package com.project.dao;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.project.common_types.TBanka;

public interface TBankaDaoLocal extends GenericDaoLocal<TBanka, Long>{
	
	public TBanka findTBankaByName(String name) throws IOException, JAXBException;
	
	public TBanka findTBankaByCode(String code) throws IOException, JAXBException;
}
