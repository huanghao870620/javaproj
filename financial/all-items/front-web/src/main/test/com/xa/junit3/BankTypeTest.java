package com.xa.junit3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.BankType;
import com.xa.mapper.AccountTypeMapper;
import com.xa.mapper.BankTypeMapper;

import junit.framework.TestCase;

public class BankTypeTest extends TestCase {
	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private BankTypeMapper bankTypeMapper;

	@Override
	protected void setUp() throws Exception {
		 this.bankTypeMapper = (BankTypeMapper)ctx.getBean("bankTypeMapper");
	}
	
	public void init() {
		try {
			this.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加银行类型
	 */
	public void testAddBankType(){
		BankType industrialAndCom = new BankType(); //工商银行
		industrialAndCom.setName("工商银行");
		this.bankTypeMapper.insert(industrialAndCom);
		
		BankType consBank = new BankType(); 
		consBank.setName("建设银行");
		this.bankTypeMapper.insert(consBank);
		
		BankType bankOfChina = new BankType();
		bankOfChina.setName("中国银行");
		this.bankTypeMapper.insert(bankOfChina);
		
		BankType agricChina = new BankType();
		agricChina.setName("中国农业银行");
		this.bankTypeMapper.insert(agricChina);
		
		BankType banComm = new BankType();
		banComm.setName("交通银行");
		this.bankTypeMapper.insert(banComm);
		
		BankType merchBank = new BankType();
		merchBank.setName("招商银行");
		this.bankTypeMapper.insert(merchBank);
	}
}
