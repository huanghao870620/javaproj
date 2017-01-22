package com.xa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.service.BrandService;
import com.xa.service.impl.BaseServiceImpl;
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
		this.logger.debug("sign : " + sign);
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
	 		String uriPath = "";
	 		if(img !=null){
	 			uriPath=img.getUriPath();
	 		}
	 		brandObj.accumulate("name", name).accumulate("imgPath",uriPath )
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
	public String getGoodsByBrandId(Long brandId,Integer pageNum,Integer pageSize, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		   "brandId","pageNum","pageSize"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}

		Brand brand = this.m.selectByPrimaryKey(brandId);
		Long dp= brand.getDetailPic();
		Long imgId=brand.getImgId();
		String info= brand.getInfo();
		String brandName= brand.getName();
		
		object.accumulate("info", info);
		object.accumulate("brandName", brandName);
		
		if(null != imgId){
			 File file= fileMapper.selectByPrimaryKey(imgId);
			 object.accumulate("imgUriPath", file.getUriPath());
		}
		
		if(null != dp){
			 File dpFile= fileMapper.selectByPrimaryKey(dp);
			 object.accumulate("detailPicUriPath", dpFile.getUriPath());
		}
		
		
		PageHelper.startPage(pageNum, pageSize,true);
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
			com.xa.entity.File thumbFile = null;
			String uriPath = null;
			if(null != fileList && fileList.size() > 0){
				thumbFile=fileList.get(0);
				uriPath= thumbFile.getUriPath();
			}
			goodObj.accumulate("name", name)
			.accumulate("goodId", good.getId())
			.accumulate("price", price).accumulate("uriPath", uriPath==null ? "" : uriPath);
			array.add(goodObj);
		}
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.DATA, array);
		return object.toString();
	}
}
