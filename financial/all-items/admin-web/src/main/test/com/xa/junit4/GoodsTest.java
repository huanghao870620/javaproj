package com.xa.junit4;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Goods;
import com.xa.service.FileService;
import com.xa.service.GoodsService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class GoodsTest {
	
	@Autowired
	private GoodsService<Goods> goodsService;
	
	@Autowired
	private FileService<com.xa.entity.File> testFileService;
	
	
	@Test
	public void testAddGood(){
		File file = new File("D:\\doc\\xunan\\20161230");
		if(file.isDirectory()){
			File files[]= file.listFiles();
			for(int i=0;i<files.length;i++){
				String path= files[i].getAbsolutePath();
				File childFile[]= files[i].listFiles();
				 for(int j=0;j<childFile.length;j++){
					 String childPath = childFile[j].getAbsolutePath();
//					 System.out.println(childPath);
					 
					 File sFile[]= childFile[j].listFiles();
					 for(int k=0;k<sFile.length;k++){
						 String sPath=  sFile[k].getAbsolutePath();
						 System.out.println(sPath);
					 }
				 }
			}
		}
	}
}
