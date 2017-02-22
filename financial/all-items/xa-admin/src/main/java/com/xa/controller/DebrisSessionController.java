package com.xa.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.convert.DatePropertyEditor;
import com.xa.entity.DebrisSession;
import com.xa.entity.FastBuySession;
import com.xa.service.DebrisSessionService;
import com.xa.service.FastBuySessionService;
import com.xa.util.Constants;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("debrisSession")
public class DebrisSessionController extends BaseController{

	@Autowired
	private DebrisSessionService<DebrisSession> debrisSessionService;
	
	@Autowired
	private FastBuySessionService<FastBuySession> fastBuySessionService;

	
	/**
	 * 去编辑抢购时间
	 * @return
	 */
	@RequestMapping("toEditDebrisSession")
	public ModelAndView toEditDebrisSession(Long id,Long fbsId){
		ModelAndView modelAndView = new ModelAndView("debrisSession/editDebrisSession");
		this.debrisSessionService.getDebrisSession(modelAndView, id,fbsId);
		return modelAndView;
	}
	
	/**
	 * 编辑抢购时间
	 * @return
	 */
	@RequestMapping("editDebrisSession")
	public ModelAndView editDebrisSession(DebrisSession dSession,Long fbsId){
		ModelAndView modelAndView = new ModelAndView();
		this.debrisSessionService.editDebrisSession(modelAndView, dSession, fbsId);
		return modelAndView;
	}
	
	/**
	 * 去添加抢购时间
	 * @return
	 */
	@RequestMapping("toAddDebrisSession")
	public ModelAndView toAddDebrisSession(Long fbsId){
		ModelAndView modelAndView = new ModelAndView("debrisSession/addDebrisSession");
		this.fastBuySessionService.getSessionByCommonFormat(modelAndView, fbsId);
		return modelAndView;
	}
	
	/**
	 * 添加抢购时间
	 * @param dSession
	 * @param goodId
	 * @return
	 */
	@RequestMapping("addDebrisSession")
	public ModelAndView addDebrisSession(DebrisSession dSession,Long goodId,Long fbsId){
		ModelAndView modelAndView = new ModelAndView("redirect:/fastBuySession/toEditSession.htm?id="+fbsId);
		this.debrisSessionService.addDebrisSession(dSession, goodId,fbsId);
		return modelAndView;
	}
	
	
	@InitBinder
	 protected void initBinder(HttpServletRequest request,
	    ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor(Constants.DEFAULT_DATE_FORMAT));
	 }
}
