package com.xa.enumeration;
/**
 * 订单来源
 * @author admin
 *
 */
public enum SrcState {

	SECOND_SESSION(1),/*秒杀专场*/
	COMMON_BUY(2)  /*普通购买*/
	; 
	
	private Integer value;
	SrcState(Integer value){
		this.value=value;
	}
	public Integer getValue() {
		return value;
	}
}
