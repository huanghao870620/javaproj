package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Country;
import com.xa.service.CountryService;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Controller
@RequestMapping("/country")
public class CountryController extends BaseController {
	
	@Autowired
	private CountryService<Country> countryService;

	/**
	 * 获取所有国家
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getCountrys")
	public void getCountrys(String random, String sign){
		try {
			this.sendAjaxMsg(this.countryService.getAllCountry(random, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取国家排序
	 */
	@RequestMapping("getAllCountryBySort")
	public void getAllCountryBySort(String random,String sign){
		try {
			this.sendAjaxMsg(this.countryService.getAllCountryBySort(random, sign));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
	}
}
