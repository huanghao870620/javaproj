package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.DeliveryAddress;
import com.xa.service.DeliveryAddressService;

@Controller
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController extends BaseController {

	
	@Autowired
	private DeliveryAddressService<DeliveryAddress> deliveryAddressService;

	/**
	 * 添加收货地址
	 * @param address
	 * @param sign
	 */
	@RequestMapping("addDeliveryAddress")
	public void addDeliveryAddress(DeliveryAddress address,String sign) {
		 try {
			this.sendAjaxMsg(this.deliveryAddressService.addDeliveryAddress(address, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
