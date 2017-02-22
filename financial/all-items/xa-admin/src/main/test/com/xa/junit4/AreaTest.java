package com.xa.junit4;

import java.awt.event.ItemEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@Transactional
//@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class AreaTest {

//	@Autowired
//	private AreaService<Area> areaService;
//	
//	@Test
//	public void testGetAllArea(){
//		String sign = Security.getSign(new String[]{
//				"random"
//		});
////		String text=this.areaService.getAllArea("d", sign);
////		System.out.println(text);
//	}
	
	
	
	@Test
	public void testSort(){
		int [] num = {1,8,9,2,6,4};
//		int tem;
//		for(int i = 0;i<num.length;i++){
//			for(int j = num.length - i;j<num.length;j++){
//				if(num[i]<num[j]){
//					tem = num[i];
//					num[i] = num[j];
//					num[j] = tem;
//				}
//			}
//		};
//		for(int n = 0;n<num.length;n++){
//			System.out.print(num[n]+",");
//		}
		
		
		for(int i=0; i<num.length; i++){
			 for(int j=1; j<num.length; j++){
				 
			 }
		}
		
	}
	
}
