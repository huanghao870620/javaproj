package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Area;
import com.xa.service.AreaService;

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
	public void getAllArea(String sign,String random){
		try {
			this.sendAjaxMsg(this.areaService.getAllArea(random, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
