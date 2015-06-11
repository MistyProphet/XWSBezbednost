package com.project.dao;

import java.util.ArrayList;

import javax.xml.datatype.XMLGregorianCalendar;

import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;

public interface UplataDaoLocal extends GenericDaoLocal<Placanje, Long> {

	ArrayList<NalogZaPlacanje> getPresek(String brojRacuna, long rbrPreseka, XMLGregorianCalendar date);

}
