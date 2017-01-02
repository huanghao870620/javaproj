package com.xa.service;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public interface CountryService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有国家
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllCountry(String random,String sign);
	
	/**
	 * 
	 * @param sign
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public String getAllCountryBySort(String random,String sign) throws BadHanyuPinyinOutputFormatCombination;
}
