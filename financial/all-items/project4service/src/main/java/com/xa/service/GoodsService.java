package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.service.BaseServiceInte;
import com.xa.service.FileService;

public interface GoodsService<T> extends BaseServiceInte<T> {

	/**
	 * 文件上传
	 * @param session
	 * @param file
	 * @param model
	 * @param contextPath
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String addGood(
			MultipartFile goodsAccordingPositiveFile, // 商品正面照
			MultipartFile leftPhotoFile,//左侧视图
			MultipartFile rightPhotoFile,//右侧视图
			HttpServletRequest request,
			Goods goods,
			String mallName, 
			String mallAddress,
			String brandName,
			Long uploadTypeId,
			String sign,
			FileService<com.xa.entity.File> fileService) throws IllegalStateException, IOException ;
	
	
	/**
	 * 
	 * @return
	 */
	public String getGoodsByPaging(Integer pageNum, Integer pageSize, String sign);
	
	
	/**
	 * 异步添加商品
	 * 
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addGoodsByAjax(MultipartFile goodsAccordingToPositiveFile, // 商品正面照
			MultipartFile backGoodsAccordingToFile, // 商品背面照
			MultipartFile productProfileFile, // 商品侧面照
			MultipartFile goodsInvoiceFile, // 商品发票
			MultipartFile expressSingleFile, // 快递单
			Goods goods,
			HttpServletRequest request) throws IllegalStateException, IOException;
	
	
	/**
	 * 获取商品根据分类
	 * @param classid
	 * @param sign
	 * @return
	 */
	public String getGoodsByClassifi(Long classid,String nameS,Long buyerId,Integer pageNum, Integer pageSize, String sign,ClassificationService<Classification> classificationService);
	
	/**
	 * 获取商品详情
	 * @param id
	 * @param sign
	 * @return
	 */
	public String getGoodsDetailById(Long id,Long cartId, String sign);
	
	
	/**
	 * 获取商品详情通过商品id
	 * @param goodId
	 * @param sign
	 * @return
	 */
	public String getGoodDetailById(Long goodId,String sign);
	
	
	/**
	 *  添加商品
	 * @param good
	 * @param bigFile
	 * @param smallFile
	 * @param fileService
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String addGood(Goods good,MultipartFile bigFile,MultipartFile smallFile,FileService<File> fileService) 
			throws IllegalStateException, IOException;
}
