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
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class QuesRoomAddMessageTest extends TestCase{
	private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "http://192.168.16.238:8080/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testAddMessage() throws Exception {
	    driver.get(baseUrl + "/front/toLogin.htm");
	    driver.manage().window().maximize(); 
	    driver.findElement(By.name("customer.account")).clear();
	    driver.findElement(By.name("customer.account")).sendKeys("test");
	    driver.findElement(By.name("customer.password")).clear();
	    driver.findElement(By.name("customer.password")).sendKeys("test");
	    driver.findElement(By.id("checkCode")).clear();
	    driver.findElement(By.id("checkCode")).sendKeys("tglx");
	    driver.findElement(By.linkText("登录")).click();
	    driver.findElement(By.linkText("实盘房间")).click();
	    driver.findElement(By.id("actual_ta")).clear();
	    driver.findElement(By.id("actual_ta")).sendKeys("abcd");
	    driver.findElement(By.linkText("发言")).click();
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
