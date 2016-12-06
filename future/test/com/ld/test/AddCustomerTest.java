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
		driver.findElement(By.linkText("��¼")).click();

		assertEquals("Hi,����Ա", driver.findElement(By.cssSelector("span.header_right")).getText());

		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).click();
		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).findElement(By.cssSelector("span")).click();
		driver.findElement(By.linkText("�ͻ�����")).click();

		// �ظ��������
		addCustomer2("account");
		addCustomer2("account");
		assertEquals("���˻��Ѿ�����!", driver.findElement(By.xpath("//ul[@class='errorMessage']"))
				.findElement(By.cssSelector("li.span")).getText());
		// ɾ�������½��Ŀͻ�
		List<WebElement> rows = driver.findElement(By.xpath("//table[@class='datagrid-btable']"))
				.findElements(By.xpath("//td/div[@class='datagrid-cell datagrid-cell-c1-account']"));
		System.out.println("������" + rows.size());
		for (WebElement row : rows) {
			if (row.getText().equals("account")) {
				row.click();
				driver.findElement(By.cssSelector("span.l-btn-left.l-btn-icon-left")).click();
				break;
			}
		}

		addCustomer(1, "��������");
		addCustomer(2, "��������Ա");
		addCustomer(3, "��ʦ");
		addCustomer(4, "��ʦ����");
		addCustomer(5, "��������");
		addCustomer(6, "��������Ա");
		addCustomer(7, "��ʦ");
		addCustomer(8, "��ʦ����");
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
		driver.findElement(By.linkText("���")).click();

		// ��֤�ͻ��Ƿ���ӳɹ�
//		Customer customer = new Customer();
//		customer.setAccount(account);
//		assertTrue(customerService.accountExists(customer));

		// ��֤���û���¼
		driver.findElement(By.linkText("ע��")).click();
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys(account + "_" + levelId);
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys(account + "_" + levelId);
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("7y94");
		driver.findElement(By.linkText("��¼")).click();
		assertEquals("Hi,����Ա", driver.findElement(By.cssSelector("span.header_right")).getText());

		// ʹ�ó�������Ա��¼
		driver.findElement(By.linkText("ע��")).click();
		driver.findElement(By.name("user.userName")).clear();
		driver.findElement(By.name("user.userName")).sendKeys("admin");
		driver.findElement(By.name("user.password")).clear();
		driver.findElement(By.name("user.password")).sendKeys("admin");
		driver.findElement(By.id("checkCode")).clear();
		driver.findElement(By.id("checkCode")).sendKeys("h3u4");
		driver.findElement(By.linkText("��¼")).click();

		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).click();
		driver.findElement(By.cssSelector("li.expandable.lastExpandable")).findElement(By.cssSelector("span")).click();
		driver.findElement(By.linkText("�ͻ�����")).click();

		// ɾ�������½��Ŀͻ�
		List<WebElement> rows = driver.findElement(By.xpath("//table[@class='datagrid-btable']"))
				.findElements(By.xpath("//td/div[@class='datagrid-cell datagrid-cell-c1-account']"));
		System.out.println("������" + rows.size());
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
		new Select(driver.findElement(By.name("customer.levelId"))).selectByVisibleText("�ο�");
		new Select(driver.findElement(By.id("selectRole"))).selectByVisibleText("��������");
		driver.findElement(By.linkText("���")).click();
	}
	
	private String getLevelText(int levelId){
		String levelText = "�ο�";
		switch (levelId) {
		case 1:
			levelText = "�ο�";
			break;
		case 2:
			levelText = "��ͨ";
			break;
		case 3:
			levelText = "����";
			break;
		case 4:
			levelText = "�ƽ�";
			break;
		case 5:
			levelText = "����";
			break;
		case 6:
			levelText = "VIP";
			break;
		case 7:
			levelText = "��ʯ";
			break;
		case 8:
			levelText = "����";
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
