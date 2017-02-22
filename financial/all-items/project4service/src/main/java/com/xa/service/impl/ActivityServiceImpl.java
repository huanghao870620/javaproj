package com.xa.service.impl;

import java.util.List;

import org.aspectj.weaver.bcel.AtAjAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Activity;
import com.xa.entity.ActivityAssociated;
import com.xa.entity.ActivityFile;
import com.xa.entity.Brand;
import com.xa.entity.Classification;
import com.xa.entity.Country;
import com.xa.entity.FastBuySession;
import com.xa.entity.File;
import com.xa.enumeration.AOrFType;
import com.xa.enumeration.ActivityType;
import com.xa.mapper.ActivityAssociatedMapper;
import com.xa.mapper.ActivityFileMapper;
import com.xa.mapper.ActivityMapper;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.ClassificationMapper;
import com.xa.mapper.CountryMapper;
import com.xa.mapper.FastBuySessionMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.ActivityService;
import com.xa.service.BrandService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class ActivityServiceImpl extends BaseServiceImpl<Activity, ActivityMapper>
		implements ActivityService<Activity> {
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private ActivityAssociatedMapper activityAssociatedMapper;
	
	@Autowired
	private ActivityFileMapper activityFileMapper;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private CountryMapper countryMapper;
	
	@Autowired
	private ClassificationMapper classificationMapper;
	
	@Autowired
	private FastBuySessionMapper fastBuySessionMapper;
	
	
	
	

	/**
	 * 获取活动轮播图
	 * @return
	 */
	public String getActivityShufflingFigure(String random,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		List<Activity> activities=  this.m.findAll();
		JSONArray array = new JSONArray();
		for(int i=0; i<activities.size(); i++){
			JSONObject activityObj = new JSONObject();
			Activity activity = activities.get(i);
			Long imgAdvId= activity.getImgAdvId();
			Long id= activity.getId();
			File file= this.fileMapper.selectByPrimaryKey(imgAdvId);
			activityObj.accumulate("id", id)
			.accumulate("uriPath", file.getUriPath())
			.accumulate("type", AOrFType.ACTIVTY.getValue())//活动
			;
			array.add(activityObj);
		}
		
		
		
		List<FastBuySession> fbsList= this.fastBuySessionMapper.findAll();
		for(int i=0;i<fbsList.size(); i++){
			 JSONObject fbsObj = new JSONObject();
			 FastBuySession fbs= fbsList.get(i);
			 fbs.getStartTime();
			 fbs.getEndTime();
			 Long imgAdvId= fbs.getImgAdvId();
			 File imgFile= this.fileMapper.selectByPrimaryKey(imgAdvId);
			 Long id= fbs.getId();
			 fbsObj.accumulate("id", id)
			 .accumulate("uriPath", imgFile.getUriPath())
			 .accumulate("type", AOrFType.SESSION.getValue())//专场
			 ;
			 array.add(fbsObj);
		}
		
		
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.DATA, array);
		return object.toString();
	}
	
	/**
	 * 获取活动详情
	 * @param id
	 * @param sign
	 * @return
	 */
	@RequestMapping("getActivityById")
	public String getActivityById(Long id, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			 "id"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG,Msg.NOT_PERMISSION).toString();
		}
		 Activity activity = this.m.selectByPrimaryKey(id);
		 Long imgAdvId= activity.getImgAdvId();
		 Long count= activity.getCount();
		 String info= activity.getInfo();
		 String name=activity.getName();
		 Float price= activity.getPrice();
		 Float sill= activity.getSill();
		 Long activityId=activity.getId();
		 
		 File advFile= this.fileMapper.selectByPrimaryKey(imgAdvId);
		 
		 object.accumulate("count", count)
		 .accumulate("info", info)
		 .accumulate("name", name)
		 .accumulate("price", price)
		 .accumulate("sill", sill)
		 .accumulate("advFileUriPath", advFile.getUriPath())
		 ;
		 
		 
		 Integer type= activity.getActivityType();
		 object.accumulate("type", type);
		 List<ActivityAssociated> aaList= this.activityAssociatedMapper.getAssoByActivityId(activityId);
		 JSONArray array = new JSONArray();
		 for(int i=0; i<aaList.size(); i++){
			  JSONObject  aaObject = new JSONObject();
			  ActivityAssociated activityAssociated= aaList.get(i);
			  Long aId= activityAssociated.getAssociateId();
//			  if(type.intValue() == 1){//品牌
//				  Brand brand= this.brandMapper.selectByPrimaryKey(aId);
//			  }else if(type.intValue() == 2){//分类
//				  Classification classification= this.classificationMapper.selectByPrimaryKey(aId);
//			  }else if(type.intValue() == 3){//国家
//				  Country country= this.countryMapper.selectByPrimaryKey(aId);
//			  }
			  aaObject.accumulate("aId", aId);
			  array.add(aaObject);
		 }
		 
		 JSONArray fileArray = new JSONArray();
		 List<ActivityFile> activityFiles= this.activityFileMapper.getActivityFileById(activityId);
		 for(int i=0;i<activityFiles.size();i++){
			  ActivityFile activityFile= activityFiles.get(i);
			  JSONObject activityObj = new JSONObject();
			  Long fileId= activityFile.getFileId();
			  File detailFile= this.fileMapper.selectByPrimaryKey(fileId);
			  activityObj.accumulate("detailUriPath", detailFile.getUriPath());
			  fileArray.add(activityObj);
		 }
		 
		 object.accumulate(Constants.SUCCESS, true)
		 .accumulate("fileArray", fileArray)
		 .accumulate(Constants.DATA, array)
		 ;
		 
		 return object.toString();
	}
	
	/**
	 * 根据活动id获取关联信息
	 * @param activityId
	 * @return
	 */
	public String getAssocByActivityId(Long activityId,Integer type, String sign){
		JSONObject object = new JSONObject();
		return object.toString();
	}
	
	/**
	 * 根据活动id获取商品
	 * @param id
	 * @param sign
	 * @return
	 */
	public String getGoodsByActivityId(Long id,Integer type,String sign){
		JSONObject object = new JSONObject();
//		List<ActivityAssociated> aaList= this.activityAssociatedMapper.getAssoByActivityId(id);
//		JSONArray array = new JSONArray();
//		 for(int i=0; i<aaList.size(); i++){
//			 JSONObject  aaObject = new JSONObject();
//			  ActivityAssociated activityAssociated= aaList.get(i);
//			  Long aId= activityAssociated.getAssociateId();
//			  if(type.intValue() == 1){//品牌
//				  Brand brand= this.brandMapper.selectByPrimaryKey(aId);
//				  this.brandMapper.
//			  }else if(type.intValue() == 2){//分类
//				  Classification classification= this.classificationMapper.selectByPrimaryKey(aId);
//			  }else if(type.intValue() == 3){//国家
//				  Country country= this.countryMapper.selectByPrimaryKey(aId);
//			  }
//		 }
		return object.toString();
	}
	
	/**
	 * 根据商品id获取活动信息
	 * @param goodId
	 * @return
	 */
	public String getActivityByGoodId(Long goodId){
		 JSONObject object = new JSONObject();
//		 List<Activity> activityList= this.m.findAll();
//		 for(int i=0; i<activityList.size(); i++){
//			 Activity activity = activityList.get(i);
//			 List<ActivityAssociated> aaList= this.activityAssociatedMapper.getAssoByActivityId(activityId);
//			 JSONArray array = new JSONArray();
//			 for(int i=0; i<aaList.size(); i++){
//		 }
		 return object.toString();
	}
}
