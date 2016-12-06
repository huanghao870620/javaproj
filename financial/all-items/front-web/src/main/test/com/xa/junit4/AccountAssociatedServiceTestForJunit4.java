package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.AccountAssociated;
import com.xa.entity.File;
import com.xa.service.AccountAssociatedService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class AccountAssociatedServiceTestForJunit4 {

	@Autowired
	private AccountAssociatedService<AccountAssociated> accountAssociatedService;
	
	
	/**
	 * 测试绑定
	 */
	@Test
	public void testAssociatedAccount(){
		String unionId = "oK5yqv0gGicGDG2kAMbBstUVJrN8";
		String mobile = "17301748932";
		Long accountType = 1L;
		String text = this.accountAssociatedService.associatedAccount(unionId, mobile, accountType,"");
		System.out.println(text);
	}
	
	
	/**
	 * 测试解绑
	 */
	@Test
	public void dissociated(){
		this.accountAssociatedService.dissociated("oK5yqv0gGicGDG2kAMbBstUVJrN8","");
	}
	
	/**
	 * 测试aop
	 */
	@Test
	public void testAop(){
		File file = new File();
		file.setUriPath("dddd");
		file.getUriPath();
		System.out.println("88888");
	}
}
