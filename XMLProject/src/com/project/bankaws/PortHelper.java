package com.project.bankaws;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.project.banka.Banka;
import com.project.banka.RTGSProccessing;
import com.project.common_types.TBanka;

public class PortHelper {
	public static int ID_Instance_Banke = 1;
    public static Banka current_bank = null;
    public static RTGSProccessing rtgsObrada = null;
    public static String checkNalogEx = "";
    public static Integer mt103ID = 1;
    public static Integer mt102ID = 1;
    
    static {
    	current_bank = new Banka();
    	String propFile = "deploy"+ID_Instance_Banke;
    	Properties properties = new Properties();
    	String s = "";
	    try {
	      properties.load(new FileInputStream(propFile+".properties"));
	      s = properties.getProperty("swift.code");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
    	Long i = Long.parseLong(Integer.toString(ID_Instance_Banke));
    	TBanka podaci = new TBanka();
    	podaci.setSWIFTKod(s);
    	podaci.setId(current_bank.getId());
    	String accNum =  properties.getProperty("account.number");
    	podaci.setBrojRacunaBanke(accNum);
    	String bName = properties.getProperty("bank.name");
    	podaci.setNazivBanke(bName);
    	current_bank.setPodaci_o_banci(podaci);
    	current_bank.setSWIFTCode(s);
    	current_bank.setId(i);
    	
    	current_bank.init();
    	rtgsObrada = new RTGSProccessing();
    	ID_Instance_Banke++;
    }
}
