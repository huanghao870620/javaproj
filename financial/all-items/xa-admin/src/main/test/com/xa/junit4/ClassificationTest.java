package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Classification;
import com.xa.service.ClassificationService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class ClassificationTest {

	@Autowired
	private ClassificationService<Classification> classificationService;
	
	
	@Test
	public void testGetAllClassification(){
		String random = "002";
		String sign = Security.getSign(new String[]{
				"random"
		});
//		String text = this.classificationService.getAllClassification(random,sign);
//		System.out.println(text);
	}
}
