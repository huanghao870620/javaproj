package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Integral;
import com.xa.service.IntegralService;

@Controller
@RequestMapping("/integral")
public class IntegralController extends BaseController {

	@Autowired
	private IntegralService<Integral> integralService;
	

	@RequestMapping("getIntegralByBuyerId")
	public void getIntegralByBuyerId(Long buyerId,String sign){
		 try {
			this.sendAjaxMsg(this.integralService.getIntegralByBuyerId(buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 积分兑换优惠券
	 * @param couponsId
	 * @param buyerId
	 * @param sign
	 */
	@RequestMapping("exchangeCoupons")
	public void exchangeCoupons(Long couponsId,Long buyerId,String sign){
		  try {
			this.sendAjaxMsg(this.integralService.exchangeCoupons(couponsId, buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 获取兑换记录
	 * @param buyerId
	 * @param sign
	 */
	@RequestMapping("getExchangeHistory")
	public void getExchangeHistory(Long buyerId, String sign){
		 try {
			this.sendAjaxMsg(this.integralService.getExchangeHistory(buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
