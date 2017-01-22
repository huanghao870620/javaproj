package com.xa.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Activity;
import com.xa.entity.ActivityAssociated;
import com.xa.entity.ActivityFile;
import com.xa.entity.File;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.ActivityAssociatedMapper;
import com.xa.mapper.ActivityFileMapper;
import com.xa.mapper.ActivityMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.ActivityService;
import com.xa.service.FileService;
import com.xa.util.Constants;

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
	private ActivityFileMapper activityFileMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private ActivityAssociatedMapper activityAssociatedMapper;

	/**
	 * 添加活动
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addActivity(Activity activity, MultipartFile imgAdvFile, MultipartFile[] activityDetailFile,Long []ids,FileService<File> fileService)
			throws IllegalStateException, IOException{
		   File file = new File();
		   fileService.uploadFile(imgAdvFile, PhotoType.ACTIVITY_BY_FIGURE, file);
		   activity.setImgAdvId(file.getId());
		   activity.setAddTime(new Date());
		   activity.setUpTime(new Date());
		   this.m.insert(activity);
		   for(int i=0; i<activityDetailFile.length; i++){
			 File detailFile = new File();
			 fileService.uploadFile(activityDetailFile[i], PhotoType.ACTIVITY_DETAIL_FIGURE, detailFile);
			 ActivityFile activityFile = new ActivityFile();
			 activityFile.setActivityId(activity.getId());
			 activityFile.setFileId(detailFile.getId());
			 activityFileMapper.insert(activityFile);
		   }
		   
		   
		   for(int i=0;i<ids.length;i++){
			   ActivityAssociated aa = new ActivityAssociated();
			   aa.setActivityId(activity.getId());
			   aa.setAssociateId(ids[i]);
			   this.activityAssociatedMapper.insert(aa);
		   }
		   
	}
	
	/**
	 * 获取活动列表
	 * @return
	 */
	public String listActivity(Integer pageNum,Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<Activity> activityPage=(Page<Activity>) this.m.findAll();
		List<Activity> activities= activityPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<activities.size();i++){
			  Activity activity = activities.get(i);
			  JSONObject activityObj = new JSONObject();
			  String name= activity.getName();
			  String info= activity.getInfo();
			  Long id= activity.getId();
			  Long imgAdvId= activity.getImgAdvId();
			  File imgAdvFile= this.fileMapper.selectByPrimaryKey(imgAdvId);
			  Float price=activity.getPrice();
			  Long count= activity.getCount();
			  Float sill= activity.getSill();
			  Integer activityType= activity.getActivityType();
			  
			  String typeStr ="";
			  if(activityType==1){
				  typeStr="品牌";
			  }else if(activityType==2){
				  typeStr="分类";
			  }else if(activityType==3){
				  typeStr="国家";
			  }
			  
			  activityObj.accumulate("id", id)
			  .accumulate("name", name)
			  .accumulate("info", info)
			  .accumulate("price", price)
			  .accumulate("sill", sill)
			  .accumulate("count", count)
			  .accumulate("type", typeStr)
//			  .accumulate("imgAdvUripath", imgAdvFile.getUriPath())
			  ;
			  array.add(activityObj);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, activityPage.getTotal());
		return object.toString();
	}
	
}
