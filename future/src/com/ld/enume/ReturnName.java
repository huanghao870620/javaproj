package com.ld.enume;
/**
 * 
 * @author huang.hao
 *
 */
public enum ReturnName {

	TOINDEX("to_back_index") // ��̨��ҳ
	
	;
	
	private final String value;
	
	ReturnName(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
}
