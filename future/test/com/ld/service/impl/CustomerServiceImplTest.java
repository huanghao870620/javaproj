package com.ld.service.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class CustomerServiceImplTest {

	/*@Autowired
	private CustomerService<Customer> customerService;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private CustomerUserService<CustomerUser> customeruserService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetPassword() {
		try {
			Customer record = new Customer();
			record.setAccount("test");
			customerService.setPassword(record);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testAccountExists() {
		try {
			Customer record = new Customer();
			record.setAccount("test1");
			Assert.assertTrue(customerService.accountExists(record));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testFindCustomerByUserPass() {
		try {
			Customer record = new Customer();
			record.setAccount("test00000");
			boolean flag = customerService.findCustomerByUserPass(record);
			Assert.assertFalse(flag);

			Map<String, Object> map = new HashMap<>();
			ActionContext context = new ActionContext(map);
			context.setSession(map);
			ActionContext.setContext(context);

			Customer record1 = new Customer();
			record1.setAccount("test1");
			record1.setPassword("test1");
			boolean flag1 = customerService.findCustomerByUserPass(record1);
			Assert.assertTrue(flag1);

			Customer record2 = new Customer();
			record2.setAccount("teacher");
			record2.setPassword("teacher");
			boolean flag2 = customerService.findCustomerByUserPass(record2);
			Assert.assertTrue(flag2);

		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	//testTestByAjax“—∏≤∏«
	//@Test
	public void testFindAllByPaging() {
		try {
			customerService.findAllByPaging();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testTestByAjax() {
		try {

			Map<String, Object> map = new HashMap<String, Object>();

			String[] pageArr = new String[1];
			pageArr[0] = "1";
			String[] rowArr = new String[1];
			rowArr[0] = "1";

			map.put("rows", pageArr);
			map.put("page", rowArr);

			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);

			HttpServletResponse resp = EasyMock.createMock(HttpServletResponse.class);
			resp.setCharacterEncoding("UTF-8");
			EasyMock.expectLastCall();

			PrintWriter writer = new PrintWriter(new FileWriter(new File("e:\\test.txt")),true);
			EasyMock.expect(resp.getWriter()).andReturn(writer);

			EasyMock.replay(resp);
			ServletActionContext.setResponse(resp);

			customerService.testByAjax();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testUpdateCustomer() {
		try {
			
			Customer retCust = insertTest();
			Assert.assertNotNull(retCust);
			
			Customer newRecord = new Customer();
			newRecord.setId(retCust.getId());
			newRecord.setAccount("testCBA");
			newRecord.setPassword("testCBA");
			newRecord.setName("testCBA");
			newRecord.setNickName("testCBA");
			newRecord.setFirmOfferAccount("testCBA");
			newRecord.setLevelId(new BigDecimal(2));
			newRecord.setEmail("testCBA");
			newRecord.setPhone("testCBA");
			newRecord.setAddress("testCBA");
			newRecord.setRegistrationTime(new Date());
			newRecord.setUpdateTime(new Date());
			
			customerService.update(newRecord);
			
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
		
	}

	//@Test
	public void testBackLogin() {
		
	}

	@Test
	public void testDelete() {
		
		try {
			Customer retCust = insertTest();
			Assert.assertNotNull(retCust);
			
			Map<String, Object> map = new HashMap<String, Object>();

			String[] ids = new String[1];
			ids[0]=retCust.getId().toString();
			map.put("id", ids);

			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);
			
			customerService.delete();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
		
	}

	@Test
	public void testGetAllCustomer() {
		try {
			Customer customer = new Customer();
			customer.setAccount("test");
			String sessionid = "123456";
			Session session = null;
			UserManager.getUserManager().getScm().addCustomerBinding(sessionid, session, customer);
			
			customerService.getAllCustomer();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testAddCustomerUser() {
		try {
			CustomerUserDto dto = new CustomerUserDto();
			Customer customer = new Customer();
			dto.setUserId(new BigDecimal(123));
			customer.setId(new BigDecimal(123));
			customerService.addCustomerUser(dto, customer, customeruserService);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	
	private Customer insertTest(){
		Customer record = new Customer();
		record.setAccount("testABC");
		record.setPassword("testABC");
		record.setName("testABC");
		record.setNickName("testABC");
		record.setFirmOfferAccount("testABC");
		record.setLevelId(new BigDecimal(1));
		record.setEmail("testABC");
		record.setPhone("testABC");
		record.setAddress("testABC");
		record.setRegistrationTime(new Date());
		record.setUpdateTime(new Date());
		
		Assert.assertEquals(1,customerService.insert(record));
		Customer retCust = this.customerMapper.customerAccountIsExists(record);
		return retCust;
	}*/
}
