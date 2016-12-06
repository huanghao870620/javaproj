package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Mail;
import com.xa.service.MailService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class MailServiceTest4Junit4 {

	@Autowired
	private MailService<Mail> mailService;
	
	/**
	 * 测试添加邮件
	 */
	@Test
	public void testAddMail(){
		Mail mail = new Mail();
		mail.setContent("dddd");
		mail.setBuyHandId(3L);
		mail.setTitle("hello");
		this.mailService.addMail(mail , "");
	}
}
