package com.xa.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meterware.httpunit.javascript.JavaScript.Image;
import com.xa.compare.CompareCountry;
import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.mapper.CountryMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.CountryService;
import com.xa.util.Constants;
import com.xa.util.GetPinyin;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
@Transactional
public class CountryServiceImpl extends BaseServiceImpl<Country, CountryMapper> implements CountryService<Country> {
	
	@Autowired
	private FileMapper fileMapper;

	/**
	 * 获取所有国家
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllCountry(String random,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<Country> countryList = this.m.findAll();
		JSONArray array = new JSONArray();
		for(int i=0;i<countryList.size();i++){
			Country country = countryList.get(i);
			JSONObject countryObj = new JSONObject();
			String name = country.getName();
			Long imgId = country.getImgId();
			File img = this.fileMapper.selectByPrimaryKey(imgId);
			countryObj.accumulate("name", name).accumulate("imgPath", img.getUriPath())
			.accumulate("id", country.getId())
			.accumulate("countryCode", country.getCountryCode())
			;
			array.add(countryObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
	
	
	/**
	 * 以排序方式获取所有国家
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination 
	 */
	public String getAllCountryBySort(String random,String sign) throws BadHanyuPinyinOutputFormatCombination{
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<Country> countries= this.m.findAll();
		Country cArr[] = new Country[countries.size()];
		countries.toArray(cArr);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<cArr.length;i++){
			Country country=countries.get(i);
			String name= country.getName();
			String headWord= name.substring(0, 1);
			sb.append(headWord);
		}
		
		String headCharStr= GetPinyin.getPinYinHeadChar(sb.toString());
		char headCharArr[]= headCharStr.toCharArray();
		
		List<CompareCountry> ccList=new ArrayList<CompareCountry>();
		for(int i=0;i<headCharArr.length;i++){
			Country  country =countries.get(i);
			CompareCountry compareCountry = new CompareCountry(headCharArr[i], country.getName(),country.getImgId(),country.getId());
			ccList.add(compareCountry);
		}
		Collections.sort(ccList);
		
		Map<Character, List<Country>> group = new LinkedHashMap<Character,List<Country>>();
		for(int i=0;i<ccList.size();i++){
			CompareCountry cc=ccList.get(i);
			Character key= cc.getHeadChar();
			String name = cc.getName();
			List<Country> vaList= group.get(key);
			if(vaList==null){
				vaList = new ArrayList<Country>();
			}
			
			Country dCountry = new Country();
			dCountry.setName(name);
			dCountry.setId(cc.getCountryId());
			dCountry.setImgId(cc.getImgId());
			vaList.add(dCountry);
			group.put(key, vaList);
		}
		
		
		JSONArray data = new JSONArray();
		for(Character c:group.keySet()){
			JSONObject node = new JSONObject();
			JSONArray array = new JSONArray();
			List<Country> nameList=group.get(c);
			for(int i=0;i<nameList.size();i++){
				JSONObject nameObj = new JSONObject();
				Country d2Country = nameList.get(i);
				Long imgId=d2Country.getImgId();
				File file= this.fileMapper.selectByPrimaryKey(imgId);
				nameObj.accumulate("name", d2Country.getName())
				.accumulate("imgPath", file.getUriPath())
				.accumulate("countryId", d2Country.getId());
				;
				array.add(nameObj);
			}
			node.accumulate("letter", c).accumulate("countryList", array);
			data.add(node);
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.DATA, data)
		;
		return object.toString();
	}
	
}
