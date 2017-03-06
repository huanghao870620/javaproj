package com.xa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Color;
import com.xa.mapper.ColorMapper;
import com.xa.service.ColorService;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class ColorServiceImpl extends BaseServiceImpl<Color, ColorMapper> implements ColorService<Color> {

	public void addColor(Color color){
		 this.m.insert(color);
	}
	
	/**
	 * 
	 * @return
	 */
	public String listColor(Integer pageNum, Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<Color> colorPage= (Page<Color>)this.m.findAll();
		List<Color> colorList= colorPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<colorList.size(); i++){
			 JSONObject colorObj = new JSONObject();
			 Color color= colorList.get(i);
			 Long id= color.getId();
			 String name= color.getName();
			 colorObj.accumulate("id", id)
			 .accumulate("name", name)
			 ;
			 array.add(colorObj);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, colorPage.getTotal())
		;
		return object.toString();
	}
	
}
