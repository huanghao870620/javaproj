package com.xa.junit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.MyMember;
import com.xa.service.MyMemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class MyMemberTest {

	@Autowired
	private MyMemberService<MyMember> myMemberService;
	
	@Test
	public void testGetAllMember(){
		List<MyMember> list=this.myMemberService.getAllMember();
		MyMember member=list.get(0);
		Long time = new Long(member.getRegTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		time=time*1000;
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(time);
		Date date=cal.getTime();
		String datestr= sdf.format(date);
		System.out.println(datestr);
		
	}
	
	/**
	 * 添加会员
	 */
	@Test
	public void testAddMember(){
		MyMember member = new MyMember();
//		member.setUserName("小红");
//		member.setPassword("123");
//		member.setMobilePhone("18215563225");
//		member.setUserType(false);
		
		
		member.setAddress("");
		member.setAnswer("");
		member.setCity(0);
		member.setCountry(0);
		member.setDistrict(0);
		member.setFrozenFunds(BigDecimal.ZERO);
		member.setGlanceCount(0);
		member.setHeadPic("");
		member.setIdBehind("0");
		member.setIdentification("");
		member.setIdPositive("0");
		member.setIsLock(false);
		member.setLastIp("");
		member.setLastTime(0);
		member.setLoginCount(0);
		member.setMemberFunds(BigDecimal.ZERO);
		member.setMobilePhone("18532265565");
		member.setNewInfo(true);
		member.setNickname("");
		member.setPassword("1312412321");
		member.setPayPoints(0);
		member.setProvince(0);
		member.setQq("");
		member.setQqKey("");
		member.setQuestion("");
		member.setRankPoints(0);
		member.setRecommend("");
		member.setRegEmail("");
		member.setRegTime(0);
		member.setRemarks("");
		member.setSha1Random((short)0);
		member.setSinaKey("");
		member.setTelephone("18532265565");
		member.setTempLoginCount((short) 0);
		member.setTempLoginTime(0);
		member.setUserName("sasad");
		member.setUserType(false);
		member.setWechat("");
		member.setZip(0);
		this.myMemberService.addMember(member);
	}
	
	
	@Test
	public void testReadTxt(){
		String filePath="D:\\doc\\xunan\\user.txt";
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
                      
                     Random random=  new Random(System.currentTimeMillis()/1000 - regTimeInt);
                      
                      MyMember member = new MyMember();
	              		member.setAddress("");
	              		member.setAnswer("");
	              		member.setCity(0);
	              		member.setCountry(0);
	              		member.setDistrict(0);
	              		member.setFrozenFunds(BigDecimal.ZERO);
	              		member.setGlanceCount(glanceCount);
	              		member.setHeadPic("");
	              		member.setIdBehind("0");
	              		member.setIdentification("");
	              		member.setIdPositive("0");
	              		member.setIsLock(false);
	              		member.setLastIp("");
	              		 
	              		member.setLastTime(0);
	              		member.setLoginCount(loginCount);
	              		member.setMemberFunds(BigDecimal.ZERO);
	              		member.setMobilePhone(mobile);
	              		member.setNewInfo(true);
	              		member.setNickname("");
	              		member.setPassword(username+mobile+"123");
	              		member.setPayPoints(0);
	              		member.setProvince(0);
	              		member.setQq("");
	              		member.setQqKey("");
	              		member.setQuestion("");
	              		member.setRankPoints(0);
	              		member.setRecommend("");
	              		member.setRegEmail("");
	              		member.setRegTime(0);
	              		member.setRemarks("");
	              		member.setSha1Random((short)0);
	              		member.setSinaKey("");
	              		member.setTelephone(mobile);
	              		member.setTempLoginCount((short) 0);
	              		member.setTempLoginTime(0);
	              		member.setUserName(username);
	              		member.setUserType(false);
	              		member.setWechat("");
	              		member.setZip(0);
	              		this.myMemberService.addMember(member);
//	              		break;
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
