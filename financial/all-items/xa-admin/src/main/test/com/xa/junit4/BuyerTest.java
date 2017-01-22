package com.xa.junit4;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Buyers;
import com.xa.service.BuyersService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class BuyerTest {

	@Autowired
	private BuyersService<Buyers> buyersService;
	
	
}
