package com.project.banka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ResourceBundle;

public class PortHelper {
	public static int ID_Instance_Banke = 1;
    public static Banka current_bank = null;
    public static RTGSProccessing rtgsObrada = null;
    public static String checkNalogEx = "";
    public static Long mt103ID = Long.valueOf("1");
    public static Long mt102ID = Long.valueOf("1");
	public static String KEY_STORE_FILE;
	public static String KEY_STORE_PASSWORD;
	public static String KEY_STORE_FILE_BANKA;
	public static String KEY_STORE_PASSWORD_BANKA;
    
    static {
    	current_bank = new Banka();
    	ResourceBundle b = ResourceBundle.getBundle ("resources.deploy");
       
    	KEY_STORE_FILE = (String) b.getObject("keystore.file");
    	KEY_STORE_PASSWORD = (String) b.getObject("keystore.password");
    	KEY_STORE_FILE_BANKA = (String) b.getObject("banka1.file");
    	KEY_STORE_PASSWORD_BANKA = (String) b.getObject("banka1.password");
    	rtgsObrada = new RTGSProccessing();
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
    	mt102.setId(Long.parseLong("1"));
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
