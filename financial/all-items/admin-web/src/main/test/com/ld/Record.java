package com.ld;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.entity.User;
import com.ld.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
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
//		User user = new User();
//		user.setAccount("ºúÎÄÀÚ");
//		user.setEmail("");
//		user.setHeadportrait(2);
//		user.set
//		this.userService.insert(user);
	}
}
