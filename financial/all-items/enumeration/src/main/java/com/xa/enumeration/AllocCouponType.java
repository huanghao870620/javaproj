package com.xa.enumeration;
/**
 * 分配优惠券类型
 * @author burgess
 *
 */
public enum AllocCouponType {

	REG(1) /*注册时*/
	
	;
	
	private Integer value;
	AllocCouponType(Integer value){
		this.value =value;
	}
	public Integer getValue() {
		return value;
	}
	
	
	
}
