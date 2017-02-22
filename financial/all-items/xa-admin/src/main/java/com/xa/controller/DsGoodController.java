package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.DsGood;
import com.xa.service.DsGoodService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/dsGood")
public class DsGoodController extends BaseController {

	@Autowired
	private DsGoodService<DsGood> dsGoodService;
	
	/**
	 * 获取此时间下的商品
	 * @param dsId
	 */
	@RequestMapping("getGoodsByDsSessionId")
	public void getGoodsByDsSessionId(Long dsId){
		 try {
			this.sendAjaxMsg(this.dsGoodService.getGoodsByDsSessionId(dsId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 去编辑秒杀商品
	 * @return
	 */
	@RequestMapping("toEditDsGood")
	public ModelAndView toEditDsGood(Long id,Long fbsId){
		ModelAndView modelAndView = new ModelAndView("dsGood/editDsGood");
		this.dsGoodService.getDsGood(id, modelAndView);
		modelAndView.addObject("fbsId", fbsId);
		return modelAndView;
	}
	
	/**
	 * 编辑秒杀商品
	 * @param dsGood
	 */
	@RequestMapping("editDsGood")
	public ModelAndView editDsGood(DsGood dsGood,Long dsId,Long fbsId){
		ModelAndView modelAndView = new ModelAndView("redirect:/debrisSession/toEditDebrisSession.htm?id="+dsId+"&&fbsId="+fbsId);
		this.dsGoodService.editDsGood(dsGood);
		return modelAndView;
	}
	
}
