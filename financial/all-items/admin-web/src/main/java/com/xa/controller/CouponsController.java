package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Coupons;
import com.xa.service.CouponsService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/coupons")
public class CouponsController extends BaseController {

	@Autowired
	private CouponsService<Coupons> couponsService;
	

	/**
	 * 获取兑换优惠券
	 */
	@RequestMapping("getCouponsByExchange")
	public void getCouponsByExchange(String random,String sign){
		try {
			this.sendAjaxMsg(this.couponsService.getCouponsByExchange(random,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
