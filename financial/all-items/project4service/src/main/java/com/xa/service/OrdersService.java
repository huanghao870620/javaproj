package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.alipay.api.AlipayApiException;
import com.xa.service.BaseServiceInte;

/**
 * 
 * @author burgess
 *
 * @param 
 */
public interface OrdersService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @return
	 */
	public String getOrders();
	
	/**
	 * 生成订单
	 * @param ids
	 * @param counts
	 * @param sign
	 * @return
	 */
	public String addOrder(String scgids,Long buyerId,  String sign);
	
	/**
	 * 获取订单中的商品
	 * @param orderId
	 * @param sign
	 * @return
	 */
	public String getSCGByOrderId(Long orderId, String sign);
	
	
	/**
	 * 微信支付回调
	 */
	public String wxNotify(HttpServletRequest request) throws IOException, DocumentException;
	
	/**
	 * 根据状态获取订单信息
	 * @param state
	 * @param sign
	 * @return
	 */
	public String getOrdersByState(Integer state, Long buyerId, String sign);
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param sign
	 * @return
	 */
	public String cancelOrder(Long orderId,String sign);
	
	/**
	 * 立即购买生成订单
	 * @param goodId
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String addOrder(Long goodId,Long buyerId,  String sign);
	
	/**
	 * 支付宝回调
	 * @param request
	 * @return
	 * @throws AlipayApiException
	 */
	public String alipayNotify(HttpServletRequest request) throws AlipayApiException;
	
	/**
	 * 选择收货方式
	 * @param orderId
	 * @param receWay
	 * @param sign
	 * @return
	 */
	public String chooseWayGoods(Long orderId,Integer receWay, String sign);
	
	/**
	 * 订单设置收货地址
	 * @param orderId
	 * @param addressId
	 * @param sign
	 * @return
	 */
	public String addTakeAddress(Long orderId, Long addressId, String sign);
	
	/**
	 * 修改订单价格
	 * @param cbId
	 * @param orderId
	 * @param sign
	 * @return
	 */
	public String updateOrderPrice(Long cbId, Long orderId, String sign);
}
