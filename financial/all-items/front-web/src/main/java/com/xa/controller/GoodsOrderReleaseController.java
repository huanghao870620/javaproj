package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.GoodsOrderRelease;
import com.xa.service.GoodsOrderReleaseService;

@Controller
@RequestMapping("/goodsOrder")
public class GoodsOrderReleaseController extends BaseController {
 
	@Autowired
	private GoodsOrderReleaseService<GoodsOrderRelease> goodsOrderReleaseService;
	
	@RequestMapping("bindingOrdersAndGoods")
	public void bindingOrdersAndGoods(Long orderId, Long []goodsId){
		 try {
			this.sendAjaxMsg(this.goodsOrderReleaseService.bindingOrdersAndGoods(orderId, goodsId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
