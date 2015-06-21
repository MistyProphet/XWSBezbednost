package com.project.cbws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;

import misc.RESTUtil;

import com.project.mt102.Mt102;
import com.project.nalog_za_placanje.Placanje;

import crud.RESTGet;
import crud.RESTPut;

public class CBUtil {

	private static BigDecimal limit = new BigDecimal(30000.00);

	public static boolean RTGSTransaction(String SwiftDuznika,
			String SwiftPoverioca, BigDecimal transakcija)
			throws ReceiveMT103Fault {
		BigDecimal pareDuznika;
		BigDecimal parePoverioca;
		try {
			DecimalFormat df = new DecimalFormat();
			df.setParseBigDecimal(true);
			pareDuznika = (BigDecimal) df.parse(RESTGet.run("(//kod_banke[@id='" + SwiftDuznika+ "']/stanje_racuna/text())"));
			parePoverioca = (BigDecimal) df.parse(RESTGet.run("(//kod_banke[@id='" + SwiftPoverioca+ "']/stanje_racuna/text())"));
		} catch (Exception e) {
			throw new ReceiveMT103Fault("Pogresan Swift");
		}
		
		if (pareDuznika.compareTo(limit.add(transakcija)) != -1) {
			try {
				RESTPut.run("Racuni", "//kod_banke[@id='" + SwiftDuznika+ "']/stanje_racuna",(pareDuznika.subtract(transakcija).toString()));
				RESTPut.run("Racuni", "//kod_banke[@id='" + SwiftPoverioca+ "']/stanje_racuna",(parePoverioca.add(transakcija).toString()));
			} catch (Exception e) {
				throw new ReceiveMT103Fault("Neuspesna transakcija");
			}
		}else {
			throw new ReceiveMT103Fault("Duznik nema dovoljno na racunu");
		}

		return true;

	}

	public static boolean ClearingTransaction(Mt102 mt102)
			throws ReceiveMT102Fault {
		Iterator<Placanje> listaPlacanja = mt102.getPlacanje().iterator();
		if (mt102.getPlacanje().isEmpty())
			throw new ReceiveMT102Fault("Lista uplata je prazna");

		BigDecimal bd = new BigDecimal(0);
		while (listaPlacanja.hasNext()) {
			Placanje placanje = listaPlacanja.next();
			if(!placanje.getUplata().getRacunPrimaoca().getBrojRacuna().substring(0, 3).equals(mt102.getBankaPoverioca().getBrojRacunaBanke().substring(0, 3)))
			{
				throw new ReceiveMT102Fault("Nisu sve uplate za istu banku");
			}
			
			if (placanje.getUplata().getIznos()
					.compareTo(new BigDecimal(250000)) == 1) {
				throw new ReceiveMT102Fault("Jedna uplata je prevelika");
			}
			bd = bd.add(placanje.getUplata().getIznos());
		}
		if(bd.compareTo(mt102.getUkupanIznos())!=0)
			throw new ReceiveMT102Fault("Ukupan iznos nije jednak zbiru uplata");
		try {
			return RTGSTransaction(mt102.getBankaDuznika().getSWIFTKod(), mt102
					.getBankaPoverioca().getSWIFTKod(), mt102.getUkupanIznos());
		} catch (ReceiveMT103Fault e) {
			throw new ReceiveMT102Fault(e.getMessage());
		}

	}

	public static Long getMaxTransactionID(String schemeName) throws ReceiveMT102Fault {
        String xQuery =  "xs:integer(max(//*:ID_poruke))";
    	try {
    		InputStream input = null;
                try {
                        input = RESTUtil.retrieveResource(xQuery, schemeName, "UTF-8", false);
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        Long result = new Long(0);
                String line;
			
					line = br.readLine();
				
 
                if (line != null)
                        result = Long.parseLong(line);
        System.out.println("!!!----> " + result);
    
        return result;
    	} catch (IOException e) {
			throw new ReceiveMT102Fault("");
		}
    }
	
	public static void main(String[] args) {
		try {
			CBUtil.RTGSTransaction("AAAARS01", "BBBBRS01", new BigDecimal(
					32930583.43));
		} catch (ReceiveMT103Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
