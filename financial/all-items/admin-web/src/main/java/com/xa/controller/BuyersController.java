package com.xa.controller;

import java.io.IOException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.BuyHand;
import com.xa.entity.Buyers;
import com.xa.service.BuyHandService;
import com.xa.service.BuyersService;

@Controller
@RequestMapping("/buyers")
public class BuyersController extends BaseController {

	@Autowired
	private BuyersService<Buyers> buyersService;
	
	@Autowired
	private BuyHandService<BuyHand> buyHandService;

	/**
	 * 买家注册
	 * @param buyer
	 * @param sign
	 */
	@RequestMapping("register")
	public void register(Buyers buyer, String vercode,String sign){
		 try {
			this.sendAjaxMsg(this.buyersService.register(buyer,vercode, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param buyers
	 * @param sign
	 */
	@RequestMapping("getVercode")
	public void getVercode(Buyers buyers, String sign){
		try {
			this.sendAjaxMsg(this.buyersService.getVercode(buyers, sign));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户登录 
	 */
	@RequestMapping("login")
	public void login(Buyers buyers,String sign) {
		try {
			this.sendAjaxMsg(this.buyersService.login(buyers, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更换手机号
	 * @param vercode
	 * @param mobile
	 * @param buyerId
	 * @param sign
	 * @param buyHandService
	 */
	public void changeMobile(String vercode, String mobile,Long buyerId, String sign){
		try {
			this.sendAjaxMsg(this.buyersService.changeMobile(vercode, mobile, buyerId, sign, this.buyHandService));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
