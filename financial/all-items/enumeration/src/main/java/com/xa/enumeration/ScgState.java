package com.xa.enumeration;
/**
 * 
 * @author burgess
 *
 */
public enum ScgState {
	
	NOT_IN_ORDER(0), //未生成订单
	IN_ORDER(1)  //已经生成订单
	;

	private int value;
	
	ScgState(int value){
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	
	
}
