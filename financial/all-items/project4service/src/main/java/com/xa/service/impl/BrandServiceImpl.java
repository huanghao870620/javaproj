package com.xa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
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
	
	@Autowired
	private GoodsMapper goodsMapper;
	
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
	
	/**
	 * 根据品牌id获取商品
	 * @return
	 */
	public String getGoodsByBrandId(Long brandId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		   "brandId"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}

		List<Goods> list= this.goodsMapper.getGoodsByBrandId(brandId);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			Goods good= list.get(i);
			JSONObject goodObj = new JSONObject();
			float price= good.getPrice();
			String name= good.getName();
			Map<String, Object> mapPic = new HashMap<String,Object>();
			mapPic.put("goodId", good.getId());
			mapPic.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
			
			List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapPic);
			com.xa.entity.File thumbFile=fileList.get(0);
			String uriPath= thumbFile.getUriPath();
			goodObj.accumulate("name", name).accumulate("price", price).accumulate("uriPath", uriPath);
			array.add(goodObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
}
