package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.CouponsBuyer;
import com.xa.service.CouponsBuyerService;

@Controller
@RequestMapping("/couponsBuyer")
public class CouponsBuyerController extends BaseController {

	@Autowired
	private CouponsBuyerService<CouponsBuyer> couponsBuyerService;

	/**
	 * 获取优惠券通过状态
	 * @param buyerId
	 * @param state
	 * @param sign
	 */
	@RequestMapping("getCouponsByState")
	public void getCouponsByState(Long buyerId, Integer state,Integer pageNum, Integer pageSize, String sign){
			try {
				this.sendAjaxMsg(this.couponsBuyerService.getCouponsByState(buyerId, state,pageNum,pageSize, sign));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
