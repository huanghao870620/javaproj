package com.ld.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.model.Menu;
import com.ld.service.MenuService;
import com.opensymphony.xwork2.ActionContext;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class MenuServiceImplTest {

	@Autowired
	private MenuService<Menu> menuService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFillMenu() {
		try {
			StringBuilder sb = new StringBuilder();
			String basepath = "e:\\test.txt";
			menuService.fillMenu(sb, basepath);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInitUserMenu() {
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			
			ActionContext ctx = new ActionContext(map);
			ctx.setSession(map);
			ActionContext.setContext(ctx);
			
			HttpServletRequest req = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(req.getContextPath()).andReturn("e:\\test");
			EasyMock.replay(req);
			ServletActionContext.setRequest(req);
			menuService.initUserMenu();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testFindByName() {
		try {
			menuService.findByName("Ω≤ ¶π‹¿Ì");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
}
