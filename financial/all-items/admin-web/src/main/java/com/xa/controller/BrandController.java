package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Brand;
import com.xa.service.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController {

	@Autowired
	private BrandService<Brand> brandService;
	
	/**
	 * 获取所有品牌
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getAllBrand")
	public void getAllBrand(String random, String sign){
		try {
			this.sendAjaxMsg(this.brandService.getBrands(random, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据品牌id获取商品
	 * @param brandId
	 * @param sign
	 */
	@RequestMapping("getGoodsByBrandId")
	public void getGoodsByBrandId(Long brandId, String sign){
		 try {
			this.sendAjaxMsg(this.brandService.getGoodsByBrandId(brandId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
