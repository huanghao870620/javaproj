package com.xa.enumeration;
/**
 * 訂單狀態
 * @author burgess
 *
 */
public enum OrdersState {
	
	HAVE_ORDER(1),  //已接單
	HAS_BEEN_SHIPPED(2), // 已發貨 
	HAS_BEEN_COMPLETED(3), // 已完成
	HAS_BEEN_CANCELLED(4)// 已取消
	;
	
	private int value;
	
	OrdersState(int value){
		  this.value = value;
	}

	public int getValue() {
		return value;
	} 
	
}
