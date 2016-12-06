package com.xa.junit3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.AccountType;
import com.xa.mapper.AccountTypeMapper;

import junit.framework.TestCase;
/**
 * 
 * @author burgess
 *
 */
public class AccountTypeTest extends TestCase {

	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private AccountTypeMapper accountTypeMapper;

	@Override
	protected void setUp() throws Exception {
		 this.accountTypeMapper = (AccountTypeMapper)ctx.getBean("accountTypeMapper");
	}



	@Override
	protected void tearDown() throws Exception {
	}
	
	public void init() {
		try {
			this.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 测试添加账号类型
	 */
	public void testAddAccountType(){
		AccountType weChat = new AccountType();
		weChat.setName("微信");
		this.accountTypeMapper.insert(weChat);
		
		AccountType faceBook = new AccountType();
		faceBook.setName("faceBook");
		this.accountTypeMapper.insert(faceBook);
		
		AccountType twitter = new AccountType();
		twitter.setName("推特");
		this.accountTypeMapper.insert(twitter);
	}
}
