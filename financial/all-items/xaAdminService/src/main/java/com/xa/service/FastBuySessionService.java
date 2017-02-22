package com.xa.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.FastBuySession;
import com.xa.entity.File;

public interface FastBuySessionService<T> extends BaseServiceInte<T> {

	/**
	 * 添加秒杀专场
	 * @param session
	 * @param goodIds
	 * @param cronDates
	 */
	public void addFastBuySession(FastBuySession session,
			Long[] goodIds,
			Date[] cronDates,
			MultipartFile imgAdvFile,
			Integer inventory,
			FileService<File> fileService)  throws IllegalStateException, IOException;

	/**
	 * 获取所有专场
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getAllSession(Integer pageNum,Integer pageSize);
	
	/**
	 * 获取专场信息
	 * @param modelAndView
	 * @param id
	 */
	public void getSession(ModelAndView modelAndView, Long id);
	
	/**
	 * 编辑专场
	 * @param session
	 * @param imgAdvFile
	 * @param fileService
	 */
	public void editSession(FastBuySession session,
			MultipartFile imgAdvFile,
			FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 获取专场时间
	 * @param id
	 * @return
	 */
	public String getTimeBySessionId(Long id);
	
	/**
	 * 中国时间格式
	 * @param modelAndView
	 * @param id
	 */
	public void getSessionByCommonFormat(ModelAndView modelAndView, Long id) ;
}
