package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.entity.UploadType;
import com.xa.service.BrandService;
import com.xa.service.FileService;
import com.xa.service.UploadTypeService;

@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController {

	@Autowired
	private BrandService<Brand> brandService;
	
	@Autowired
	private UploadTypeService<UploadType> uploadTypeService;
	
	@Autowired
	private FileService<File> fileService;
	
	/**
	 * 获取所有品牌
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getAllBrand")
	public void getAllBrand(String random, String sign){
//		this.brandService.getBrands(random, sign)
	}
	
	/**
	 * 跳转到添加品牌页面
	 * @param random
	 * @param sign
	 */
	@RequestMapping("toAddBrand")
	public ModelAndView toAddBrand(){
		ModelAndView modelAndView = new ModelAndView("brand/addBrand");
		uploadTypeService.getAllUploadType(modelAndView);
		return modelAndView;
	}
	
	/**
	 * 跳转到品牌列表页面
	 * @return
	 */
	@RequestMapping("toListBrand")
	public ModelAndView toListBrand(){
		ModelAndView modelAndView = new ModelAndView("brand/brandList");
		return modelAndView;
	}

	
	
	/**
	 * 获取品牌分页
	 * @param page
	 * @param rows
	 */
	@RequestMapping("getBrandByPaging")
	public void getBrandByPaging(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.brandService.getBrandsByPaging(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加品牌
	 * @return
	 */
	@RequestMapping("addBrand")
	public ModelAndView addBrand(Brand brand,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
			@RequestParam(value = "detailPicFile", required = false) MultipartFile  detailPicFile){
		ModelAndView modelAndView = new ModelAndView("redirect:toListBrand.htm");
		try {
			this.brandService.addBrand(brand, imgFile,detailPicFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * 编辑品牌
	 * @return
	 */
	@RequestMapping("toEditBrand")
	public ModelAndView toEditBrand(Long brandId){
		ModelAndView modelAndView = new ModelAndView("brand/editBrand");
		this.brandService.getBrandById(modelAndView, brandId);
		uploadTypeService.getAllUploadType(modelAndView);
		return modelAndView;
	}
	
	/**
	 * 编辑品牌
	 * @param brand
	 * @param imgFile
	 * @return
	 */
	@RequestMapping("editBrand")
	public ModelAndView editBrand(Brand brand, 
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
			@RequestParam(value = "detailPicFile", required = false) MultipartFile detailPicFile){
		ModelAndView modelAndView = new ModelAndView("redirect:toListBrand.htm");
		try {
			this.brandService.editBrand(brand, imgFile,detailPicFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return modelAndView;
	}
	

	/**
	 * 删除品牌根据id
	 * @param brandId
	 * @param fileService
	 */
	@RequestMapping("delByBrandId")
	public void delByBrandId(Long brandId){
		try {
			this.sendAjaxMsg(this.brandService.delByBrandId(brandId, fileService));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
