package com.xa.service;

public interface OrderRecvService<T> extends BaseServiceInte<T> {

	/**
	 * 买手接单
	 * @param buyHandId
	 * @param orderId
	 * @param sign
	 * @return
	 */
	public String recvOrder(Long buyHandId, Long orderId, String sign);
	
	/**
	 * 采购
	 * @param orderId
	 * @param sign
	 * @return
	 */
	public String procurement(Long orderId, String sign);
	
	/**
	 * 获取订单信息通过状态和买手id
	 * @param buyHandId
	 * @param state
	 * @param sign
	 * @return
	 */
	public String getAlreadyAcceptOrders(Long buyHandId,Integer state, String sign);
}
