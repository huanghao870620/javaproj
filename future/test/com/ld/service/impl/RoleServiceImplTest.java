package com.ld.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
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

import com.ld.dto.RoleDto;
import com.ld.model.Role;
import com.ld.service.RoleService;
import com.opensymphony.xwork2.ActionContext;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class RoleServiceImplTest {

	@Autowired
	private RoleService<Role> roleService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryByAjaxRoleDto() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] size = new String[2];
			String[] index = new String[2];
			size[0]="1";
			size[1]="2";
			
			index[0]="1";
			index[1]="2";
			map.put("size", size);
			map.put("index", index);
			
			ActionContext ctx = new ActionContext(map);
			ctx.setParameters(map);
			ActionContext.setContext(ctx);
			
			HttpServletResponse resp = EasyMock.createMock(HttpServletResponse.class);
			resp.setCharacterEncoding("UTF-8");
			EasyMock.expectLastCall();

			PrintWriter writer = new PrintWriter(new FileWriter(new File("e:\\test.txt")),true);
			EasyMock.expect(resp.getWriter()).andReturn(writer);

			EasyMock.replay(resp);
			ServletActionContext.setResponse(resp);
			
			RoleDto dto = new RoleDto();
			roleService.queryByAjax(dto);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testPutRoles() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext ctx = new ActionContext(map);
			ActionContext.setContext(ctx);
			roleService.putRoles();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testGetUserByRole() {
		try {
			RoleDto dto = new RoleDto();
			dto.setRoleid(new BigDecimal(2));
			roleService.getUserByRole(dto);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

}
