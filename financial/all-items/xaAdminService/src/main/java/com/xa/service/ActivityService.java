package com.xa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

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
}
