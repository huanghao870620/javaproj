package com.ld.service.impl;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.model.UserRole;
import com.ld.service.UserRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class UserRoleServiceImplTest {

	@Autowired
	private UserRoleService<UserRole> userRoleService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddUserRole() {
		try {
			UserRole userRole = new UserRole();
			userRole.setRoleid(new BigDecimal(1));
			userRole.setUserid(new BigDecimal(1));
			List<UserRole> list = new LinkedList<UserRole>();
			list.add(userRole);
			userRoleService.addUserRole(list);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
