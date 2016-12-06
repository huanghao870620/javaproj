package com.xa.junit3;
/**
 * 初始化应用
 * @author burgess
 *
 */
public class InitServer {

	public static void main(String[] args) {
		AccountTypeTest accountTypeTest = new AccountTypeTest();
		accountTypeTest.init();
		accountTypeTest.testAddAccountType();
		
		BankTypeTest bankTypeTest = new BankTypeTest();
		bankTypeTest.init();
		bankTypeTest.testAddBankType();
		
		FileTypeTest fileTypeTest = new FileTypeTest();
		fileTypeTest.init();
		fileTypeTest.testInsert();
	}
}
