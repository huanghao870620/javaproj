package com.ld.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.ld.dto.UserDto;
import com.ld.model.MessageBO;
import com.ld.service.LoginService;
import com.opensymphony.xwork2.ActionContext;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class LoginServiceImplTest {

	@Autowired
	private LoginService loginService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {
		MessageBO result = null;
		try {
			result = loginService.login(null);
			Assert.assertEquals("请输入用户名密码!", result.getMessage());
			
			UserDto userDto = new UserDto();
			result = loginService.login(userDto);
			Assert.assertEquals("请输入用户名密码!", result.getMessage());
			
			userDto.setUserName("test");
			result = loginService.login(userDto);
			Assert.assertEquals("请输入用户名密码!", result.getMessage());
			
			Map<String,Object> map = new HashMap<String,Object>();
			ActionContext ctx = new ActionContext(map);
			ctx.setSession(map);
			ActionContext.setContext(ctx);
			
			userDto.setPassword("test");
			HttpSession session1 = EasyMock.createMock(HttpSession.class);
			HttpServletRequest req1 = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(session1.getAttribute("checkCode")).andReturn("abcd");
			EasyMock.expect(req1.getSession()).andReturn(session1);
			EasyMock.replay(session1,req1);
			ServletActionContext.setRequest(req1);
			result = loginService.login(userDto);
			Assert.assertEquals("验证码为空!", result.getMessage());
			
			userDto.setCheckCode("abcd");
			HttpSession session2 = EasyMock.createMock(HttpSession.class);
			HttpServletRequest req2 = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(session2.getAttribute("checkCode")).andReturn("aaaa");
			EasyMock.expect(req2.getSession()).andReturn(session2);
			EasyMock.replay(session2,req2);
			ServletActionContext.setRequest(req2);
			result = loginService.login(userDto);
			Assert.assertEquals("您输入的验证码不正确!", result.getMessage());
			
			HttpSession session3 = EasyMock.createMock(HttpSession.class);
			HttpServletRequest req3 = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(session3.getAttribute("checkCode")).andReturn("abcd");
			EasyMock.expect(req3.getSession()).andReturn(session3);
			EasyMock.replay(session3,req3);
			ServletActionContext.setRequest(req3);
			result = loginService.login(userDto);
			Assert.assertEquals("用户名或密码错误!", result.getMessage());
			
			UserDto userDto2 = new UserDto();
			userDto2.setUserName("admin");
			userDto2.setPassword("admin");
			userDto2.setCheckCode("abcd");
			HttpSession session4 = EasyMock.createMock(HttpSession.class);
			HttpServletRequest req4 = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(session4.getAttribute("checkCode")).andReturn("abcd");
			EasyMock.expect(req4.getSession()).andReturn(session4);
			EasyMock.replay(session4,req4);
			ServletActionContext.setRequest(req4);
			result = loginService.login(userDto2);
			Assert.assertEquals("登录成功!", result.getMessage());
			
			HttpSession session5 = EasyMock.createMock(HttpSession.class);
			HttpServletRequest req5 = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(session5.getAttribute("checkCode")).andReturn("abcd");
			EasyMock.expect(req5.getSession()).andReturn(session5);
			EasyMock.replay(session5,req5);
			ServletActionContext.setRequest(req5);
			ActionContext.setContext(null);
			result = loginService.login(userDto2);
			Assert.assertEquals("登录失败,请稍后再试!", result.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
