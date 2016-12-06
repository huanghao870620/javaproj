package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Classification;
import com.xa.service.ClassificationService;

@Controller
@RequestMapping("/classifi")
public class ClassificationController extends BaseController {

	@Autowired
	private ClassificationService<Classification> classificationService;

	/**
	 * 根据父分类id获取子分类
	 * @param sign
	 * @param pid
	 */
	@RequestMapping("getChildByClassId")
	public void getChildByClassId(String sign,Long pid){
		 try {
			this.sendAjaxMsg(this.classificationService.getChildByClassId(sign, pid));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
