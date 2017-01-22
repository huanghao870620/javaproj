package com.xa.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Goods;
import com.xa.entity.ShoppingCart;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.mapper.ShoppingCartMapper;
import com.xa.service.ShoppingCartService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class ShoppingCartServiceImpl extends BaseServiceImpl<ShoppingCart, ShoppingCartMapper>
		implements ShoppingCartService<ShoppingCart> {

	
	@Autowired
	private ShoppingCartGoodsMapper shoppingCartGoodsMapper;
	
	/**
	 * 获取购物车通过买家id
	 * @return
	 */
	public String getCartByBuyerId(Long buyerId, String sign) {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		ShoppingCart cart = this.m.getCartByBuyerId(buyerId);
		List<Goods> goods = this.shoppingCartGoodsMapper.getGoodsByCartId(cart.getId());
		object.accumulate(Constants.SUCCESS, true).accumulate("cartId", cart.getId());
		return object.toString();
	}
}
