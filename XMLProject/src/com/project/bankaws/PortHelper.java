package com.project.bankaws;

import java.util.ResourceBundle;

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
    	String s = ResourceBundle.getBundle(propFile).getString("swift.code");
    	Long i = Long.parseLong(Integer.toString(ID_Instance_Banke));
    	TBanka podaci = new TBanka();
    	podaci.setSWIFTKod(s);
    	podaci.setId(current_bank.getId());
    	podaci.setBrojRacunaBanke(ResourceBundle.getBundle(propFile).getString("account.number"));
    	podaci.setNazivBanke(ResourceBundle.getBundle(propFile).getString("bank.name"));
    	current_bank.setPodaci_o_banci(podaci);
    	current_bank.setSWIFTCode(s);
    	current_bank.setId(i);
    	
    	current_bank.init();
    	rtgsObrada = new RTGSProccessing();
    	ID_Instance_Banke++;
    }
}
