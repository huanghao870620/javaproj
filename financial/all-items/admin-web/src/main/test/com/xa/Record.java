package com.xa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.User;
import com.xa.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class Record {
	
	
	@Autowired
	private UserService<User> userService;

	@Before
	public void before(){
		
	}
	
	
	@After
	public void after(){
		
	}
	
	@Test
	public void add(){
		System.out.println(1);
	}
}
