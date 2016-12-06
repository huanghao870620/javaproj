package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Area;
import com.xa.service.AreaService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class AreaTest {

	@Autowired
	private AreaService<Area> areaService;
	
	@Test
	public void testGetAllArea(){
		String sign = Security.getSign(new String[]{
				"random"
		});
		String text=this.areaService.getAllArea("d", sign);
		System.out.println(text);
	}
}
