package com.project.cbws;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.project.mt102.Mt102;

import crud.RESTGet;
import crud.RESTPut;

public class CBUtil {

	private static BigDecimal limit = new BigDecimal(30000.00);
	
	public static boolean RTGSTransaction(String SwiftDuznika,String SwiftPoverioca, BigDecimal transakcija){
		BigDecimal pareDuznika;
		BigDecimal parePoverioca;
		try {
			DecimalFormat df = new DecimalFormat();
			df.setParseBigDecimal(true);
			 pareDuznika = (BigDecimal) df.parse( RESTGet.run("(//kod_banke[@id='"+SwiftDuznika+"']/stanje_racuna/text())"));
			 parePoverioca = (BigDecimal) df.parse(RESTGet.run("(//kod_banke[@id='"+SwiftPoverioca+"']/stanje_racuna/text())"));
			 if (pareDuznika.compareTo(limit)!=-1){
				 RESTPut.run("Racuni","//kod_banke[@id='"+SwiftDuznika+"']/stanje_racuna",(pareDuznika.subtract(transakcija).toString()));
				 RESTPut.run("Racuni","//kod_banke[@id='"+SwiftPoverioca+"']/stanje_racuna",(parePoverioca.add(transakcija).toString()));
			 }
			 else return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static boolean ClearingTransaction(Mt102 mt102) {
		//mt102.get
		return false;
	}
	
	public static void main(String[] args) {
		CBUtil.RTGSTransaction("AAAARS01", "BBBBRS01", new BigDecimal(432.43));
	}

}
