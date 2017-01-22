package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.service.BaseServiceInte;
import com.xa.service.FileService;

public interface ClassificationService<T> extends BaseServiceInte<T> {

	
	public void getFirstClass(ModelAndView mav);
	
	public String getClassbyPid(Long pid, HttpServletRequest request);
	
	public String getClassificationForStr();
	
	public String addClass(Classification classification,MultipartFile multipartFile,FileService<File> fileService) 
			throws IllegalStateException, IOException;
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public String deleteClass(Long id);
	
	/**
	 * 获取分类通过id
	 * @param mav
	 * @param classid
	 */
	public void fillClassById(ModelAndView mav,Long classid);
	
	
	/**
	 * 编辑分类
	 * @param classification
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void updateClass(Classification classification, MultipartFile imgFile,FileService<File> fileService) 
			throws IllegalStateException,
			IOException;
}
