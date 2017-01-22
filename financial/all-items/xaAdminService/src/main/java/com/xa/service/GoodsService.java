package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	 * 获取商品根据分类
	 * @param classid
	 * @param sign
	 * @return
	 */
	public String getGoodsByClassifi(Long classid,Integer pageNum, Integer pageSize, String sign);
	
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
	public String addGood(Goods good,MultipartFile bigFile,MultipartFile smallFile,MultipartFile[] bigPicFile,FileService<File> fileService) 
			throws IllegalStateException, IOException;


	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public String getGoodsByPaging(Integer page,Integer rows,String nameS, Long brandId, Long countryId);
	
	/**
	 * 获取商品信息
	 * @param modelAndView
	 * @param id
	 */
	public void getGood(ModelAndView modelAndView, Long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String delGoodById(Long id);
	
	/**
	 * 编辑商品
	 * @param good
	 * @param thumbFileId
	 * @param bigFile
	 * @param smallFile
	 * @param fileService
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String editGood(Goods good,Long thumbFileId,MultipartFile bigFile,MultipartFile smallFile,FileService<File> fileService) 
			throws IllegalStateException, IOException;
	
	/**
	 * 获取商品大图
	 * @param goodId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getBigPic4Good(Long goodId,Integer pageNum, Integer pageSize);
	
	/**
	 * 添加商品大图
	 * @param goodId
	 * @param bigPicFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addBigPic4Good(Long goodId, MultipartFile bigPicFile, FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 删除商品大图
	 * @param gfId
	 * @param fileService
	 */
	public String delBigPic(Long gfId, FileService<File> fileService);
	
}
