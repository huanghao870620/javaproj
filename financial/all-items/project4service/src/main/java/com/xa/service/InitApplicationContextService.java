package com.xa.service;

public interface InitApplicationContextService<T> extends BaseServiceInte<T> {

	/**
	 * 初始化应用数据
	 */
	public void initApp();
	
	/**
	 * 账号类型
	 */
	public void initAccountType();
	
	/**
	 * 银行类型
	 */
	public void initBankType();
	
	/**
	 * 照片类型
	 */
	public void initFileType();
	
	/**
	 * 总分类类型
	 */
	public void initClassification();
//	public void initGeneralLedger();
	
	/**
	 * 初始化品牌
	 */
	public void initBrand();
	
	/**
	 * 初始化国家
	 */
	public void initCountry();
	
	/**
	 * 初始化币种
	 */
	public void initCurrency();
	

	/**
	 * 吃时候规格类型
	 */
	public void initNorms() ;
	
	/**
	 * 上传类型
	 */
	public void initUploadType();
}
