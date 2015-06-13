package com.project.dao;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.project.presek.Presek;
import com.project.stavka_preseka.Transakcija;

public interface TransakcijaDaoLocal extends GenericDaoLocal<Transakcija, Long> {
	
	public Presek getPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka) throws IOException, JAXBException;
	
	public List<Transakcija> getTransakcijeZaPresek(XMLGregorianCalendar date, String brojRacuna, long brPreseka) throws IOException, JAXBException;

}
