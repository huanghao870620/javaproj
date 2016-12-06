package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Country;
import com.xa.service.CountryService;

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
}
