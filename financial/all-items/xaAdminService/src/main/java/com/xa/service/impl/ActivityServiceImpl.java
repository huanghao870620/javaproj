package com.xa.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.dto.AssoActivityDto;
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
	
	/**
	 * 编辑活动
	 * @param modelAndView
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public  void editActivity(Activity activity, ModelAndView modelAndView,MultipartFile imgAdvFile,Long []ids,FileService<File> fileService) 
			throws IllegalStateException, IOException {
		Long imgAdvId= activity.getImgAdvId();
		if(null != imgAdvFile && imgAdvFile.getSize() > 0){
			if(null != imgAdvId){
				// 修改
				File file = new File();
				file.setId(imgAdvId);
				fileService.editFile(imgAdvFile, PhotoType.ACTIVITY_BY_FIGURE, file);
			}else {
				//添加
				File file=new File();
				fileService.uploadFile(imgAdvFile, PhotoType.ACTIVITY_BY_FIGURE, file);
				activity.setImgAdvId(file.getId());
			}
		}
		
		if(ids.length >0){
			this.activityAssociatedMapper.deleteASByActivityId(activity.getId());
			 for(int i=0;i<ids.length;i++){
				   ActivityAssociated aa = new ActivityAssociated();
				   aa.setActivityId(activity.getId());
				   aa.setAssociateId(ids[i]);
				   this.activityAssociatedMapper.insert(aa);
			   }
		}
		
		 this.m.updateByPrimaryKeySelective(activity);
	}
	
	/**
	 * 
	 * @param activityId
	 * @param modelAndView
	 */
	public void getActivity(Long activityId, ModelAndView modelAndView){
		Activity activity = this.m.selectByPrimaryKey(activityId);
		modelAndView.addObject("activity",activity);
		
		Integer activityType= activity.getActivityType();
		List<AssoActivityDto> adtoList=null;
		if(activityType.intValue()==1){ // 品牌
			adtoList=this.activityAssociatedMapper.getCheckBrandByActivityId(activityId);
		}else if (activityType.intValue()==2) { //分类
			
		}else if (activityType.intValue()==3) { //国家
			adtoList=this.activityAssociatedMapper.getCheckCountryByActivityId(activityId);
		}
		modelAndView.addObject("adtoList",adtoList);
		
		Long imgAdvId= activity.getImgAdvId();
		File file= this.fileMapper.selectByPrimaryKey(imgAdvId);
		modelAndView.addObject("file",file);
	}
	
	/**
	 * 删除活动
	 * @param activityId
	 */
	public String deleteActivityById(Long activityId){
		JSONObject object = new JSONObject();
		 this.m.deleteByPrimaryKey(activityId);
		 this.activityAssociatedMapper.deleteASByActivityId(activityId);
		 return object.accumulate(Constants.SUCCESS, true).toString();
	}
	
	/**
	 * 获取活动详情图
	 * @param activityId
	 * @return
	 */
	public String getDetailPicByActivityId(Long activityId,Integer pageNum,Integer pageSize){
		 JSONObject object = new JSONObject();
		 PageHelper.startPage(pageNum, pageSize, true);
		 Page<ActivityFile> afPage= (Page<ActivityFile>)this.activityFileMapper.getActivityFileById(activityId);
		 List<ActivityFile> afList= afPage.getResult();
		 JSONArray array = new JSONArray();
		 for(int i=0; i<afList.size();i++){
			 JSONObject afObject = new JSONObject();
			 ActivityFile af= afList.get(i);
			 Long fileId= af.getFileId();
			 File file= this.fileMapper.selectByPrimaryKey(fileId);
			 String uriPath= file.getUriPath();
			 afObject.accumulate("id", af.getId())
			 .accumulate("uriPath", uriPath)
			 ;
			 array.add(afObject);
		 }
		 object.accumulate(Constants.ROWS, array)
		 .accumulate(Constants.TOTAL, afPage.getTotal())
		 ;
		 return object.toString();
	}
	
	/**
	 * 添加活动详情图
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addActivityDetailPic(Long activityId, MultipartFile detailPicFile,FileService<File> fileService) 
			throws IllegalStateException, IOException{
		if(null != detailPicFile && detailPicFile.getSize() > 0){
			 File file = new File();
			 fileService.uploadFile(detailPicFile, PhotoType.ACTIVITY_DETAIL_FIGURE, file );
			 
			 ActivityFile af = new ActivityFile();
			 af.setActivityId(activityId);
			 af.setFileId(file.getId());
			 this.activityFileMapper.insert(af);
		}
	}
	
	/**
	 * 删除活动详情图
	 * @param picId
	 * @param fileService
	 * @return
	 */
	public String deleteActivityDetailPic(Long afId,FileService<File> fileService){
		JSONObject object = new JSONObject();
		 ActivityFile activityFile= this.activityFileMapper.selectByPrimaryKey(afId);
		 fileService.removeFile(activityFile.getFileId());
		 this.activityFileMapper.deleteByPrimaryKey(afId);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
	  
}
