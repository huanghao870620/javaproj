package com.ld.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.model.AdviceType;
import com.ld.service.AdviceTypeService;
import com.opensymphony.xwork2.ActionContext;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class AdviceTypeServiceImplTest {

	@Autowired
	private AdviceTypeService<AdviceType> adviceTypeService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllAdviceType() {
		try {
			Map<String, Object> map = new HashMap<>();
			ActionContext ctx = new ActionContext(map);
			adviceTypeService.getAllAdviceType(ctx);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
		
	}

}
