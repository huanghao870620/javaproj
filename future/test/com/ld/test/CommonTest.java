package com.ld.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ld.model.User;
import com.ld.service.UserService;
import com.ld.test.freemarker.DealFreemarker;
import com.ld.util.EncryptionTool;

import freemarker.template.TemplateException;
import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:./spring/application.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CommonTest {

	private SqlSession session = null;

	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring\\application.xml");

	@Before
	public void before() {
		Object obj = ctx.getBean("sqlSessionFactory");
		if (obj instanceof SqlSessionFactory) {
			SqlSessionFactory sf = (SqlSessionFactory) obj;
			try {
				this.session = sf.openSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	@Test
	public void test0() {
		List<?> list = session.selectList("selectByPrimaryKey", 1);
		System.out.println(list.size());
	}

	//@Test
	//@Rollback(true)
	public void test1() {
		User user = new User();
		user.setUserName("abcdef");
		user.setPassword("321321");		
		try {
			this.session.insert("insert", user);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	// @Test
	public void test2() {
		DealFreemarker dfk = new DealFreemarker();
		try {
			dfk.generate();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	//@Test
	public void test3() {
		UserService<User> userService = (UserService<User>) this.ctx.getBean("userServiceImpl");
		User user = new User();
		user.setName("ºúÎÄÀÚ1");
		user.setRegistTime(new Date());
		user.setUserName("admin");
		user.setPassword(EncryptionTool.addSaltEncrypt("admin", user.getUserName()));
		try {
			userService.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void after() {
		System.out.println("after");
	}
}
