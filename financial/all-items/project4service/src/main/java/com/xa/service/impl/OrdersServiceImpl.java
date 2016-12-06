package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Orders;
import com.xa.mapper.OrdersMapper;
import com.xa.service.OrdersService;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class OrdersServiceImpl extends BaseServiceImpl<Orders, OrdersMapper> implements OrdersService<Orders> {

	/**
	 * 假数据
	 * 获取订单
	 * @return
	 */
	public String getOrders() {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject object0 = new JSONObject();
		object0.accumulate("orderState", "发货")
		.accumulate("iconImageViewStr", "婴儿奶粉")
		.accumulate("countryStr", "德国")
		.accumulate("titleStr", "Aptamil 婴儿奶粉")
		.accumulate("subTitleStr", "婴儿奶粉2段 800克 新老包装随机发货")
		.accumulate("leftPriceStr", 199.00)
		.accumulate("rightPriceStr", 205.00)
		.accumulate("goodsUrl", "http://www.xunanbuy.com/Goods/content/html/g-935.htm");
		array.add(object0);
		
		JSONObject object1 = new JSONObject();
		object1.accumulate("orderState", "接单")
		.accumulate("iconImageViewStr", "磨牙棒")
		.accumulate("countryStr", "美国")
		.accumulate("titleStr", "Gerber 磨牙谷物棒")
		.accumulate("subTitleStr", "Gerber 嘉宝 香草曲奇磨牙谷物棒 126克")
		.accumulate("leftPriceStr", 34.00)
		.accumulate("rightPriceStr", 36.00)
		.accumulate("goodsUrl", "http://www.xunanbuy.com/Goods/content/html/goods-996.html");
		array.add(object1);
		
		JSONObject object2 = new JSONObject();
		object2.accumulate("orderState", "接单")
		.accumulate("iconImageViewStr", "防晒霜")
		.accumulate("countryStr", "韩国")
		.accumulate("titleStr", "LΛNEIGE 雪纱丝柔防晒隔离霜")
		.accumulate("subTitleStr", "LΛNEIGE 兰芝 雪纱丝柔防晒隔离霜 绿色")
		.accumulate("leftPriceStr", 169.00)
		.accumulate("rightPriceStr", 176.00)
		.accumulate("goodsUrl", "http://www.xunanbuy.com/Goods/content/html/g-930.html");
		array.add(object2);
		object.accumulate(Constants.SUCCESS, true).accumulate("data", array);
		return object.toString();
	}

	/**
	 * 买手接单
	 * @return
	 */
	public String orderReceiving(Long orderId, String sign) {
		JSONObject object = new JSONObject();
		
		return null;
	}
}
