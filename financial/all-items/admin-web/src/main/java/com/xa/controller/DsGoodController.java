package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.DsGood;
import com.xa.service.DsGoodService;

@Controller
@RequestMapping("/dsGood")
public class DsGoodController extends BaseController {

	@Autowired
	private DsGoodService<DsGood> dsGoodService;

	/**
	 * 获取商品库存
	 * @param dgId
	 * @param sign
	 */
	@RequestMapping("getDsGood")
	public void getDsGood(Long dgId, String sign){
		try {
			this.sendAjaxMsg(this.dsGoodService.getDsGood(dgId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
