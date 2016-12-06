package com.xa.service.impl;

import java.awt.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.GoodsOrderRelease;
import com.xa.mapper.GoodsOrderReleaseMapper;
import com.xa.service.GoodsOrderReleaseService;

import net.sf.json.JSONObject;

/**
 * 订单和商品绑定
 * @author admin
 *
 */
@Service
@Transactional
public class GoodsOrderReleaseServiceImpl extends BaseServiceImpl<GoodsOrderRelease, GoodsOrderReleaseMapper>
		implements GoodsOrderReleaseService<GoodsOrderRelease> {

	
	/**
	 * 绑定订单和商品
	 */
	public String bindingOrdersAndGoods(Long orderId, Long []goodsId){
		 JSONObject object = new JSONObject();
		 for(int i=0; i<goodsId.length; i++){
			 GoodsOrderRelease gor = new GoodsOrderRelease();
			 gor.setOrderId(orderId);
			 gor.setGoodsId(goodsId[i]);
			 this.m.insert(gor);
		 }
		 object.accumulate("success", true);
		 return object.toString();
	}
	
	
}
