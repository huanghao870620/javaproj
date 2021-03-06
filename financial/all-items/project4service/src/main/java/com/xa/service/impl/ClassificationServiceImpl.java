package com.xa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.mapper.ClassificationMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.ClassificationService;
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
public class ClassificationServiceImpl extends BaseServiceImpl<Classification, ClassificationMapper> 
implements ClassificationService<Classification> {

	
	@Autowired
	private FileMapper fileMapper;
	
	
	/**
	 * 获取所有分类
	 * @return
	 */
	public String getAllClassification(String random,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		List<Classification> childClass = this.m.findClassByPid(-1L);
		JSONArray array = new JSONArray();
		for (int i = 0; i < childClass.size(); i++) {
			 Classification child = childClass.get(i);
			 Long imgId = child.getImgId();
			 File img = this.fileMapper.selectByPrimaryKey(imgId);
			 JSONObject childObj = new JSONObject();
			 childObj.accumulate("id", child.getId()).accumulate("name", child.getName())
			 .accumulate("img", img.getUriPath());
			 this.appendChild(child, childObj);
			 array.add(childObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
	
	/**
	 * 
	 * @param pid
	 */
	private void appendChild(Classification pClass, JSONObject object){
		List<Classification> childs = this.m.findClassByPid(pClass.getId());
		JSONArray array = new JSONArray();
		for (int i = 0; i < childs.size(); i++) {
			 Classification classification = childs.get(i);
			 JSONObject child = new JSONObject();
			 Long imgId = classification.getImgId();
			 File img = this.fileMapper.selectByPrimaryKey(imgId);
			 child.accumulate("id", classification.getId())
			 .accumulate("name", classification.getName());
			 if(img != null){
				 child.accumulate("img", img.getUriPath());
			 }
			 this.appendChild(classification, child);
			 array.add(child);
		}
		if(array.size() > 0){
			object.accumulate("childs", array);
		}
	}
	
	/**
	 * 根据父分类id获取子分类
	 * @return
	 */
	public String getChildByClassId(String sign,Long pid){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"pid"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<Classification> childs = this.m.findClassByPid(pid);
		JSONArray array = new JSONArray();
		for(int i=0;i<childs.size();i++){
			JSONObject childObj = new JSONObject();
			Classification child= childs.get(i);
			Long imgId= child.getImgId();
			String name= child.getName();
			File img= this.fileMapper.selectByPrimaryKey(imgId);
			String uriPath = null;
			if(img != null){
				uriPath = img.getUriPath();
			}
			childObj.accumulate("name", name).accumulate("imgPath",uriPath==null ? "" : uriPath )
			.accumulate("id", child.getId());
			array.add(childObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
	
	
	
	/**
	 * 根据父分类id获取子分类
	 * @return
	 */
	public String getChildByNoteClassId(String sign,Long pid){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"pid"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<Classification> childs = this.m.findClassByPid(pid);
		JSONArray array = new JSONArray();
		array.add(new JSONObject().accumulate("name", "推荐"));
		array.add(new JSONObject().accumulate("name", "关注"));
		for(int i=0;i<childs.size();i++){
			JSONObject childObj = new JSONObject();
			Classification child= childs.get(i);
			Long imgId= child.getImgId();
			String name= child.getName();
			File img= this.fileMapper.selectByPrimaryKey(imgId);
			String uriPath = null;
			if(img != null){
				uriPath = img.getUriPath();
			}
			childObj.accumulate("name", name).accumulate("imgPath",uriPath==null ? "" : uriPath )
			.accumulate("id", child.getId());
			array.add(childObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
	
	
	
	
	
	public String getChildIdByPid(Long pid){
		StringBuilder sb = new StringBuilder(pid.toString());
		List<Classification> list=new ArrayList<Classification>();
		this.getChild(pid, list);
		if(list.size()>0){
			sb.append(",");
		}
		
		for(int i=0;i<list.size();i++){
			Classification cls=list.get(i);
			sb.append(cls.getId());
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}
	
	public void getChild(Long pid,List<Classification> clsList){
		List<Classification> classifiList= this.m.findClassByPid(pid);
		if(classifiList.size()>0){
			for(int i=0;i<classifiList.size();i++){
				Classification classification= classifiList.get(i);
				clsList.add(classification);
				this.getChild(classification.getId(), clsList);
			}
		}else{
			
		}
	}
	
}
