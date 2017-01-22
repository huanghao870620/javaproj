package com.xa.junit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Area;
import com.xa.mapper.AreaMapper;

/**
 * 
 * @author burgess
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class OrderTest {

	
	@Autowired
	private AreaMapper areaMapper;
	

	/**
	 * 处理xml
	 * @throws DocumentException
	 */
	@Test
	public void testDealXML() throws DocumentException{
		String xml= "<xml>"
		  +"<appid><![CDATA[wx4a8459702c7f3962]]></appid>"  //应用id
		  +"<bank_type><![CDATA[CFT]]></bank_type>"  //付款银行
		  +"<cash_fee><![CDATA[1]]></cash_fee>"     //现金支付金额
		  +"<fee_type><![CDATA[CNY]]></fee_type>"   //货币类型
		  +"<is_subscribe><![CDATA[N]]></is_subscribe>"  //是否关注公众账户
		  +"<mch_id><![CDATA[1417930102]]></mch_id>"   //商户号
		  +"<nonce_str><![CDATA[0BRI1B8QZJUQMN0]]></nonce_str>"   //随机字符串
		  +"<openid><![CDATA[oSp9PxNKyEVJQzQOCSG5ht-Tc59k]]></openid>"   //用户标识
		  +"<out_trade_no><![CDATA[1481462114]]></out_trade_no>"     //商户订单号
		  +"<result_code><![CDATA[SUCCESS]]></result_code>"     //返回状态码
		  +"<sign><![CDATA[CAFE8CAE0EB2D762F93182EF76748141]]></sign>"   //签名
		  +"<time_end><![CDATA[20161211211521]]></time_end>"  //支付完成时间
		  +"<total_fee>1</total_fee>"       //总金额
		  +"<trade_type><![CDATA[APP]]></trade_type>"    //交易类型
		  +"<transaction_id><![CDATA[4000932001201612112500266793]]></transaction_id>"
		 +"</xml>";
		
		Document document= DocumentHelper.parseText(xml);
		Element rootEle= document.getRootElement();
		String typeName= rootEle.getNodeTypeName();
		System.out.println(typeName);
		Object data= rootEle.getData();
		System.out.println(data);
		String name= rootEle.getName();
		System.out.println(name);
		Element appidEle= rootEle.element("appid");
		String text= appidEle.getText();
		System.out.println(text);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	@Test
	public void parseXmlFile() throws IOException, DocumentException{
		   File file = new File("D:/doc/ab.xml");
		   StringBuilder result = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
	        Document document= DocumentHelper.parseText(result.toString());
			Element rootEle= document.getRootElement();
			String typeName= rootEle.getNodeTypeName();
			Object data= rootEle.getData();
			String name= rootEle.getName();
			Element province= rootEle.element("dict");
			
			List<Element> eleList= province.elements("key");
			List<Element> provinceChild = province.elements("array");
			 System.out.println("size : " + eleList.size());
			 
			 for(int i=0; i<eleList.size(); i++){
				   Element element= eleList.get(i);
				   Element cityArray = provinceChild.get(i);
				   String text= element.getText();
				   System.out.println(text);
				   Area prov = new Area();
				   prov.setName(text);
				   prov.setPid(1L);
				   this.areaMapper.insert(prov);
				   
				   
				   List<Element> citys= cityArray.element("dict").elements("key");
				   List<Element> cityChild=  cityArray.element("dict").elements("array");
				   for(int j=0;j<citys.size();j++){
					     Element city= citys.get(j);
					     Element areaArray= cityChild.get(j);
						 String cityText= city.getText();
						 System.out.println("\t" + cityText);
						 Area cityArea = new Area();
						 cityArea.setName(cityText);
						 cityArea.setPid(prov.getId());
						 this.areaMapper.insert(cityArea);
						 
						List<Element> areas= areaArray.elements("string");
						for(int k=0;k<areas.size(); k++){
							Element area= areas.get(k);
							String areaText= area.getText();
							System.out.println("\t\t"+areaText);
							Area area2 = new Area();
							area2.setName(areaText);
							area2.setPid(cityArea.getId());
							this.areaMapper.insert(area2);
						}
						  
				   }
			 }
			
	}
}
