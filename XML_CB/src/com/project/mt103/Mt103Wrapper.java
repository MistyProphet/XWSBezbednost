package com.project.mt103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Mt103Wrapper {

	
	@XmlElement
	Mt103 mt103;

	public Mt103 getMt103() {
		return mt103;
	}

	public void setMt103(Mt103 mt103) {
		this.mt103 = mt103;
	}
	
}

