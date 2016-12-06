package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.AccountTransactionRecords;
import com.xa.service.AccountTransactionRecordsService;
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class AccountTransactionRecordsServiceTest4Junit4 {

	@Autowired
	private AccountTransactionRecordsService<AccountTransactionRecords> accountTransactionRecordsService;
	
	
	/**
	 * 获取钱包记录
	 */
	@Test
	public void testGetRecordsByPaging(){
	 	String text = accountTransactionRecordsService.getRecordsByPaging(1, 10, "");
	 	System.out.println(text);
	}
	
}
