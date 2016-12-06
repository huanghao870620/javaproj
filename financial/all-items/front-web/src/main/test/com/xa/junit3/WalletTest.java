package com.xa.junit3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.BuyhandWallet;
import com.xa.mapper.BuyhandWalletMapper;

import junit.framework.TestCase;

public class WalletTest extends TestCase {

	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private BuyhandWalletMapper buyhandWalletMapper;

	@Override
	protected void setUp() throws Exception {
		 this.buyhandWalletMapper = (BuyhandWalletMapper)ctx.getBean("buyhandWalletMapper");
	}



	@Override
	protected void tearDown() throws Exception {
	}
	
	
	/**
	 * 测试添加钱包
	 */
	public void testAddWallet(){
		BuyhandWallet wallet = new BuyhandWallet();
		wallet.setBalance(100.0);
		wallet.setBuyHandId(10L);
		this.buyhandWalletMapper.insert(wallet );
	}
	
	/**
	 * 
	 */
	public void testGetWalletByBuyHandId(){
		this.buyhandWalletMapper.getWalletByBuyHandId(9L);
	}
}
