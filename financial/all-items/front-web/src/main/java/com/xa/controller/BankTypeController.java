package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.BankType;
import com.xa.service.BankTypeService;

@Controller
@RequestMapping("/bankType")
public class BankTypeController extends BaseController {

	@Autowired
	private BankTypeService<BankType> bankTypeService;
	
	/**
	 * 获取所有银行类型
	 */
	@RequestMapping("getAllBank")
	public void getAllBank(String sign){
		try {
			this.sendAjaxMsg(this.bankTypeService.getAllBank(sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
