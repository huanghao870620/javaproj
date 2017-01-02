package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Buyers;
import com.xa.service.InitApplicationContextService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class InitApp {
	
	@Autowired
	private InitApplicationContextService<Buyers> initApplicationContextService;
	
	
	/**
	 * 初始化应用
	 */
	@Test
	public void initApp() {
		this.initApplicationContextService.initApp();
	}
	
	
	/**
	 * 初始化分类
	 */
	@Test
	public void initClass() {
		this.initApplicationContextService.initClassification();
	}
	
	/**
	 * 初始化品牌
	 */
	@Test
	public void initBrand(){
		this.initApplicationContextService.initBrand();
	}
	
	/**
	 * 初始化币种
	 */
	@Test
	public void initCurrency(){
		this.initApplicationContextService.initCurrency();
	}
	
	/**
	 * 规格类型
	 */
	@Test
	public void initNorms(){
		this.initApplicationContextService.initNorms();
	}
	
	/**
	 * 上传类型
	 */
	@Test
	public void initUploadType(){
		this.initApplicationContextService.initUploadType();
	}
	
	/**
	 * 初始化分类
	 */
	@Test
	public void initClassification(){
		this.initApplicationContextService.initClassification();
	}
}
