package com.project.banka;

import com.project.bankaws.BankaPortImpl;
import com.project.bankaws.ClearingFault;

public class Clearing {

	private BankaPortImpl bankaPort = null;
    private Integer mt102ID = 1;
    
    public Clearing(BankaPortImpl banka){
    	bankaPort = banka;
    }

}
