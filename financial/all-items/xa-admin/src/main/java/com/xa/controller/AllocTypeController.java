package com.xa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.AllocType;
import com.xa.service.AllocTypeService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/allocType")
public class AllocTypeController extends BaseController {

	@Autowired
	private AllocTypeService<AllocType> allocTypeService;
	
	/**
	 * 添加分配类型
	 * @return
	 */
	@RequestMapping("toAddAllocType")
	public ModelAndView toAddAllocType(){
		 ModelAndView modelAndView = new ModelAndView("coupons/allocType/addAllocType");
		 return modelAndView;
	}
	
	/**
	 * 添加分配类型
	 * @return
	 */
	@RequestMapping("addAllocType")
	public ModelAndView addAllocType(AllocType allocType){
		ModelAndView modelAndView = new ModelAndView("redirect:toListAllocType.htm");
		this.allocTypeService.addAllocType(allocType);
		return modelAndView;
	}
	
	/**
	 * 去编辑分配类型
	 * @return
	 */
	@RequestMapping("toEditAllocType")
	public ModelAndView toEditAllocType(Long id){
		ModelAndView modelAndView = new ModelAndView("coupons/allocType/editAllocType");
		this.allocTypeService.getAllocType(modelAndView, id);
		return modelAndView;
	}
	
	/**
	 * 编辑分配类型
	 * @return
	 */
	@RequestMapping("editAllocType")
	public ModelAndView editAllocType(AllocType allocType){
		ModelAndView modelAndView = new ModelAndView();
		this.allocTypeService.updateAllocType(allocType);
		return modelAndView;
	}
	
	/**
	 * 分配类型列表
	 * @return
	 */
	@RequestMapping("toListAllocType")
	public ModelAndView toListAllocType(){
		ModelAndView modelAndView = new ModelAndView("coupons/allocType/allocTypeList");
		return modelAndView;
	}
	
	/**
	 * 
	 */
	@RequestMapping("listAllocType")
	public void listAllocType(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.allocTypeService.listAllocType(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除分配类型
	 * @param id
	 */
	@RequestMapping("delAllocType")
	public void delAllocType(Long id){
		try {
			this.sendAjaxMsg(this.allocTypeService.delAllocType(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
