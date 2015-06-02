package com.project.banka;

import com.project.mt102.Mt102;

public class Banka {
	
	private String SWIFTCode;
	
	public boolean checkMT102Receive(Mt102 mt102) throws Exception {
		if (checkMT102SWIFTCodeReceive(mt102)) {
			throw new Exception("Error");
		}
		return false;
	}
	
	public boolean checkMT102Send(Mt102 mt102)  throws Exception {
		if (checkMT102SWIFTCodeSend(mt102)) {
			throw new Exception("Error");
		}
		return false;
	}
	
	public boolean checkMT102SWIFTCodeReceive(Mt102 mt102) {
		if (mt102.getSWIFTKodBankePoverioca().trim().equals(SWIFTCode.trim())) {
			return true;
		}
		return false;
	}
	
	public boolean checkMT102SWIFTCodeSend(Mt102 mt102) {
		if (mt102.getSWIFTKodBankeDuznika().trim().equals(SWIFTCode.trim())) {
			return true;
		}
		return false;
	}

}
