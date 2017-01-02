package com.xa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Area;
import com.xa.mapper.AreaMapper;
import com.xa.service.AreaService;
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
	public String getAllArea(String random,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"random"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		List<Area> childArea = this.m.findAreaByPid(-1L);
		JSONArray array = new JSONArray();
		for (int i = 0; i < childArea.size(); i++) {
			Area child = childArea.get(i);
			 JSONObject childObj = new JSONObject();
			 childObj.accumulate("id", child.getId()).accumulate("name", child.getName());
			 this.appendChild(child, childObj);
			 array.add(childObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate("data", array);
		return object.toString();
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
			 .accumulate("name", classification.getName());
			 this.appendChild(classification, child);
			 array.add(child);
		}
		if(array.size() > 0){
			object.accumulate("childs", array);
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
}
