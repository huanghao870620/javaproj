package com.xa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Activity;
import com.xa.entity.File;

public interface ActivityService<T> extends BaseServiceInte<T> {

	
	/**
	 * 添加活动
	 * @param activity
	 * @param imgAdvFile
	 * @param activityDetailFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addActivity(Activity activity, MultipartFile imgAdvFile, MultipartFile[] activityDetailFile,Long []ids,FileService<File> fileService)
			throws IllegalStateException, IOException;
	
	/**
	 * 获取活动列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String listActivity(Integer pageNum,Integer pageSize);
	
	/**
	 * 
	 * @param activityId
	 * @param modelAndView
	 */
	public void getActivity(Long activityId, ModelAndView modelAndView);
	
	/**
	 * 编辑活动
	 * @param activity
	 * @param modelAndView
	 */
	public  void editActivity(Activity activity, ModelAndView modelAndView,MultipartFile imgAdvFile,Long []ids,FileService<File> fileService) 
			throws IllegalStateException, IOException ;
	
	/**
	 * 删除活动
	 * @param activityId
	 */
	public String deleteActivityById(Long activityId);
	
	/**
	 * 
	 * @param activityId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getDetailPicByActivityId(Long activityId,Integer pageNum,Integer pageSize);
	
	/**
	 * 
	 * @param activityId
	 * @param detailPicFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addActivityDetailPic(Long activityId, MultipartFile detailPicFile,FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 删除活动详情图
	 * @param picId
	 * @param fileService
	 * @return
	 */
	public String deleteActivityDetailPic(Long afId,FileService<File> fileService);
}
