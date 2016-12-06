package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BankCard;
import com.xa.service.BankCardService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class BankCardServiceTest4Junit4 {

	@Autowired
	private BankCardService<BankCard> bankCardService;
	
	/**
	 * 
	 */
	@Test
	public void testGetBankList(){
		String sign = Security.getSign(new String[]{"buyHandId"});
		String text = this.bankCardService.getBankCardList(1L, sign);
		System.out.println(text);
	}
}
