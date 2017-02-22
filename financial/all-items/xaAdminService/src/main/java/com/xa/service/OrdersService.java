package com.xa.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.xa.entity.Area;
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
	public String getOrders(Integer stateS,
			Integer receWayS,
			String nameS,
			Date startTime,
			Date endTime,
			Integer pageNum, 
			Integer pageSize,
			AreaService<Area> areaService);
	
	
	
	
	/**
	 * 获取订单中的商品
	 * @param orderId
	 * @return
	 */
	public String getGoodsForOrderId(Long orderId);
	
	/**
	 * 获取订单详情
	 * @param orderId
	 * @param modelAndView
	 */
	public void getOrderById(Long orderId,ModelAndView modelAndView,AreaService<Area> areaService);
}
