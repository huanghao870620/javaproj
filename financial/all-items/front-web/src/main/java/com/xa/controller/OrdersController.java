package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Orders;
import com.xa.service.OrdersService;

@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController {

	@Autowired
	private OrdersService<Orders> ordersService;
	
	/**
	 * 获取订单
	 */
	@RequestMapping("getOrders")
	public void getOrders() {
		try {
			this.sendAjaxMsg(this.ordersService.getOrders());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 根据状态获取订单信息
	 * @param state
	 * @param sign
	 */
//	@RequestMapping("getOrdersByState")
//	public void getOrdersByState(Integer state, String sign){
//		 try {
//			this.sendAjaxMsg(this.ordersService.getOrdersByState(state, sign));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
