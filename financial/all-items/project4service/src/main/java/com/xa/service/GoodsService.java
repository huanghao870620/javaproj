package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.Brand;
import com.xa.entity.Goods;
import com.xa.entity.Mall;
import com.xa.enumeration.PhotoType;

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
			String sign) throws IllegalStateException, IOException ;
	
	
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
	public String getGoodsByClassifi(Long classid, String sign);
	
	/**
	 * 获取商品详情
	 * @param id
	 * @param sign
	 * @return
	 */
	public String getGoodsDetailById(Long id,Long cartId, String sign);
	
	/**
	 * 上传文件
	 * @param request
	 * @param multipartFile
	 * @param type
	 * @param file
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void uploadFile(HttpServletRequest request,MultipartFile multipartFile,PhotoType type,com.xa.entity.File file) throws IllegalStateException, IOException;
}
