package com.xa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xa.dto.HotSearchDto;
import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.GoodsSearchRecord;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.GoodsSearchRecordMapper;
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
	
	@Autowired
	private GoodsSearchRecordMapper goodsSearchRecordMapper;
	
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
	public String getGoodsByBrandId(Long brandId,String nameS,Long buyerId,Integer pageNum,Integer pageSize, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		   "brandId","pageNum","pageSize","nameS","buyerId"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		if(!StringUtils.isBlank(nameS)){
			GoodsSearchRecord record = new GoodsSearchRecord();
			record.setAddTime(new Date());
			record.setName(nameS);
			record.setBuyerId(buyerId);
			goodsSearchRecordMapper.insert(record );
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
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("brandId", brandId);
		if(!StringUtils.isBlank(nameS)){
			map.put("nameS", nameS);
		}
		List<Goods> list= this.goodsMapper.getGoodsByBrandId(map );
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
		
		
		List<GoodsSearchRecord> gsrList= this.goodsSearchRecordMapper.getGSRByBuyerId(buyerId);
		JSONArray gsrArray = new JSONArray();
		for(int i=0; i<gsrList.size(); i++){
				GoodsSearchRecord gsr= gsrList.get(i);
				JSONObject gsrObj = new JSONObject();
				gsr.getAddTime();
				String historySearchRecord= gsr.getName();
				gsrObj.accumulate("historySearchRecord", historySearchRecord);
				gsrArray.add(gsrObj);
		}
		
		List<HotSearchDto> hsdList= this.goodsSearchRecordMapper.getHotSearch();
		JSONArray hsdArray = new JSONArray();
		for(int i=0;i<hsdList.size();i++){
			JSONObject hsdObj = new JSONObject();
			HotSearchDto hotSearchDto= hsdList.get(i);
			String name= hotSearchDto.getName();
			hotSearchDto.getTotal();
			hsdObj.accumulate("name", name);
			hsdArray.add(hsdObj);
		}
		
		
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.DATA, array)
		.accumulate("gsrArray", gsrArray)
		.accumulate("hsdArray", hsdArray)
		;
		return object.toString();
	}
}
