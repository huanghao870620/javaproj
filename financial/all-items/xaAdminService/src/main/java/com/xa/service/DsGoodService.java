package com.xa.service;

import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.DsGood;

/**
 * 
 * @author burgess
 *
 * @param <T>
 */
public interface DsGoodService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param dsId
	 * @return
	 */
	public String getGoodsByDsSessionId(Long dsId);
	
	/**
	 * 
	 * @param dsGood
	 */
	public void editDsGood(DsGood dsGood);
	
	/**
	 * 
	 * @param id
	 * @param modelAndView
	 */
	public void getDsGood(Long id, ModelAndView modelAndView);
}
