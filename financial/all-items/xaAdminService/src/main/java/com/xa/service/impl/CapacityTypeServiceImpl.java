package com.xa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.CapacityType;
import com.xa.mapper.CapacityTypeMapper;
import com.xa.service.CapacityTypeService;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 添加容量类型
 * @author burgess
 *
 */
@Service
@Transactional
public class CapacityTypeServiceImpl extends BaseServiceImpl<CapacityType, CapacityTypeMapper>
		implements CapacityTypeService<CapacityType> {

	/**
	 * 
	 * @param capacityType
	 */
	public void addCapacityType(CapacityType capacityType){
		this.m.insert(capacityType);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getCapacityTypes(Integer pageNum,Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<CapacityType> ctPage= (Page<CapacityType>)this.m.findAll();
		List<CapacityType> ctList= ctPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<ctList.size();i++){
			JSONObject ctObject = new JSONObject();
			CapacityType cType= ctList.get(i);
			String name= cType.getName();
			String unit= cType.getUnit();
			Long id= cType.getId();
			ctObject.accumulate("name", name)
			.accumulate("unit", unit)
			.accumulate("id", id)
			;
			array.add(ctObject);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, ctPage.getTotal())
		;
		return object.toString();
	}

}
