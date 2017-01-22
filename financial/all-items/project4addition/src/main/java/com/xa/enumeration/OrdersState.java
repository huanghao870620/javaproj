package com.xa.enumeration;
/**
 * 訂單狀態
 * @author burgess
 *
 */
public enum OrdersState {
	
	FOR_PAYMENT(1),//待付款
	WAIT_ORDER(2), //待接单
	WAIT_PURCHASING(3),  //待采购
	WAIT_DELIVERY(4), // 待发货
	WAIT_RECEIVING(5), // 待收货
	COMPLETE(6), //完成订单
	HAS_BEEN_CANCELLED(7)// 已取消
	;
	
	private int value;
	
	OrdersState(int value){
		  this.value = value;
	}

	public int getValue() {
		return value;
	} 
	
}
