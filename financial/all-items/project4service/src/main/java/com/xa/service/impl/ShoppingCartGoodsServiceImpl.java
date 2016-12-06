package com.xa.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.Orders;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.OrdersState;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.ShoppingCartGoodsService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional	
public class ShoppingCartGoodsServiceImpl extends BaseServiceImpl<ShoppingCartGoods, ShoppingCartGoodsMapper>
		implements ShoppingCartGoodsService<ShoppingCartGoods> {

	/**
	 * 
	 */
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	/**
	 * 添加商品到购物车
	 */
	public String addGoodsToCart(ShoppingCartGoods scg, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"goodsId","shoppingCartId","count","id"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		if(scg.getId() == null){
			//添加
			this.m.insert(scg);
		}else {
			//修改
			this.m.updateByPrimaryKey(scg);
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("scgId", scg.getId())
		.accumulate("goodId", scg.getGoodsId())
		.accumulate("count", scg.getCount())
		;
		return object.toString();
	}
	
	/**
	 * 结算
	 * @return
	 */
	public String sattlement(String sign){
		JSONObject object = new JSONObject();
		
		Orders orders = new Orders();
		orders.setState(OrdersState.HAS_BEEN_SHIPPED.getValue()); // 已发货
		this.ordersMapper.insert(orders);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 获取所有购物车中的商品信息
	 * @return
	 */
	public String getAllCartGoods(Long cartId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"cartId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<ShoppingCartGoods> scgs=this.m.getSCGByCartId(cartId);
		
		if(null != scgs && scgs.size() > 0){
			JSONArray array = new JSONArray();
			for(int i=0;i<scgs.size(); i++){
				ShoppingCartGoods scg = scgs.get(i);
				JSONObject scgObj = new JSONObject();
				Long count= scg.getCount();
				Long goodId= scg.getGoodsId();
				Long scgId= scg.getId();
				
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("goodId", goodId);
				map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
				
				List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map);
				File thumbFile=fileList.get(0);
				
				Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
				String goodName= good.getName();
				Long price = good.getPrice();
				scgObj
				
				.accumulate("count", count)
				.accumulate("filePath", thumbFile.getUriPath())
				.accumulate("price", price)
				.accumulate("goodName", goodName)
				.accumulate("scgId", scgId)
				.accumulate("goodId", good.getId())
				;
				array.add(scgObj);
			}
			object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		}else {
			object.accumulate(Constants.SUCCESS, false);
			object.accumulate(Constants.MSG, "购物车中暂无商品!");
		}
			
		return object.toString();
	}
	
	/**
	 * 通过商品id获取购物车中的信息
	 * @return
	 */
	public String getCartInfoByGoodId(Long cartId, Long goodId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"cartId","goodId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("cartId", cartId);
		map.put("goodId", goodId);
		ShoppingCartGoods scg=this.m.getSCGByCartIdAndGoodId(map);
		return null;
	}
	
	
	/**
	 * 购物车中删除商品
	 * @return
	 */
	public String removeSCG(Long scgId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"scgId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		this.m.deleteByPrimaryKey(scgId);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
}
