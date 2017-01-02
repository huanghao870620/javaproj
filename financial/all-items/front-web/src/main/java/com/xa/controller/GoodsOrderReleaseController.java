package com.xa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goodsOrder")
public class GoodsOrderReleaseController extends BaseController {
 
//	@Autowired
//	private GoodsOrderReleaseService<GoodsOrderRelease> goodsOrderReleaseService;
//	
//	@RequestMapping("bindingOrdersAndGoods")
//	public void bindingOrdersAndGoods(Long orderId, Long []goodsId){
//		 try {
//			this.sendAjaxMsg(this.goodsOrderReleaseService.bindingOrdersAndGoods(orderId, goodsId));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
