package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.OrderRecv;
import com.xa.service.OrderRecvService;

@Controller
@RequestMapping("/orderRecv")
public class OrderRecvController extends BaseController {

	
	@Autowired
	private OrderRecvService<OrderRecv> orderRecvService;
	
	
	/**
	 * 买手接单
	 * @param buyHandId
	 * @param orderId
	 * @param sign
	 */
	@RequestMapping("recvOrder")
	public void recvOrder(Long buyHandId, Long orderId, String sign){
		try {
			this.sendAjaxMsg(this.orderRecvService.recvOrder(buyHandId, orderId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
