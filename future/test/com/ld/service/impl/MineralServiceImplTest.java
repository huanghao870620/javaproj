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

import com.ld.model.Mineral;
import com.ld.service.MineralService;
import com.opensymphony.xwork2.ActionContext;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class MineralServiceImplTest {

	@Autowired
	private MineralService<Mineral> mineralService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllMineral() {
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			ActionContext ctx = new ActionContext(map);
			mineralService.getAllMineral(ctx);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

}
