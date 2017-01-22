package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.service.CountryService;
import com.xa.service.FileService;

@Controller
@RequestMapping("/country")
public class CountryController extends BaseController {

	@Autowired
	private CountryService<Country> countryService;
	
	@Autowired
	private FileService<File> fileService;

	/**
	 * 获取所有国家
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getCountrys")
	public void getCountrys(String random,String sign){
//		try {
//			this.sendAjaxMsg(this.countryService.getAllCountry(random, sign));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 去列出国家
	 * @return
	 */
	@RequestMapping("toListCountry")
	public ModelAndView toListCountry(){
		ModelAndView modelAndView = new ModelAndView("country/countryList");
		return modelAndView;
	}
	
	/**
	 * 去添加国家
	 * @return
	 */
	@RequestMapping("toAddCountry")
	public ModelAndView toAddCountry() {
		ModelAndView modelAndView = new ModelAndView("country/addCountry");
		return modelAndView;
	}
	
	/**
	 * 获取国家-分页
	 * @param page
	 * @param rows
	 */
	@RequestMapping("getCountrysByPaging")
	public void getCountrysByPaging(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.countryService.getCountrysByPaging(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加国家
	 * @param country
	 * @param imgFile
	 */
	@RequestMapping("addCountry")
	public ModelAndView addCountry(Country country,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		ModelAndView modelAndView = new ModelAndView("redirect:toListCountry.htm");
		 try {
			this.countryService.addCountry(country, imgFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return modelAndView;
	}
	
	/**
	 * 跳转到编辑国家页面
	 * @return
	 */
	@RequestMapping("toEditCountry")
	public ModelAndView toEditCountry(Long id){
		ModelAndView modelAndView = new ModelAndView("country/editCountry");
		this.countryService.getCountryById(modelAndView, id);
		return modelAndView;
	}
	
	
	/**
	 * 编辑国家
	 * @param country
	 * @param imgFile
	 * @param fileService
	 */
	@RequestMapping("editCountry")
	public ModelAndView editCountry(Country country,
			@RequestParam(value = "imgFile", required = false)MultipartFile imgFile){
		 ModelAndView modelAndView = new ModelAndView("redirect:toListCountry.htm");
		try {
			this.countryService.editCountry(country, imgFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return modelAndView;
	}
}
