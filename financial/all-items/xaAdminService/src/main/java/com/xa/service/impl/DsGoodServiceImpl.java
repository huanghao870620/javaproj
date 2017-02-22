package com.xa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.DsGood;
import com.xa.entity.Goods;
import com.xa.mapper.DsGoodMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.service.DsGoodService;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class DsGoodServiceImpl extends BaseServiceImpl<DsGood, DsGoodMapper> implements DsGoodService<DsGood> {

	@Autowired
	private DsGoodMapper dsGoodMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	/**
	 * 获取抢购时间下的商品
	 * @param dsId
	 * @return
	 */
	public String getGoodsByDsSessionId(Long dsId){
		 JSONObject object = new JSONObject();
		 List<DsGood> dgList= dsGoodMapper.getDSGoodByDSID(dsId);
		 JSONArray array = new JSONArray();
		 for(int i=0;i<dgList.size(); i++){
			 JSONObject dgObject = new JSONObject();
			 DsGood dsGood= dgList.get(i);
			 Long id= dsGood.getId();
			 Long goodId= dsGood.getGoodId();
			 Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
			 String name= good.getName();
			 Float price= good.getPrice();
			 Integer inventory= dsGood.getInventory();
			 dgObject.accumulate("goodId", goodId)
			 .accumulate("name", name)
			 .accumulate("id", id)
			 .accumulate("price", price)
			 .accumulate("inventory", inventory)
			 ;
			 array.add(dgObject);
		 }
		 object.accumulate(Constants.ROWS, array);
		 return object.toString();
	}
	
	
	/**
	 * 
	 * @param dsGood
	 */
	public void editDsGood(DsGood dsGood){
		this.m.updateByPrimaryKeySelective(dsGood);
	}
	
	/**
	 * 
	 * @param id
	 * @param modelAndView
	 */
	public void getDsGood(Long id, ModelAndView modelAndView){
		 DsGood dsGood = this.m.selectByPrimaryKey(id);
		 modelAndView.addObject("dsGood", dsGood);
	}
}
