package com.project.banka;

import java.math.BigDecimal;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;

import com.project.common_types.TRacunKlijenta;
import com.project.exceptions.WrongBankSWIFTCodeException;
import com.project.exceptions.WrongOverallSumException;
import com.project.mt102.Mt102;
import com.project.nalog_za_placanje.NalogZaPlacanje;
import com.project.nalog_za_placanje.Placanje;
import com.project.util.Util;

public class Banka {
	
	private String SWIFTCode;
	private ArrayList<TRacunKlijenta> accounts;
	//Key - naziv banke za koju je namenjen niz naloga
	private HashMap<String,ArrayList<NalogZaPlacanje>> naloziZaClearing;
	
	public void checkMT102(Mt102 mt102) throws Exception {
			checkMT102SWIFTCode(mt102);
			checkOverallMT102Sum(mt102);
	}
	
	public void checkMT102SWIFTCode(Mt102 mt102) throws WrongBankSWIFTCodeException {
		if (!mt102.getBankaPoverioca().getSWIFTKod().trim().equals(SWIFTCode.trim())) {
			throw new WrongBankSWIFTCodeException();
		}
	}
	
	public void checkOverallMT102Sum(Mt102 mt102) throws WrongOverallSumException {
		BigDecimal sum = new BigDecimal(0);
		
		for (Placanje p : mt102.getPlacanje()) {
			sum.add(p.getUplata().getIznos());
		}
		
		if (!sum.equals(mt102.getUkupanIznos())) {
			throw new WrongOverallSumException();
		}
	}
	
	public void Clearing() {
	
		Mt102 mt102 = null;
		for (String banka : naloziZaClearing.keySet()) {
			mt102 = new Mt102();
			try {
				mt102.setDatum(Util.getXMLGregorianCalendarNow());
				mt102.setDatumValute(Util.getXMLGregorianCalendarNow());
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			for (NalogZaPlacanje nalog : naloziZaClearing.get(banka)) {
				mt102.getPlacanje().add(nalog.getPlacanje());
			}
			
		}
	}

	
}
