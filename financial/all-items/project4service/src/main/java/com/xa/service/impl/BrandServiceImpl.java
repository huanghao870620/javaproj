package com.xa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.BrandService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand, BrandMapper> implements BrandService<Brand> {

	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	/**
	 * 获取所有品牌
	 * @return
	 */
	public String getBrands(String random,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
	 	List<Brand> brandList = this.brandMapper.findAll();
	 	JSONArray array = new JSONArray();
	 	for(int i=0;i<brandList.size();i++){
	 		JSONObject brandObj = new JSONObject();
	 		Brand brand = brandList.get(i);
	 		String name = brand.getName();
	 		Long imgId = brand.getImgId();
	 		File img= this.fileMapper.selectByPrimaryKey(imgId);
	 		brandObj.accumulate("name", name).accumulate("imgPath", img.getUriPath())
	 		.accumulate("id", brand.getId());
	 		array.add(brandObj);
	 	}
	 	object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
}
