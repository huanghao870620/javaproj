package com.ld.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class test {

	/*@Autowired
	private CustomerService<Customer> customerService;
	
	private void AddCustomer(String account){
		Customer customer = new Customer();
		customer.setAccount(account);
		customer.setPassword(EncryptionTool.addSaltEncrypt(account, account));
		customer.setName(account);
		customer.setNickName(account);
		customer.setLevelId(new BigDecimal(1));
		customer.setRegistrationTime(new Date());
		customerService.insert(customer);
	}
	@Test
	public void testA() {
		
		for (int i = 0; i < 100; i++) {
			AddCustomer("test_"+i);
		}
	}*/
}