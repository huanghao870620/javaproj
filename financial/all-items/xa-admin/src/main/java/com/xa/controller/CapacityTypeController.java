package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.CapacityType;
import com.xa.service.CapacityTypeService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/capacityType")
public class CapacityTypeController extends BaseController{

	@Autowired
	private CapacityTypeService<CapacityType> capacityTypeService;
	
	
	/**
	 * 跳转到添加容量类型页面
	 * @return
	 */
	@RequestMapping("toAddCapacityType")
	public ModelAndView toAddCapacityType(){
		ModelAndView modelAndView = new ModelAndView("capacityType/addCapacityType");
		return modelAndView;
	}
	
	/**
	 * 添加类型
	 * @return
	 */
	@RequestMapping("addCapacityType")
	public ModelAndView addCapacityType(CapacityType capacityType){
		 ModelAndView modelAndView = new ModelAndView("redirect:toListCapacityType.htm");
		 this.capacityTypeService.addCapacityType(capacityType);
		 return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("toListCapacityType")
	public ModelAndView toListCapacityType(){
		 ModelAndView modelAndView = new ModelAndView("capacityType/capacityTypeList");
		 return modelAndView;
	}
	
	/**
	 * 类型列表
	 */
	@RequestMapping("listCapacityType")
	public void listCapacityType(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.capacityTypeService.getCapacityTypes(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
