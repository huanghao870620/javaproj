package com.xa.junit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.xa.util.GetPinyin;
/**
 * 
 * @author burgess
 *
 */
public class XlsxTest {
	
	private WebConversation wc = new WebConversation();
	
	
	
	@Before
	public void Before(){
		HttpUnitOptions.setScriptingEnabled(false);
	}
	
	
	public static void main(String[] args) {
		XlsxTest xTest = new XlsxTest();
		xTest.Before();
		try {
			xTest.testReadXlsx();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 读取xlsx
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Test
	public void testReadXlsx() throws IOException, SAXException{
		long sum=0;
		File file=new File("d:/doc/msg.xlsx");
		InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        // 获取每一个工作薄
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // 获取当前工作薄的每一行
//            for (int rowNum = 5000; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            for(int rowNum=xssfSheet.getLastRowNum();rowNum>=1;rowNum--){
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell one = xssfRow.getCell(0);
                    //读取第一列数据
                    XSSFCell two = xssfRow.getCell(1);
                    //读取第二列数据
                    XSSFCell three = xssfRow.getCell(2);
                    //读取第三列数据

                    String userName= one.toString();
                    String phone= two.toString();
                    String password= three.toString();
                    
//                    userName = new String(userName.getBytes("UTF-8"),"GBK");
//                    phone = new String(phone.getBytes("iso-8859-1"),"utf-8");
//                    password = new String(password.getBytes("iso-8859-1"),"utf-8");
//                    userName= URLEncoder.encode(userName,"UTF-8");
                    int sp= password.indexOf(".");
                    password= password.substring(0, sp);
                    
                    
                    PostMethodWebRequest req = new PostMethodWebRequest("http://www.xunanbuy.com/Register/regDo.html",true);
                    userName=GetPinyin.getPingYin(userName);
//                    userName="aabbcdaac112";
            		req.setParameter("user_name", userName);
            		req.setParameter("password", password);
            		req.setParameter("not_password", password);
            		req.setParameter("mobile_phone", phone);
            		req.setParameter("recommend", "");
            		req.setParameter("agree", "1");
            		req.setParameter("type", "0");
            		
//            		req.setHeaderField("Accept-Charset", "gb2312,utf-8;q=0.7,*;q=0.7");
//            		req.setHeaderField("Content-type", "application/x-www-form-urlencoded;charset:UTF-8");
//            		try {
//						Thread.sleep(20);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
            		
            		WebResponse response=null;
            		try{
            			response = wc.getResponse(req);
            		}catch(Exception e){
            			System.out.println("rowNum : " + rowNum + " error!");
            			continue;
            		}
            		String msg = response.getText();	
            		System.out.println(msg);
//            		String s="﻿[[\"submits\",true,\"REGISTEREDSUCCESS\",\"/Member/question.html\",\"\"]]";
            		String arrs[]= msg.split(",");
//            		System.out.println(arrs[1]);
            		System.out.println("rowNum : " + rowNum);
            		String ret=arrs[1];
//            		if(ret.equals("true")){
//            			sum++;
//            		}else {
//            			
//            			System.out.println(userName+"注册失败");
//					}
                    
                }
            }
        }
         System.out.println("sum : " + sum);
	}
	
	  //转换数据格式
    private String getValue(XSSFCell xssfRow) {

        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }
}
