package com.project.mt102;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Mt102Wrapped {

	@XmlElement
	Mt102 mt102;

	public Mt102 getMt102() {
		return mt102;
	}

	public void setMt102(Mt102 mt102) {
		this.mt102 = mt102;
	}
	
}
