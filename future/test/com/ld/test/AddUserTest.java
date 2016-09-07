package com.ld.test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.UserDto;
import com.ld.model.User;
import com.ld.service.UserService;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class AddUserTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String userName = "majiadan";

	@Autowired
	private UserService<User> userService;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.16.238:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAddUser() throws Exception {
		//登录后台系统
		driver.get(baseUrl + "/back/toLogin.htm");
		driver.manage().window().maximize(); 
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys("admin");
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys("admin");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("h3u4");
		driver.findElement(By.linkText("登录")).click();
		String string = driver.findElement(By.cssSelector("span.header_right")).getText();
		//是否登录成功
		assertEquals("Hi,管理员", string);
		
		WebElement element = driver.findElement(By.cssSelector("li.expandable.lastExpandable"));
		element.click();
		element.findElement(By.cssSelector("span")).click();
		driver.findElement(By.linkText("添加用户")).click();

		
		//重复添加数据
		addUser("error","error","error");
		addUser("error","error","error");
		assertEquals("此用户已存在，不能重复添加!", driver.findElement(By.xpath("//ul[@class='errorMessage']")).findElement(By.cssSelector("li.span")).getText());
		UserDto userDtoError = new UserDto(); 
		userDtoError.setUserName("error");
		User userError = userService.findUserByUserName(userDtoError);
		
		//两次密码不一致
		addUser("error","error","error1");
		assertEquals("两次密码输入不一致!", driver.findElement(By.xpath("//ul[@class='errorMessage']")).findElement(By.cssSelector("li.span")).getText());
		
		//添加用户
		addUser(userName,userName,userName);

		//验证数据库中用户是否添加成功
		UserDto userDto = new UserDto(); 
		userDto.setUserName(userName);
		assertNotNull(userService.deteWhetheUserExists(userDto));
		userService.findUser(userDto);
		User user = userService.findUserByUserName(userDto);
		
		//验证新用户登录
		driver.findElement(By.linkText("注销")).click();
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys(userName);
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys(userName);
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("ixl4");
		driver.findElement(By.linkText("登录")).click();
		//验证登录成功
		assertEquals("Hi,管理员", driver.findElement(By.cssSelector("span.header_right")).getText());
		
		//使用超级管理员登录
		driver.findElement(By.linkText("注销")).click();
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys("admin");
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys("admin");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("h3u4");
		driver.findElement(By.linkText("登录")).click();
		
		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).click();;
		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).findElement(By.cssSelector("span")).click();
		//删除上面新建的用户
		driver.findElement(By.linkText("用户列表")).click();
		List<WebElement> rows = driver.findElement(By.xpath("//table[@class='datagrid-btable']")).
				findElements(By.xpath("//td/div[@class='datagrid-cell datagrid-cell-c1-userId']"));
		System.out.println("行数："+rows.size());
		for (WebElement row : rows) {
			BigDecimal userId = new BigDecimal(row.getText());
			System.out.println("userId:"+userId);
			if (user.getUserId().equals(userId)|| userError.getUserId().equals(userId)) {
				row.click();
				driver.findElement(By.cssSelector("span.l-btn-icon.icon-remove")).click();
			}
		}
	}
	
	private void addUser(String userName,String password,String dupPassowrd)	{
		driver.findElement(By.name("dto.userName")).clear();
		driver.findElement(By.name("dto.userName")).sendKeys(userName);
		driver.findElement(By.name("dto.name")).clear();
		driver.findElement(By.name("dto.name")).sendKeys(userName);
		driver.findElement(By.name("dto.password")).clear();
		driver.findElement(By.name("dto.password")).sendKeys(password);
		driver.findElement(By.name("dto.dupPassword")).clear();
		driver.findElement(By.name("dto.dupPassword")).sendKeys(dupPassowrd);
		driver.findElement(By.id("1")).click();
		driver.findElement(By.id("2")).click();
		driver.findElement(By.id("3")).click();
		driver.findElement(By.id("4")).click();
		driver.findElement(By.linkText("完成")).click();
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
