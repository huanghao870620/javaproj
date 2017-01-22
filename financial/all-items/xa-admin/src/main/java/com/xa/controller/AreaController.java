package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Area;
import com.xa.service.AreaService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {

	
	@Autowired
	private AreaService<Area> areaService;
	

	/**
	 * 获取所有区域
	 * @param sign
	 * @param random
	 */
	@RequestMapping("getAllArea")
	public void getAllArea(){
		try {
			this.sendAjaxMsg(this.areaService.getAllArea());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 列出所有区域
	 * @return
	 */
	@RequestMapping("toListArea")
	public ModelAndView toListArea(){
		ModelAndView modelAndView = new ModelAndView("area/areaList");
		return modelAndView;
	}
	
	/**
	 * 添加区域
	 */
	@RequestMapping("addArea")
	public void addArea(Area area){
		try {
			this.sendAjaxMsg(this.areaService.addArea(area));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除区域
	 * @param id
	 */
	@RequestMapping("delAreaById")
	public void delAreaById(Long id){
		try {
			this.sendAjaxMsg(this.areaService.delAreaById(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
