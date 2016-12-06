package com.ld.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.model.Level;
import com.ld.service.LevelService;

import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class LevelServiceImplTest {

	@Autowired
	private LevelService<Level> levelService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllLevel() {
		try {
			levelService.getAllLevel();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAllLevelByJson() throws IOException, TemplateException {
		try {
			levelService.getAllLevelByJson();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

//	@Test
//	public void testPutAllRole() {
//		try {
//			Map<String, Object> map = new HashMap<String,Object>();
//			ActionContext actionContext = new ActionContext(map);
//			actionContext.setParameters(map);
//			ActionContext.setContext(actionContext);
//			levelService.putAllRole();
//			assertTrue(true);
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
}
