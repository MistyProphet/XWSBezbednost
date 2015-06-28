package com.project.bankaws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import misc.RESTUtil;

import com.project.banka.Banka;
import com.project.banka.RTGSProccessing;
import com.project.common_types.TBanka;
import com.project.mt102.Mt102;

public class PortHelper {
	public static int ID_Instance_Banke = 1;
    public static Banka current_bank = null;
    public static RTGSProccessing rtgsObrada = null;
    public static String checkNalogEx = "";
    public static Long mt103ID = Long.valueOf("1");
    public static Long mt102ID = Long.valueOf("1");
	public static String KEY_STORE_FILE;
	public static String KEY_STORE_PASSWORD;
	public static String TEST_FILE;
	public static String SCHEMA_PATH;
    
    static {
    	current_bank = new Banka();
    	String s = "";
    	
    	ResourceBundle bbb = ResourceBundle.getBundle ("resource.deploy"+ID_Instance_Banke);
    	SCHEMA_PATH =  (String) bbb.getObject("schema.path");
    	
    	ResourceBundle bb = ResourceBundle.getBundle ("resource.deploy"+ID_Instance_Banke);
    	TEST_FILE =  (String) bb.getObject("test.file");
        
        ResourceBundle b = ResourceBundle.getBundle ("resource.deploy"+ID_Instance_Banke);
        s =  (String) b.getObject("swift.code");

    	Long i = Long.parseLong(Integer.toString(ID_Instance_Banke));
    	TBanka podaci = new TBanka();
    	podaci.setSWIFTKod(s);
    	podaci.setId(current_bank.getId());
    	String accNum =  (String) b.getObject("account.number");
    	podaci.setBrojRacunaBanke(accNum);
    	String bName = (String) b.getObject("bank.name");
    	podaci.setNazivBanke(bName);
    	current_bank.setPodaci_o_banci(podaci);
    	current_bank.setSWIFTCode(s);
    	current_bank.setId(i);
    	KEY_STORE_FILE = (String) b.getObject("keystore.file");
    	KEY_STORE_PASSWORD = (String) b.getObject("keystore.password");
    	
    	try {
    		mt102ID = getMaxMTID(i, "MT102");
    		mt103ID = getMaxMTID(i, "MT103");
		}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	current_bank.init();
    	rtgsObrada = new RTGSProccessing();
    	ID_Instance_Banke++;
    }
    
    public static Long getMaxMTID (Long idBanke, String message) throws IOException {
    	String xQuery = "xs:integer(max(//*:ID_poruke))";
    	InputStream input = null;
		try {
			input = RESTUtil.retrieveResource(xQuery, "Banka/00"+idBanke+"/"+message, "UTF-8", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	BufferedReader br = new BufferedReader(new InputStreamReader(input));
    	Long result = new Long(0);
		String line = br.readLine();

		if (line != null) 
			result = Long.parseLong(line);
    	return result;
    }
    
    public static void main(String[] args) {
    	Mt102 mt102 = new Mt102();
    	mt102.setIDPoruke("314");
    	mt102.setSifraValute("RSD");
    	mt102.setUkupanIznos(BigDecimal.valueOf(200));
    	RESTUtil.objectToDB("Banka/001/MT102", mt102.getIDPoruke(), mt102);
    	try {
			System.out.println(getMaxMTID(Long.parseLong("1"), "MT102"));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
}