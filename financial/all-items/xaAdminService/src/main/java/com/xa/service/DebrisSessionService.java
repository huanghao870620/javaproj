package com.xa.service;

import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.DebrisSession;

public interface DebrisSessionService<T> extends BaseServiceInte<T> {

	/**
	 * 获取抢购时间
	 * @param modelAndView
	 * @param id
	 */
	public void getDebrisSession(ModelAndView modelAndView,Long id,Long fbsId);
	
	/**
	 * 编辑抢购时间
	 * @param modelAndView
	 * @param dSession
	 * @param fbsId
	 */
	public void editDebrisSession(ModelAndView modelAndView,DebrisSession dSession,Long fbsId);
	
	/**
	 * 
	 * @param dSession
	 * @param goodId
	 */
	public void addDebrisSession(DebrisSession dSession,Long goodId,Long fbsId);
}
