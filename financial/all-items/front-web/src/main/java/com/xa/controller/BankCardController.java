package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.BankCard;
import com.xa.service.BankCardService;

@RequestMapping("/bankcard")
@Controller
public class BankCardController extends BaseController {

	@Autowired
	private BankCardService<BankCard> bankCardService;
	
	
	/**
	 * 
	 */
	@RequestMapping("getBankCardList")
	public void getBankCardList(Long buyHandId, String sign){
		try {
			this.sendAjaxMsg(this.bankCardService.getBankCardList(buyHandId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加银行卡
	 * @param bankCard
	 * @param sign
	 */
	@RequestMapping("addBankCard")
	public void addBankCard(BankCard bankCard, String sign){
		try {
			this.sendAjaxMsg(this.bankCardService.addBankCard(bankCard, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除银行卡
	 */
	@RequestMapping("removeBankCard")
	public void removeBankCard(Long cardId, String sign) {
		try {
			this.sendAjaxMsg(this.bankCardService.removeBankCard(cardId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
