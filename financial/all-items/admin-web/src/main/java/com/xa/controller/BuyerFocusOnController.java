package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.BuyerFocusOn;
import com.xa.service.BuyerFocusOnService;

@Controller
@RequestMapping("/buyerFocusOn")
public class BuyerFocusOnController extends BaseController {

	@Autowired
	private BuyerFocusOnService<BuyerFocusOn> buyerFocusOnService;
	

	/**
	 * 添加关注
	 * @param buyerFocusOn
	 * @param sign
	 */
	@RequestMapping("addBuyerFocusOn")
	public void addBuyerFocusOn(BuyerFocusOn buyerFocusOn,String sign){
		 try {
			this.sendAjaxMsg(this.buyerFocusOnService.addBuyerFocusOn(buyerFocusOn, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
