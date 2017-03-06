package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Color;
import com.xa.service.ColorService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/color")
public class ColorController extends BaseController {

	@Autowired
	private ColorService<Color> colorService;
	
	/**
	 * 跳转到添加颜色页面
	 * @return
	 */
	@RequestMapping("toAddColor")
	public ModelAndView toAddColor(){
		ModelAndView modelAndView = new ModelAndView("color/addColor");
		return modelAndView;
	}
	
	/**
	 * 添加颜色
	 * @return
	 */
	@RequestMapping("addColor")
	public ModelAndView addColor(Color color){
		ModelAndView modelAndView = new ModelAndView("redirect:toListColor.htm");
		this.colorService.addColor(color);
		return modelAndView;
	}
	
	/**
	 * 跳转到颜色列表页面
	 * @return
	 */
	@RequestMapping("toListColor")
	public ModelAndView toListColor(){
		ModelAndView modelAndView = new ModelAndView("color/colorList");
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping("listColor")
	public void listColor(Integer page, Integer rows){
		try {
			this.sendAjaxMsg(this.colorService.listColor(page,rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
