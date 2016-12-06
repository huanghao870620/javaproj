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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class AuditMessageTest extends TestCase {
	private WebDriver driver;
	private WebDriver back_driver;
	private WebDriver driver2;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		back_driver = new FirefoxDriver();
		
		//�ڶ����û�ʹ�ùȸ��¼
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		driver2 = new ChromeDriver(options);
		
//		driver2 = new FirefoxDriver();
		baseUrl = "http://192.168.10.203/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		back_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver2.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	//��Ϣ��˲���
	@Test
	public void testSendMsg() throws Exception {
		//ǰ̨�û���¼
		driver.get(baseUrl + "/front/toLogin.htm");
		driver.manage().window().maximize();
		driver.findElement(By.name("customer.account")).clear();
		driver.findElement(By.name("customer.account")).sendKeys("majiadan");
		driver.findElement(By.name("customer.password")).clear();
		driver.findElement(By.name("customer.password")).sendKeys("majiadan");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("iigd");
		driver.findElement(By.linkText("��¼")).click();
		
		//�ڶ����û���¼ǰ̨
		driver2.get(baseUrl + "/front/toLogin.htm");
		driver2.manage().window().maximize();
		driver2.findElement(By.name("customer.account")).clear();
		driver2.findElement(By.name("customer.account")).sendKeys("test");
		driver2.findElement(By.name("customer.password")).clear();
		driver2.findElement(By.name("customer.password")).sendKeys("test");
		driver2.findElement(By.id("checkCode")).clear();
		driver2.findElement(By.id("checkCode")).sendKeys("iigd");
		driver2.findElement(By.linkText("��¼")).click();

		//��̨�û���¼
		back_driver.get(baseUrl + "/back/toLogin.htm");
		back_driver.manage().window().maximize();
		back_driver.findElement(By.name("user.userName")).clear();
		back_driver.findElement(By.name("user.userName")).sendKeys("admin");
		back_driver.findElement(By.name("user.password")).clear();
		back_driver.findElement(By.name("user.password")).sendKeys("admin");
		back_driver.findElement(By.id("checkCode")).clear();
		back_driver.findElement(By.id("checkCode")).sendKeys("xk21");
		back_driver.findElement(By.linkText("��¼")).click();
		
		WebDriverWait wait = new WebDriverWait(back_driver,10);
		WebElement element = wait.until(new ExpectedCondition<WebElement>(){
        @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector("li.expandable"));
        	}
        });
		
		element.click();
		element.findElement(By.cssSelector("span")).click();
		back_driver.findElement(By.linkText("�������")).click();
		back_driver.findElement(By.linkText("���")).click();

		//ǰ̨�û�����������Ϣ
		driver.findElement(By.id("writeMsg1")).click();
		driver.findElement(By.id("writeMsg1")).sendKeys("abcd");
		driver.findElement(By.cssSelector("a.send-btn")).click();
		//�ж��Ƿ��ͳɹ�
		WebElement result = driver.findElement(By.xpath("//div[@class='detest']")).findElement(By.tagName("p"));
		assertEquals("abcd", result.getText());
		
		//��̨�û������Ϣ
		assertNotNull(back_driver.findElement(By.cssSelector("button[type=\"button\"]")));
		back_driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
		
		//�жϵڶ����û��Ƿ��յ���Ϣ
		WebElement result2 = driver2.findElement(By.xpath("//div[@class='detest']")).findElement(By.tagName("p"));
		assertEquals("abcd", result2.getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		back_driver.quit();
		driver2.quit();
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
