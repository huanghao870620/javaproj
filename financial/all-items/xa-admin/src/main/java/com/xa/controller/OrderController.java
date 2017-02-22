package com.xa.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.xa.convert.DatePropertyEditor;
import com.xa.entity.Area;
import com.xa.entity.Orders;
import com.xa.service.AreaService;
import com.xa.service.OrdersService;
import com.xa.util.Constants;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private OrdersService<Orders> ordersService;
	
	@Autowired
	private AreaService<Area> areaService;


	
	/**
	 * 获取订单
	 * @param page
	 * @param rows
	 */
	@RequestMapping("getOrders")
	public void getOrders(Integer stateS,
			Integer receWayS,
			String nameS, 
			Date startTime,
			Date endTime,
			Integer page,
			Integer rows){
		try {
			this.sendAjaxMsg(
					this.ordersService.getOrders(
							stateS,
							receWayS,
							nameS,
							startTime,
							endTime, 
							page, 
							rows,
							this.areaService
							));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到订单列表页面
	 * @return
	 */
	@RequestMapping("toListOrder")
	public ModelAndView toListOrder(){
		ModelAndView modelAndView = new ModelAndView("order/orderList");
		return modelAndView;
	}
	
	/**
	 * 查看订单中的商品
	 * @return
	 */
	@RequestMapping("toShowOrderGoods")
	public ModelAndView toShowOrderGoods(Long id){
		 ModelAndView modelAndView = new ModelAndView("order/orderGoods");
		 this.ordersService.getOrderById(id, modelAndView,this.areaService);
		 return modelAndView;
	}
	

	/**
	 * 获取订单中的商品
	 * @param orderId
	 */
	@RequestMapping("getGoodsForOrderId")
	public void getGoodsForOrderId(Long orderId){
		try {
			this.sendAjaxMsg(this.ordersService.getGoodsForOrderId(orderId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
