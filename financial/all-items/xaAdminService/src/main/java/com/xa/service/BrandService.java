package com.xa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.service.BaseServiceInte;
import com.xa.service.FileService;

public interface BrandService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有品牌
	 * @param random
	 * @param sign
	 * @return
	 */
	public void getBrands(ModelAndView mav);
	
	
	/**
	 * 获取品牌
	 * @param page
	 * @param rows
	 * @return
	 */
	public String  getBrandsByPaging(Integer page, Integer rows);
	
	/**
	 * 添加品牌
	 * @param brand
	 * @param imgFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addBrand(Brand brand,MultipartFile imgFile,MultipartFile detailPicFile, FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 获取品牌
	 * @param mav
	 * @param id
	 */
	public void getBrandById(ModelAndView mav,Long id);
	
	/**
	 * 编辑品牌
	 * @param brand
	 * @param imgFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void editBrand(Brand brand, MultipartFile imgFile, MultipartFile detailPicFile,FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 删除品牌根据id
	 * @param brandId
	 * @param fileService
	 */
	public String delByBrandId(Long brandId,FileService<File> fileService);
}
