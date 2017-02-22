package com.xa.enumeration;

public enum AOrFType {
    ACTIVTY(1), //活动
    SESSION(2)  //专场
    ;
	
	
	private Integer  value;	 
	
	AOrFType(Integer value){
		this.value=value;
	}

	public Integer getValue() {
		return value;
	}

	
	
	
}
