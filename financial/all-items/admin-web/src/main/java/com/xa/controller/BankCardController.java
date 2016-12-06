package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.BankCard;
import com.xa.service.BankCardService;

@Controller
@RequestMapping("/bankCard")
public class BankCardController extends BaseController {

	@Autowired
	private BankCardService<BankCard> bankCardService;
	
	/**
	 * 获取银行卡列表
	 */
	@RequestMapping("getBankCardList")
	public void getBankCardList(Long customerId,String sign){
		try {
			this.sendAjaxMsg(this.bankCardService.getBankCardList(customerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
