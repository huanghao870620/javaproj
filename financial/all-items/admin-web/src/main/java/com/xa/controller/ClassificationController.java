package com.xa.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Classification;
import com.xa.service.ClassificationService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/classification")
public class ClassificationController extends BaseController {

	@Autowired
	private ClassificationService<Classification> classificationService;
	
	/**
	 * 获取所有分类
	 */
	@RequestMapping("getAllClassification")
	public void getAllClassification(String random,String sign){
		try {
			this.sendAjaxMsg(this.classificationService.getAllClassification(random,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据父id获取子分类
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
