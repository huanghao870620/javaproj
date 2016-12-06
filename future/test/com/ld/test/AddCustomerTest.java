package com.ld.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import junit.framework.TestCase;

public class AddCustomerTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String account = "majiadan";

//	@Autowired
//	private CustomerService<Customer> customerService;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.16.238:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAddCustomer() throws Exception {
		driver.get(baseUrl + "/back/toLogin.htm");
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys("admin");
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys("admin");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("7y94");
		driver.findElement(By.linkText("登录")).click();

		assertEquals("Hi,管理员", driver.findElement(By.cssSelector("span.header_right")).getText());

		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).click();
		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).findElement(By.cssSelector("span")).click();
		driver.findElement(By.linkText("客户管理")).click();

		// 重复添加数据
		addCustomer2("account");
		addCustomer2("account");
		assertEquals("此账户已经存在!", driver.findElement(By.xpath("//ul[@class='errorMessage']"))
				.findElement(By.cssSelector("li.span")).getText());
		// 删除上面新建的客户
		List<WebElement> rows = driver.findElement(By.xpath("//table[@class='datagrid-btable']"))
				.findElements(By.xpath("//td/div[@class='datagrid-cell datagrid-cell-c1-account']"));
		System.out.println("行数：" + rows.size());
		for (WebElement row : rows) {
			if (row.getText().equals("account")) {
				row.click();
				driver.findElement(By.cssSelector("span.l-btn-left.l-btn-icon-left")).click();
				break;
			}
		}

		addCustomer(1, "助理秘书");
		addCustomer(2, "超级管理员");
		addCustomer(3, "讲师");
		addCustomer(4, "讲师助理");
		addCustomer(5, "助理秘书");
		addCustomer(6, "超级管理员");
		addCustomer(7, "讲师");
		addCustomer(8, "讲师助理");
	}

	private void addCustomer(int levelId, String RoleText) {
		
		String levelText = getLevelText(levelId);
		driver.findElement(By.cssSelector("span.l-btn-left.l-btn-icon-left")).click();
		driver.findElement(By.name("customer.account")).clear();
		driver.findElement(By.name("customer.account")).sendKeys(account + "_" + levelId);
		driver.findElement(By.name("customer.name")).clear();
		driver.findElement(By.name("customer.name")).sendKeys(account + "_" + levelId);
		driver.findElement(By.name("customer.nickName")).clear();
		driver.findElement(By.name("customer.nickName")).sendKeys(account + "_" + levelId);
		driver.findElement(By.name("customer.firmOfferAccount")).clear();
		driver.findElement(By.name("customer.firmOfferAccount")).sendKeys(account + "_" + levelId);
		new Select(driver.findElement(By.name("customer.levelId"))).selectByVisibleText(levelText);
		new Select(driver.findElement(By.id("selectRole"))).selectByVisibleText(RoleText);
		driver.findElement(By.linkText("完成")).click();

		// 验证客户是否添加成功
//		Customer customer = new Customer();
//		customer.setAccount(account);
//		assertTrue(customerService.accountExists(customer));

		// 验证新用户登录
		driver.findElement(By.linkText("注销")).click();
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys(account + "_" + levelId);
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys(account + "_" + levelId);
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("7y94");
		driver.findElement(By.linkText("登录")).click();
		assertEquals("Hi,管理员", driver.findElement(By.cssSelector("span.header_right")).getText());

		// 使用超级管理员登录
		driver.findElement(By.linkText("注销")).click();
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys("admin");
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys("admin");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("h3u4");
		driver.findElement(By.linkText("登录")).click();

		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).click();
		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).findElement(By.cssSelector("span")).click();
		driver.findElement(By.linkText("客户管理")).click();

		// 删除上面新建的客户
		List<WebElement> rows = driver.findElement(By.xpath("//table[@class='datagrid-btable']"))
				.findElements(By.xpath("//td/div[@class='datagrid-cell datagrid-cell-c1-account']"));
		System.out.println("行数：" + rows.size());
		for (WebElement row : rows) {
			if (row.getText().equals(account + "_" + levelId)) {
				row.click();
				driver.findElement(By.cssSelector("span.l-btn-left.l-btn-icon-left")).click();
				break;
			}
		}
		// assertFalse(customerService.accountExists(customer));
	}

	public void addCustomer2(String text) {
		driver.findElement(By.cssSelector("span.l-btn-left.l-btn-icon-left")).click();
		driver.findElement(By.name("customer.account")).clear();
		driver.findElement(By.name("customer.account")).sendKeys(text);
		driver.findElement(By.name("customer.name")).clear();
		driver.findElement(By.name("customer.name")).sendKeys(text);
		driver.findElement(By.name("customer.nickName")).clear();
		driver.findElement(By.name("customer.nickName")).sendKeys(text);
		driver.findElement(By.name("customer.firmOfferAccount")).clear();
		driver.findElement(By.name("customer.firmOfferAccount")).sendKeys(text);
		new Select(driver.findElement(By.name("customer.levelId"))).selectByVisibleText("游客");
		new Select(driver.findElement(By.id("selectRole"))).selectByVisibleText("助理秘书");
		driver.findElement(By.linkText("完成")).click();
	}
	
	private String getLevelText(int levelId){
		String levelText = "游客";
		switch (levelId) {
		case 1:
			levelText = "游客";
			break;
		case 2:
			levelText = "普通";
			break;
		case 3:
			levelText = "白银";
			break;
		case 4:
			levelText = "黄金";
			break;
		case 5:
			levelText = "铂金";
			break;
		case 6:
			levelText = "VIP";
			break;
		case 7:
			levelText = "钻石";
			break;
		case 8:
			levelText = "至尊";
			break;
		default:
			break;
		}
		return levelText;
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
