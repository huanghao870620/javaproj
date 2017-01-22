package com.xa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.service.BaseServiceInte;
import com.xa.service.FileService;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public interface CountryService<T> extends BaseServiceInte<T> {


	
	public String  getCountrysByPaging(Integer page, Integer rows);
	
	/**
	 * 获取所有国家
	 * @param mav
	 */
	public void getAllCountry(ModelAndView mav);
	
	/**
	 * 添加国家
	 * @param country
	 * @param imgFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addCountry(Country country, MultipartFile imgFile, FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 编辑国家
	 * @param country
	 * @param imgFile
	 * @param fileService
	 */
	public void editCountry(Country country,MultipartFile imgFile,FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 
	 * @param modelAndView
	 * @param id
	 */
	public void getCountryById(ModelAndView modelAndView, Long id);
	
}
