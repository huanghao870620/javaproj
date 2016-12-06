package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.AccountTransactionRecords;
import com.xa.service.AccountTransactionRecordsService;

@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController {

	@Autowired
	private AccountTransactionRecordsService<AccountTransactionRecords> accountTransactionRecordsService;
	
	/**
	 * 获取钱包交易记录
	 */
	@RequestMapping("getWalletRecords")
	public void getWalletRecords(Integer pageNum, Integer pageSize, String sign){
		try {
			this.sendAjaxMsg(this.accountTransactionRecordsService.getRecordsByPaging(pageNum, pageSize,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
