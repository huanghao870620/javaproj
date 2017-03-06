package com.xa.junit4;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

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
//		String text=this.areaService.getAllArea("d", sign);
//		System.out.println(text);
//	}
	
	
	@Test
	public void testSort(){
		int arr[] = {2,32,41,44,22,11,322,112,52};
		//此处实现排序
       for(int k=0;k<arr.length-1;k++){
    	   int temp1=0;
    	   for(int y=0;y<arr.length-k-1;y++){
    		   if(arr[y]>arr[y+1]){
    			   temp1=arr[y];
    			   arr[y]=arr[y+1];
    			   arr[y+1]=temp1;
    		   }
    	   }
       }
		
        //进行输出
        for(int i=0;i<arr.length;i++){
        	System.out.print(arr[i]+"\t");
        }
		
	}

}
