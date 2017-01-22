package com.xa.junit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Random;

import org.apache.http.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Buyers;
import com.xa.entity.MyMember;
import com.xa.service.BuyersService;
import com.xa.util.EncryptionTool;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class BuyerTest {

	@Autowired
	private BuyersService<Buyers> buyersService;
	
	/**
	 * 
	 */
	@Test
	public void testGetVercode() {
	 	String sign= Security.getSign(new String[]{
				 "mobile"
		});
	 	
		Buyers buyers = new Buyers();
		buyers.setMobile("18217742115");
		try {
			String text = this.buyersService.getVercode(buyers , sign);
			System.out.println(text);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRegister() {
		String sign = Security.getSign(new String[]{
				"mobile","vercode","password"
		});
		String vercode = "232312";
		Buyers buyer = new Buyers();
		buyer.setMobile("18217742115");
		buyer.setPassword("123456");
		this.buyersService.register(buyer , vercode, sign);
	}
	
	/**
	 * 测试登录
	 */
	@Test
	public void testLogin() {
		Buyers buyers = new Buyers();
		buyers.setMobile("18217742115");
		buyers.setPassword("123456");
		String sign = Security.getSign(new String[]{
				"mobile","password"
		});
		this.buyersService.login(buyers , sign);
	}
	
	
	@Test
	public void testReadTxt(){
		String filePath="D:\\doc\\xunan\\user5000.txt";
		   try {
               String encoding="GBK";
               File file=new File(filePath);
               if(file.isFile() && file.exists()){ //判断文件是否存在
                   InputStreamReader read = new InputStreamReader(
                   new FileInputStream(file),encoding);//考虑到编码格式
                   BufferedReader bufferedReader = new BufferedReader(read);
                   String lineTxt = null;
                   while((lineTxt = bufferedReader.readLine()) != null){
                       System.out.println(lineTxt);
                      String arr[]= lineTxt.split("\\s+");
                      String username=arr[0].trim();
                      String mobile = arr[1].trim();
                      String regTime = arr[2].trim();
                      int regTimeInt = Integer.valueOf(regTime.trim());
                      int loginCount = Integer.valueOf(arr[3].trim());
                      int glanceCount = Integer.valueOf(arr[4].trim());
                      
                      Buyers buyers = new Buyers();
                      buyers.setBuyerType(2);
                      buyers.setMobile(mobile);
                      buyers.setPassword(username);
                      buyers.setPassword(EncryptionTool.addSaltEncrypt(buyers.getPassword(), Security.getPasswordSalt()));
                      this.buyersService.insert(buyers);
                      
                   }
                   read.close();
       }else{
           System.out.println("找不到指定的文件");
       }
       } catch (Exception e) {
           System.out.println("读取文件内容出错");
           e.printStackTrace();
       }
	}
}
