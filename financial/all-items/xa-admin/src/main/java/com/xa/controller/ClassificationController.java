package com.xa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.service.ClassificationService;
import com.xa.service.FileService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/classification")
public class ClassificationController extends BaseController {

	@Autowired
	private ClassificationService<Classification> classificationService;
	
	@Autowired
	private FileService<File> fileService;
	
	
	/**
	 * 根据父id获取子分类
	 * @param sign
	 * @param pid
	 */
	@RequestMapping("getChildByClassId")
	public void getChildByClassId(Long pid){
		try {
			this.sendHtml(this.classificationService.getClassbyPid(pid, request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@RequestMapping("getClassifi")
	public void getClassifi(){
		 try {
			this.sendAjaxMsg(this.classificationService.getClassificationForStr());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 去添加分类
	 * @return
	 */
	@RequestMapping("toAddClass")
	public ModelAndView toAddClass(){
		ModelAndView modelAndView = new ModelAndView("classification/addClass");
		return modelAndView;
	}
	
	/**
	 * 去查看分类
	 * @return
	 */
	@RequestMapping("toListClass")
	public ModelAndView toListClass(){
		ModelAndView modelAndView = new ModelAndView("classification/classList");
		return modelAndView;
	}
	
	/**
	 * 编辑商品
	 * @param classification
	 * @param imgFile
	 * @return
	 */
	@RequestMapping("editClass")
	public ModelAndView editClass(Classification classification,@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		ModelAndView modelAndView = new ModelAndView("classification/classList");
		try {
			this.classificationService.updateClass(classification, imgFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * 去编辑分类
	 * @return
	 */
	@RequestMapping("toEditClass")
	public ModelAndView toEditClass(Long classid){
		ModelAndView modelAndView = new ModelAndView("classification/editClass");
		this.classificationService.fillClassById(modelAndView, classid);
		return modelAndView;
	}
	
	
	/**
	 * 添加分类
	 * @return
	 */
	@RequestMapping("addClass")
	public ModelAndView addClass(Classification classification,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		ModelAndView modelAndView = new ModelAndView("classification/classList");
		try {
			this.classificationService.addClass(classification, imgFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return modelAndView;
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@RequestMapping("deleteClass")
	public void deleteClass(Long id){
		try {
			this.sendAjaxMsg(this.classificationService.deleteClass(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
