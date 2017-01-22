package com.xa.enumeration;
/**
 * 优惠券使用状态
 * @author burgess
 *
 */
public enum CouponsState {
	  NOT_USE(1), // 未使用
	  USED(2),  //已经使用
	  PASS_TIME(3)  //已经过期
	  ;
	
	private Integer value;
	CouponsState(Integer value) {
		this.value=value;
	}
	public Integer getValue() {
		return value;
	}
	
	
	
}
