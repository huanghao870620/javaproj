package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.GoodsSearchRecord;
import com.xa.service.GoodsSearchRecordService;

@Controller
@RequestMapping("/gsr")
public class GoodsSearchRecordController extends BaseController {
	
	@Autowired
	private GoodsSearchRecordService<GoodsSearchRecord> goodsSearchRecordService;

	/**
	 * 
	 */
	@RequestMapping("delGSR")
	public void delGSR(Long buyerId,String sign){
		try {
			this.sendAjaxMsg(this.goodsSearchRecordService.delGSR(buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
