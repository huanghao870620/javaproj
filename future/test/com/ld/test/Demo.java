package com.ld.test;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.UserDto;
import com.ld.model.User;
import com.ld.service.UserService;
import com.ld.service.impl.UserServiceImpl;

public class Demo {

	@Autowired
	private UserService<User> userService;
	
	@Test
	public void test(){
		userService = EasyMock.createMock(UserServiceImpl.class);
		UserDto dto = new UserDto();
		EasyMock.expect(userService.deteWhetheUserExists(dto)).andReturn(true);
		EasyMock.replay(userService);
		
		Assert.assertEquals(true,userService.deteWhetheUserExists(dto));
	}
}
