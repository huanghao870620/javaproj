package com.xa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Area;
import com.xa.mapper.AreaMapper;
import com.xa.service.AreaService;
import com.xa.service.impl.BaseServiceImpl;
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
public class AreaServiceImpl extends BaseServiceImpl<Area, AreaMapper> implements AreaService<Area> {

	
	/**
	 * 获取所有区域
	 * @return
	 */
	public String getAllArea(){
		List<Area> childArea = this.m.findAreaByPid(-1L);
		JSONArray array = new JSONArray();
		for (int i = 0; i < childArea.size(); i++) {
			Area child = childArea.get(i);
			 JSONObject childObj = new JSONObject();
			 childObj.accumulate("id", child.getId()).accumulate("text", child.getName());
			 this.appendChild(child, childObj);
			 array.add(childObj);
		}
		return array.toString();
	}
	
	/**
	 * 
	 * @param pid
	 */
	private void appendChild(Area pArea, JSONObject object){
		List<Area> childs = this.m.findAreaByPid(pArea.getId());
		JSONArray array = new JSONArray();
		for (int i = 0; i < childs.size(); i++) {
			Area classification = childs.get(i);
			 JSONObject child = new JSONObject();
			 child.accumulate("id", classification.getId())
			 .accumulate("text", classification.getName());
			 this.appendChild(classification, child);
			 array.add(child);
		}
		if(array.size() > 0){
			object.accumulate("children", array);
		}
	}

	/**
	 * 获取完整区域
	 * @return
	 */
	public String getFullArea(Long areaId){
		JSONArray array = new JSONArray();
		List<Area> list = new ArrayList<Area>();
		Area area = this.m.selectByPrimaryKey(areaId);
		list.add(area);
		
		while (area.getPid()!=-1) {
			 area = this.m.selectByPrimaryKey(area.getPid());
			 list.add(area);
		}
		
		for(int i=list.size()-1;i>=0;i--){
			Area  area2= list.get(i);
			 JSONObject object = new JSONObject();
			 object.accumulate("name", area2.getName()).accumulate("id", area2.getId());
			 array.add(object);
		}
		
		return array.toString();
	}
	
	/**
	 * 获取完整地址通过区域id
	 * @param areaId
	 * @return
	 */
	public String getFullAddress(Long areaId){
		StringBuilder sb = new StringBuilder();
		List<Area> list = new ArrayList<Area>();
		Area area = this.m.selectByPrimaryKey(areaId);
		list.add(area);
		
		while (area.getPid()!=1) {
			 area = this.m.selectByPrimaryKey(area.getPid());
			 list.add(area);
		}
		
		for(int i=list.size()-1;i>=0;i--){
			Area  area2= list.get(i);
			sb.append(area2.getName());
		}
		
		return sb.toString();
	}
	
	/**
	 * 添加区域
	 * @param area
	 * @return
	 */
	public String addArea(Area area){
		 JSONArray array = new JSONArray();
		 JSONObject object = new JSONObject();
		 this.m.insert(area);
		 object.accumulate("text", area.getName())
		 .accumulate("id", area.getId())
		 ;
		 
		 array.add(object);
		 return array.toString();
	}
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public String delAreaById(Long id){
		JSONObject object = new JSONObject();
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		this.appendChildId(id, idList);
		for (int i = 0; i < idList.size(); i++) {
			  this.m.deleteByPrimaryKey(idList.get(i));
		}
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	private void appendChildId(Long pid,List<Long> idList){
		 List<Area> areas=  this.m.findAreaByPid(pid);
		 for(int i=0;i<areas.size();i++){
			  Area area= areas.get(i);
			  Long id= area.getId();
			  idList.add(id);
			  this.appendChildId(id, idList);
		 }
	}
	
}
