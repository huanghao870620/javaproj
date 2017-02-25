package com.xa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Brand;
import com.xa.entity.DebrisSession;
import com.xa.entity.DsGood;
import com.xa.entity.FastBuySession;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.DsGoodMapper;
import com.xa.mapper.FastBuySessionMapper;
import com.xa.mapper.FbsDsMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.service.FastBuySessionService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class FastBuySessionServiceImpl extends BaseServiceImpl<FastBuySession, FastBuySessionMapper>
		implements FastBuySessionService<FastBuySession> {

	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private  FbsDsMapper  fbsDsMapper;
	
	@Autowired
	private DsGoodMapper dsGoodMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private BrandMapper brandMapper;
	
	/**
	 * 获取秒杀专场详情
	 * @param fsbId
	 * @param sign
	 * @return
	 */
	public String getFBSDetail(Long fsbId,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"fsbId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG,Msg.NOT_PERMISSION).toString();
		}
		
		FastBuySession fastBuySession=this.m.selectByPrimaryKey(fsbId);
		float discount= fastBuySession.getDiscount();
		Date endTime= fastBuySession.getEndTime();
		
		
		List<String> dates= this.fbsDsMapper.getAllDateByfbsId(fsbId);
		JSONArray array = new JSONArray();
		for(int i=0;i<dates.size(); i++){
			JSONObject dsObject = new JSONObject();
			String date= dates.get(i);
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("fbsId", fsbId);
			map.put("date", date);
			List<DebrisSession> dsList= this.fbsDsMapper.getCronTimeByFbsIdAndDate(map);
			
			
			String nextDate="";
			List<DebrisSession> nextDsList=null;
			if(i+1<=dates.size()-1){
				nextDate= dates.get(i+1);
				Map<String, Object> nextMap = new HashMap<String,Object>();
				nextMap.put("fbsId", fsbId);
				nextMap.put("date", nextDate);
				nextDsList= this.fbsDsMapper.getCronTimeByFbsIdAndDate(nextMap);
			}
			
			
			
			JSONArray dsArray = new JSONArray();
			for(int j=0;j<dsList.size(); j++){
				 JSONObject dsObj = new JSONObject();
				 DebrisSession ds= dsList.get(j);
				 Long dsId= ds.getId();
				 
				 List<DsGood> dgList= this.dsGoodMapper.getDSGoodByDSID(dsId);
				 Long goodId=null;
				 if(null != dgList && dgList.size() > 0){
					 DsGood dg= dgList.get(0);
					 goodId=dg.getGoodId();
					 Long dgId= dg.getId();
					 Goods goods= this.goodsMapper.selectByPrimaryKey(goodId);
					 Map<String, Object> mapParam = new HashMap<String,Object>();
					 mapParam.put("goodId", goodId);
					 mapParam.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
					
					 List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapParam );
					 if(null != fileList && fileList.size() > 0){
						File goodFile= fileList.get(0);
						String uriPath= goodFile.getUriPath();
						if(!StringUtils.isBlank(uriPath)){
							dsObj.accumulate("uriPath", uriPath);
						}
					}
					
					Float price= goods.getPrice();
					String goodName= goods.getName();
					Long brandId= goods.getBrandId();
					String brandName="";
					if(null != brandId){
						Brand brand= this.brandMapper.selectByPrimaryKey(brandId);
						brandName= brand.getName();
					}
					
					Float disPrice= price*discount;
					  Integer inventory= dg.getInventory();
					  dsObj.accumulate("inventory", inventory)
					  .accumulate("dgId", dgId)
					  .accumulate("price", price)
					  .accumulate("disPrice", disPrice)
					  .accumulate("discount", discount*10)
					  .accumulate("goodName", goodName)
					  .accumulate("brandName", brandName)
					  ;
				 }
				 Date cronTime= ds.getCronTime();
				 long timeDiff=  new Date().getTime() - cronTime.getTime();
				 
				 long nextTimeDiff=-1;
				 if(j+1 != dsList.size()){
					 DebrisSession dsNext= dsList.get(j+1);
					 Date nextCronTime= dsNext.getCronTime();
					 nextTimeDiff= new Date().getTime() - nextCronTime.getTime();
				 }else {
					 if(null != nextDsList && nextDsList.size() > 0){
						 DebrisSession nextDs= nextDsList.get(0);
						 Date nextCronTime= nextDs.getCronTime();
						 nextTimeDiff = new Date().getTime() - nextCronTime.getTime();
					 }else {
						 nextTimeDiff = new Date().getTime() - fastBuySession.getEndTime().getTime();
					}
				 }
				 
				 String cronTimeStr= DateFormatUtils.format(cronTime, "HH:mm");
				 
				 dsObj.accumulate("dsId", dsId)
				 .accumulate("goodId", goodId)
				 .accumulate("cronTime", cronTimeStr)
				 .accumulate("timeDiff", timeDiff)
				 .accumulate("nextTimeDiff", nextTimeDiff)
				 ;
				 dsArray.add(dsObj);
			}
			dsObject.accumulate(date, dsArray);
			array.add(dsObject);
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.DATA, array)
		;
		return object.toString();
	}
	
	/**
	 * 获取所有活动
	 * @return
	 */
	public String getFBS(String random, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<FastBuySession> fbsList= this.m.findAll();
		JSONArray array = new JSONArray();
		for(int i=0;i<fbsList.size(); i++){
			 JSONObject fbsObj = new JSONObject();
			 FastBuySession fbs= fbsList.get(i);
			 fbs.getStartTime();
			 fbs.getEndTime();
			 Long imgAdvId= fbs.getImgAdvId();
			 File imgFile= this.fileMapper.selectByPrimaryKey(imgAdvId);
			 Long id= fbs.getId();
			 fbsObj.accumulate("id", id)
			 .accumulate("uriPath", imgFile.getUriPath());
			 array.add(fbsObj);
		}
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.DATA, array)	
		;
		return object.toString();
	}
}
