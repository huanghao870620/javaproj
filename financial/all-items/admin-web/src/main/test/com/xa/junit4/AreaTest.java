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
	
	
	@Test
	public void testSort(){
		
		int [] array = new int []{88,56,22,10,25,500,47};
		
		int i,j,temp,length;
		length = array.length;
		for( j = 0; j < length; j ++){
			for(i = 0;i < length - j - 1;i ++ ){
				
				if (array[i] > array[i + 1]) {  //
					temp = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
				}			
			}
			
		}
		for(int index = 0;index < length;index++){
			System.out.println(array[index] + "-");
		}
	}
	
}
