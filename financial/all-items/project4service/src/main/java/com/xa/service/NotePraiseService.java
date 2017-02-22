package com.xa.service;

import com.xa.entity.NotePraise;

public interface NotePraiseService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param notePraise
	 * @param sign
	 * @return
	 */
	public String addNotePraise(NotePraise notePraise,String sign);
	
	/**
	 * 
	 * @param notePraise
	 * @param sign
	 * @return
	 */
	public String disabPraise(NotePraise notePraise, String sign);
}
