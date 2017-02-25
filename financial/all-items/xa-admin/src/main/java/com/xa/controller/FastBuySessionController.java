package com.xa.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.convert.DatePropertyEditor;
import com.xa.entity.FastBuySession;
import com.xa.entity.File;
import com.xa.service.FastBuySessionService;
import com.xa.service.FileService;
import com.xa.util.Constants;

@Controller
@RequestMapping("/fastBuySession")
public class FastBuySessionController extends BaseController {

	@Autowired
	private FastBuySessionService<FastBuySession> fastBuySessionService;
	
	@Autowired
	private FileService<File> fileService;
	
	/**
	 * 跳转到添加秒杀专场页面
	 * @return
	 */
	@RequestMapping("toAddFastBuySession")
	public ModelAndView toAddFastBuySession(){
		ModelAndView modelAndView = new ModelAndView("fastBuySession/addFastBuySession");
		return modelAndView;
	}
	
	/**
	 * 添加秒杀专场
	 * @return
	 */
	@RequestMapping("addFastBuySession")
	public ModelAndView addFastBuySession(FastBuySession fbSession,Long []goodId,Date[]cronDate,
			@RequestParam(value = "imgAdvFile", required = false)MultipartFile imgAdvFile,
			Integer inventory){
		ModelAndView modelAndView = new ModelAndView("redirect:toListSession.htm");
		try {
			fastBuySessionService.addFastBuySession(fbSession,
					goodId, 
					cronDate,
					imgAdvFile,
					inventory,
					fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * 跳转到专场列表页面
	 * @return
	 */
	@RequestMapping("toListSession")
	public ModelAndView toListSession(){
		ModelAndView modelAndView = new ModelAndView("fastBuySession/fastBuySessionList");
		return modelAndView;
	}
	
	/**
	 * 获取所有专场
	 */
	@RequestMapping("getFbss")
	public void getFbss(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.fastBuySessionService.getAllSession(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到编辑专场页面
	 * @return
	 */
	@RequestMapping("toEditSession")
	public ModelAndView toEditSession(Long id){
		ModelAndView modelAndView = new ModelAndView("fastBuySession/editFastBuySession");
		this.fastBuySessionService.getSession(modelAndView, id);
		return modelAndView;
	}
	
	/**
	 * 编辑专场
	 * @return
	 */
	@RequestMapping("editSession")
	public ModelAndView editSession(FastBuySession fbSession,
			@RequestParam(value = "imgAdvFile", required = false)MultipartFile imgAdvFile){
		ModelAndView modelAndView = new ModelAndView("redirect:toListSession.htm");
		try {
			this.fastBuySessionService.editSession(fbSession, imgAdvFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return modelAndView;
	}
	

	/**
	 * 获取专场时间
	 * @param id
	 */
	@RequestMapping("getTimeBySessionId")
	public void getTimeBySessionId(Long id){
		 try {
			this.sendAjaxMsg(this.fastBuySessionService.getTimeBySessionId(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除专场
	 * @param fbsId
	 */
	@RequestMapping("delSession")
	public void delSession(Long fbsId){
		try {
			this.sendAjaxMsg(this.fastBuySessionService.delSession(fbsId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@InitBinder
	 protected void initBinder(HttpServletRequest request,
	    ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor(Constants.DEFAULT_DATE_FORMAT));
	 }
}
