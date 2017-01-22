package com.xa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.DeliveryAddress;
import com.xa.entity.Goods;
import com.xa.entity.OrderRecv;
import com.xa.entity.Orders;
import com.xa.entity.ScgOrderRelease;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.OrdersState;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.DeliveryAddressMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.OrderRecvMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.mapper.ScgOrderReleaseMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.OrderRecvService;
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
public class OrderRecvServiceImpl extends BaseServiceImpl<OrderRecv, OrderRecvMapper>
		implements OrderRecvService<OrderRecv> {

	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private ScgOrderReleaseMapper scgOrderReleaseMapper;
	
	@Autowired
	private ShoppingCartGoodsMapper shoppingCartGoodsMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private DeliveryAddressMapper deliveryAddressMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	/**
	 * 买手接单
	 * @return
	 */
	public String recvOrder(Long buyHandId, Long orderId, String sign) {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyHandId","orderId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		Orders order = this.ordersMapper.selectByPrimaryKey(orderId);
		order.setState(OrdersState.WAIT_PURCHASING.getValue());
		this.ordersMapper.updateByPrimaryKeySelective(order);
		
		OrderRecv orderRecv = new OrderRecv();
		orderRecv.setBuyhandId(buyHandId);
		orderRecv.setOrderId(orderId);
		this.m.insert(orderRecv);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 采购
	 * @return
	 */
	public String procurement(Long orderId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"orderId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		Orders order= this.ordersMapper.selectByPrimaryKey(orderId);
		order.setState(OrdersState.WAIT_DELIVERY.getValue());
		this.ordersMapper.updateByPrimaryKeySelective(order);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	/**
	 * 根据买手id获取订单信息
	 * @return
	 */
	public String getAlreadyAcceptOrders(Long buyHandId,Integer state, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyHandId","state"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", state);
		map.put("buyHandId", buyHandId);
		List<Orders> orderList=this.m.getOrdersByBuyHandId(map);
		JSONArray array = new JSONArray();
		for(int i=0;i<orderList.size();i++){
			 Orders order= orderList.get(i);
			 JSONObject orderObj = new JSONObject();
			 Long buyerId= order.getBuyerId();
			 String orderNo= order.getOrderNo();
			 Integer orderState= order.getState();
			 Float sumPrice= order.getSumPrice();
			 Long orderId=order.getId();
			 Date gt= order.getGenerateTime();
			 List<ScgOrderRelease> sorList= this.scgOrderReleaseMapper.getSCGByOrderId(orderId);
			 
			 JSONArray goodArray = new JSONArray();
			 for(int j=0;j<sorList.size();j++){
				  JSONObject goodObj = new JSONObject();
				  ScgOrderRelease sor= sorList.get(j);
				  Long scgId= sor.getScgId();
				  ShoppingCartGoods scg= this.shoppingCartGoodsMapper.selectByPrimaryKey(scgId);
				  Long count= scg.getCount();
				  Long goodId= scg.getGoodsId();
				  Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
				  Long capacity= good.getCapacity();
				  String info= good.getInfo();
				  Float hp= good.getHighestPrice();
				  float lp= good.getLowestPrice();
				  
				  Map<String, Object> mapPic = new HashMap<String,Object>();
				  mapPic.put("goodId", good.getId());
				  mapPic.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
					
				  List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapPic);
				  com.xa.entity.File thumbFile=fileList.get(0);
				  
				  DeliveryAddress address= this.deliveryAddressMapper.getDefaultAddressByBuyerId(buyerId);
				  
				  goodObj.accumulate("goodName", good.getName()).accumulate("price", good.getPrice())
				  .accumulate("capacity", capacity)
				  .accumulate("count", count)
				  .accumulate("goodId", goodId)
				  .accumulate("info", info)
				  .accumulate("hp", hp)
				  .accumulate("lp", lp)
				  .accumulate("address", address.getAddress())
				  .accumulate("filePath", thumbFile.getUriPath())
				  ;
				  
				  goodArray.add(goodObj);
			 }
			 
			 orderObj.accumulate("buyerId", buyerId).accumulate("orderNo", orderNo).accumulate("orderState", orderState)
			 .accumulate("gt", gt)
			 .accumulate("orderId", orderId)
			 .accumulate("sumPrice", sumPrice).accumulate(Constants.DATA, goodArray);
			 array.add(orderObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
	
}
