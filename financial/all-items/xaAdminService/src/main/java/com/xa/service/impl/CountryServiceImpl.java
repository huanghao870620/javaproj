package com.xa.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import org.apache.poi.ss.formula.functions.Count;
import org.apache.xpath.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.meterware.httpunit.javascript.JavaScript.Image;
import com.xa.compare.CompareCountry;
import com.xa.entity.Country;
import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.entity.UploadType;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.CountryMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.CountryService;
import com.xa.service.FileService;
import com.xa.service.impl.BaseServiceImpl;
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

	
	public String  getCountrysByPaging(Integer page, Integer rows){
		JSONObject object = new JSONObject();
		PageHelper.startPage(page, rows);
		Page<Country> countryPage = (Page<Country>) this.m.findAll();
		List<Country> countryList= countryPage.getResult();
		long total = countryPage.getTotal();
		JSONArray array = new JSONArray();
		for(int i=0;i<countryList.size();i++){
			JSONObject brandObj = new JSONObject();
			Country country= countryList.get(i);
			String name= country.getName();
			Long id= country.getId();
			Long imgId= country.getImgId();
			 String countryCode= country.getCountryCode();
			File img= this.fileMapper.selectByPrimaryKey(imgId);
			brandObj
			.accumulate("name", name)
			.accumulate("countryCode", countryCode)
			.accumulate("id", id)
			.accumulate("img", img.getUriPath())
			;
			array.add(brandObj);
		}
		object.accumulate("total", total).accumulate("rows", array);
		return object.toString();
	}
	

	/**
	 * 获取所有国家
	 * @param mav
	 */
	public void getAllCountry(ModelAndView mav){
		 mav.addObject("countrys", this.m.findAll());
	}
	
	/**
	 * 添加国家
	 * @param country
	 * @param imgFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addCountry(Country country, MultipartFile imgFile, FileService<File> fileService) throws IllegalStateException, IOException{
		File file = new File();
		fileService.uploadFile(imgFile, PhotoType.COUNTRY_PHOTO, file );
		country.setImgId(file.getId());
		this.m.insert(country);
	}
	
	/**
	 * 编辑国家
	 * @param country
	 * @param imgFile
	 * @param fileService
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void editCountry(Country country,MultipartFile imgFile,FileService<File> fileService) throws IllegalStateException, IOException{
		    File file = new File();
		    file.setId(country.getImgId());
			fileService.editFile(imgFile, PhotoType.COUNTRY_PHOTO, file );
			this.m.updateByPrimaryKeySelective(country);
	}
	
	/**
	 * 根据id获取国家
	 * @param modelAndView
	 * @param id
	 */
	public void getCountryById(ModelAndView modelAndView, Long id){
		  Country country= this.m.selectByPrimaryKey(id);
		  Long imgId= country.getImgId();
		  File file = this.fileMapper.selectByPrimaryKey(imgId);
		  modelAndView.addObject("file", file);
		  modelAndView.addObject("country", country);
	}
}
