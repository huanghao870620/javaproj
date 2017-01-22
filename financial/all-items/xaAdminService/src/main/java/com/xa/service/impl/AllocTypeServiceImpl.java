package com.xa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.AllocType;
import com.xa.mapper.AllocTypeMapper;
import com.xa.service.AllocTypeService;
import com.xa.service.impl.BaseServiceImpl;
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
public class AllocTypeServiceImpl extends BaseServiceImpl<AllocType, AllocTypeMapper>
		implements AllocTypeService<AllocType> {

	/**
	 * 添加分配类型
	 * @param allocType
	 */
	public void addAllocType(AllocType allocType){
		 this.m.insert(allocType);
	}
	
	/**
	 * 列出分配类型
	 * @return
	 */
	public String listAllocType(Integer pageNum, Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<AllocType> allocTypePage=(Page<AllocType>) this.m.findAll();
		List<AllocType> atList= allocTypePage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<atList.size();i++){
			JSONObject atObject = new JSONObject();
			 AllocType aType= atList.get(i);
			 String name= aType.getName();
			 Long id= aType.getId();
			 String info= aType.getInfo();
			 atObject.accumulate("id", id)
			 .accumulate("name", name)
			 .accumulate("info"	, info);
			 array.add(atObject);
		}
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.TOTAL, allocTypePage.getTotal())
		.accumulate(Constants.ROWS, array)
		;
		return object.toString();
	}
	
	/**
	 * 
	 * @param modelAndView
	 * @param id
	 */
	public void getAllocType(ModelAndView modelAndView, Long id){
		 AllocType allocType = this.m.selectByPrimaryKey(id);
		 modelAndView.addObject("allocType", allocType);
	}
	

	/**
	 * 编辑分配类型
	 * @param allocType
	 */
	public void updateAllocType(AllocType allocType){
		this.m.updateByPrimaryKeySelective(allocType);
	}
	

	/**
	 * 删除分配类型
	 * @param id
	 * @return
	 */
	public String delAllocType(Long id){
		 JSONObject object = new JSONObject();
		  this.m.deleteByPrimaryKey(id);
		  object.accumulate(Constants.SUCCESS, true);
		  return object.toString();
	}
	
	/**
	 * 获取所有分配类型
	 * @param modelAndView
	 */
	public void getAllAllocType(ModelAndView modelAndView){
		List<AllocType> allocTypes= this.m.findAll();
		 modelAndView.addObject("allocTypes", allocTypes);
	}
}
