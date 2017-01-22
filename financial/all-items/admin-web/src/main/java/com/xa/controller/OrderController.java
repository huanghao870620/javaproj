package com.xa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.xa.entity.Orders;
import com.xa.service.OrdersService;
import com.xa.util.Constants;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private OrdersService<Orders> ordersService;

	/**
	 * 生成订单
	 * @param ids
	 * @param counts
	 * @param sign
	 */
	@RequestMapping("addOrder")
	public void addOrder(String scgids,Long buyerId, String counts, String sign){
		try {
			this.sendAjaxMsg(this.ordersService.addOrder(scgids,buyerId,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 获取订单中的商品
	 * @param orderId
	 * @param sign
	 */
	@RequestMapping("getSCGByOrderId")
	public void getSCGByOrderId(Long orderId, String sign){
		 try {
			this.sendAjaxMsg(this.ordersService.getSCGByOrderId(orderId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信回调
	 */
	@RequestMapping("wxNotify")
	public void wxNotify(){
			try {
				this.sendAjaxMsg(this.ordersService.wxNotify(request));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 根据状态获取订单信息
	 * @param state
	 * @param sign
	 */
	@RequestMapping("getOrdersByState")
	public void getOrdersByState(Integer state,Long buyerId, String sign){
		 try {
			this.sendAjaxMsg(this.ordersService.getOrdersByState(state,buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param sign
	 */
	@RequestMapping("cancelOrder")
	public void cancelOrder(Long orderId,String sign){
		try {
			this.sendAjaxMsg(this.ordersService.cancelOrder(orderId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 立即购买生成订单
	 * @param goodId
	 * @param buyerId
	 * @param sign
	 */
	@RequestMapping("addOrderImm")
	public void addOrder(Long goodId,Long buyerId,  String sign){
		try {
			this.sendAjaxMsg(this.ordersService.addOrder(goodId, buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 支付宝回调
	 * @param request
	 */
	@RequestMapping("alipayNotify")
	public void alipayNotify(HttpServletRequest request) {
		try {
			this.sendAjaxMsg(this.ordersService.alipayNotify(request));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择收货方式  1-自提  2-地址
	 * @param orderId
	 * @param receWay
	 * @param sign
	 */
	@RequestMapping("chooseWayGoods")
	public void chooseWayGoods(Long orderId,Integer receWay, String sign){
		 try {
			this.sendAjaxMsg(this.ordersService.chooseWayGoods(orderId, receWay, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 订单设置收货地址
	 * @param orderId
	 * @param addressId
	 * @param sign
	 */
	@RequestMapping("addTakeAddress")
	public void addTakeAddress(Long orderId, Long addressId, String sign){
		try {
			this.sendAjaxMsg(this.ordersService.addTakeAddress(orderId, addressId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改订单价格
	 * @param cbId
	 * @param orderId
	 * @param sign
	 */
	@RequestMapping("updateOrderPrice")
	public void updateOrderPrice(Long cbId, Long orderId, String sign){
		try {
			this.sendAjaxMsg(this.ordersService.updateOrderPrice(cbId, orderId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
