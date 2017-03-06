package com.xa.enumeration;
/**
 * 分配优惠券类型
 * @author burgess
 *
 */
public enum AllocCouponType {

	REG(1L), /*注册时*/
	EXCHANGE(2L) /*兑换优惠券*/
	;
	
	private Long value;
	AllocCouponType(Long value){
		this.value =value;
	}
	public Long getValue() {
		return value;
	}
	
	
	
}
