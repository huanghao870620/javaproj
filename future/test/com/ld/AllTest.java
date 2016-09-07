package com.ld;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ld.service.impl.AdviceTypeServiceImplTest;
import com.ld.service.impl.LevelServiceImplTest;
import com.ld.service.impl.LoginServiceImplTest;
import com.ld.service.impl.MenuServiceImplTest;
import com.ld.service.impl.MineralServiceImplTest;
import com.ld.service.impl.RoleServiceImplTest;
import com.ld.service.impl.RoomServiceImplTest;
import com.ld.service.impl.UserListenServiceImplTest;
import com.ld.service.impl.UserRoleServiceImplTest;
import com.ld.service.impl.UserServiceImplTest;
import com.ld.service.impl.ViewpointServiceImplTest;

@RunWith(Suite.class)
@SuiteClasses({
	 //这里自由加入需要运行的测试类
	AdviceTypeServiceImplTest.class,
	LevelServiceImplTest.class,
	LoginServiceImplTest.class,
	MenuServiceImplTest.class,
	MineralServiceImplTest.class,
	RoleServiceImplTest.class,
	RoomServiceImplTest.class,
	UserListenServiceImplTest.class,
	UserRoleServiceImplTest.class,
	UserServiceImplTest.class,
	ViewpointServiceImplTest.class
  })
public class AllTest {

}
