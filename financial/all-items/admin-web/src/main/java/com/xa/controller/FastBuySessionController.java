package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.FastBuySession;
import com.xa.service.FastBuySessionService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("fastBuySession")
public class FastBuySessionController extends BaseController {

	@Autowired
	private FastBuySessionService<FastBuySession> fastBuySessionService;
	
	

	/**
	 * 获取所有秒杀专场
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getFBS")
	public void getFBS(String random, String sign){
		 try {
			this.sendAjaxMsg(this.fastBuySessionService.getFBS(random, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 获取秒杀专场详情
	 * @param fsbId
	 * @param sign
	 */
	@RequestMapping("getFBSDetail")
	public void getFBSDetail(Long fsbId,String sign){
		try {
			this.sendAjaxMsg(this.fastBuySessionService.getFBSDetail(fsbId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
