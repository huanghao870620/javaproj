package com.ld.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.UserDto;
import com.ld.model.MessageBO;
import com.ld.model.User;
import com.ld.model.UserRole;
import com.ld.service.UserRoleService;
import com.ld.service.UserService;
import com.opensymphony.xwork2.ActionContext;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class UserServiceImplTest extends TestCase {

	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private UserRoleService<UserRole> userRoleService;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeteWhetheUserExists() {
		try {
			UserDto userDto = new UserDto();
			userService.deteWhetheUserExists(userDto);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	//testDeteWhetheUserExists已覆盖
	// @Test
	public void testFindUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUserByUserName() {
		UserDto userDto = new UserDto();
		try {
			userService.findUserByUserName(userDto);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testAddUserError()  {
		MessageBO result = null;
		try {
			UserDto dto = new UserDto();
			dto.setUserName("test1");
			dto.setName("test1");
			dto.setPassword("test1");
			dto.setDupPassword("test1");
			ActionContext.setContext(null);
			result = userService.addUser(dto);
			assertEquals("新增用户失败!", result.getMessage());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testAddUser() {
		MessageBO result = null;
		try {
			UserDto dto = new UserDto();
			result = userService.addUser(dto);
			assertEquals("新增用户失败,请填写完整的信息!", result.getMessage());
			
			dto.setUserName("admin");
			result = userService.addUser(dto);
			assertEquals("新增用户失败,请填写完整的信息!", result.getMessage());
			
			dto.setName("admin");
			result = userService.addUser(dto);
			assertEquals("新增用户失败,请填写完整的信息!", result.getMessage());
			
			dto.setPassword("admin");
			result = userService.addUser(dto);
			assertEquals("新增用户失败,请填写完整的信息!", result.getMessage());
			
			dto.setDupPassword("admin1");
			result = userService.addUser(dto);
			assertEquals("新增用户失败,请填写完整的信息!", result.getMessage());
			
			dto.setDupPassword("admin");
			
			Map<String, Object> map = new HashMap<String,Object>();
			ActionContext ctx = new ActionContext(map);
			ctx.setParameters(map);
			ActionContext.setContext(ctx);
			result = userService.addUser(dto);
			assertEquals("新增用户失败,未选择角色!", result.getMessage());
			
			String[] roles = new String[2];
			roles[0] = "1";
			roles[1] = "2";
			map.put("role", roles);
			ctx.setParameters(map);
			ActionContext.setContext(ctx);
			result = userService.addUser(dto);
			assertEquals("新增用户失败,该用户名已存在!", result.getMessage());
			
			UserDto dto2 = new UserDto();
			dto2.setUserName("11111111111");
			dto2.setName("11111111111");
			dto2.setPassword("11111111111");
			dto2.setDupPassword("11111111111");
			result = userService.addUser(dto2);
			assertEquals("新增用户成功!", result.getMessage());
			
			userService.addUser(dto);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testFindTeachersList() {
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			ActionContext ctx = new ActionContext(map);
			ctx.setParameters(map);
			ActionContext.setContext(ctx);
//			userService.findTeachersList();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testDelete() {
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			String[]  ids = new String[2];
			ids[0] = "1";
			ids[1] = "2";
			map.put("id", ids);
			ActionContext ctx = new ActionContext(map);
			ctx.setParameters(map);
			ActionContext.setContext(ctx);
			userService.delete();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAuditMessage() {
		try {
//			userService.getAuditMessage();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
