package com.ld.test;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import junit.framework.TestCase;

public class QuesRoomAddViewTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		driver = new ChromeDriver(options);
		baseUrl = "http://192.168.10.203/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAddView() throws Exception {
		driver.get(baseUrl + "/front/toLogin.htm");
		driver.manage().window().maximize();
		driver.findElement(By.name("customer.account")).clear();
		driver.findElement(By.name("customer.account")).sendKeys("teacher");
		driver.findElement(By.name("customer.password")).clear();
		driver.findElement(By.name("customer.password")).sendKeys("teacher");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("abcd");
		driver.findElement(By.linkText("登录")).click();
		driver.findElement(By.linkText("实盘房间")).click();
		driver.findElement(By.linkText("添加观点")).click();
		driver.findElement(By.id("content")).clear();
		driver.findElement(By.id("content")).sendKeys("test");
//		driver.findElement(By.id("selectpic_a")).click();
//		driver.findElement(By.id("selectpic_a")).sendKeys("G:\\bug\\test.png");
		driver.findElement(By.linkText("发表")).click();
		driver.findElement(By.linkText("删除")).click();
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
