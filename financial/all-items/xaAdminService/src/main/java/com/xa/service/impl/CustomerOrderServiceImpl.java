package com.xa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.CustomerOrder;
import com.xa.mapper.CustomerOrderMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.service.CustomerOrderService;
import com.xa.service.impl.BaseServiceImpl;

@Service
@Transactional
public class CustomerOrderServiceImpl extends BaseServiceImpl<CustomerOrder, CustomerOrderMapper>
		implements CustomerOrderService<CustomerOrder> {
	
	@Autowired
	private OrdersMapper ordersMapper;
	
//	@Autowired
//	private GoodsOrderReleaseMapper goodsOrderReleaseMapper;
	
	@Autowired
	private FileMapper fileMapper;

	/**
	 * 綁定客戶和訂單
	 */
	public void bindingCustomerAndOrder(Long buyHandId, Long[] orders){
		  for(int i=0; i<orders.length;i++){
			  CustomerOrder co = new CustomerOrder();
			  co.setBuyHandId(buyHandId);
			  co.setOrderId(orders[i]);
			  this.m.insert(co);
		  }
	}
	
	/**
	 * 獲取訂單通過客戶id
	 * @return
	 */
//	public String getOrdersByBuyHandId(Long buyHandId, String sign){
//		JSONObject object = new JSONObject();
//		
//		if(!sign.equals(Security.getSign(new String[]{
//				"buyHandId"
//		}))){
//			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
//			return object.toString();
//		}
//		
//		List<CustomerOrder> cos = this.m.getOrdersByBuyHandId(buyHandId);
//		JSONArray ordersJson = new JSONArray(); // 订单数组
//		for(int i=0; i<cos.size(); i++){
//			CustomerOrder co = cos.get(i);
//			Long orderId = co.getOrderId();
//		    Orders orders = this.ordersMapper.selectByPrimaryKey(orderId); // 订单
//		    JSONObject orderObj = new JSONObject(); // 订单json
//		    Integer state = orders.getState();
//		    List<Goods> goodsList = this.goodsOrderReleaseMapper.getGoodsByOrderId(orders.getId());
//		    JSONArray goodsJson = new JSONArray(); // 商品数组(所有商品)
//		     for(int j=0; j<goodsList.size(); j++){
//		    	  JSONObject goodObj = new JSONObject(); // 商品 json
//		    	  Goods goods = goodsList.get(j);
//		    	  String barCode = goods.getBarCode();
//		    	  Long capacity = goods.getCapacity();
//		    	  Date dateOfProduction = goods.getDateOfProduction();
//		    	  Float highestPrice = goods.getHighestPrice();
//		    	  String info = goods.getInfo();
//		    	  Float lowestPrice = goods.getLowestPrice();
//		    	  String name = goods.getName();
////		    	  Long shelfLife = goods.getShelfLife();
//		    	  String purchasingPosition = goods.getPurchasingPosition();
//		    	  
////		    	  Long backGoodsAccordingToFileId = goods.getBackGoodsAccordingTo();
//		    	  Long expressSingleFileId = goods.getExpressSingle();
//		    	  Long goodsAccordingToPositiveFileId = goods.getGoodsAccordingToPositive();
//		    	  Long goodsInvoiceFileId = goods.getGoodsInvoice();
////		    	  Long productProfileFileId = goods.getProductProfile();
//		    	  
////		    	  File backGoodsAccordingToFile = this.fileMapper.selectByPrimaryKey(backGoodsAccordingToFileId);
//		    	  File expressSingleFile = this.fileMapper.selectByPrimaryKey(expressSingleFileId);
//		    	  File goodsAccordingToPositiveFile = this.fileMapper.selectByPrimaryKey(goodsAccordingToPositiveFileId);
//		    	  File goodsInvoiceFile = this.fileMapper.selectByPrimaryKey(goodsInvoiceFileId);
////		    	  File productProfileFile = this.fileMapper.selectByPrimaryKey(productProfileFileId);
//		    	  
//		    	  goodObj.accumulate("barCode", barCode)
//		    	  .accumulate("capacity", capacity)
//		    	  .accumulate("dateOfProduction", dateOfProduction)
//		    	  .accumulate("highestPrice", highestPrice)
//		    	  .accumulate("info", info)
//		    	  .accumulate("lowestPrice", lowestPrice)
//		    	  .accumulate("name", name)
////		    	  .accumulate("shelfLife", shelfLife)
//		    	  .accumulate("purchasingPosition", purchasingPosition)
////		    	  .accumulate("backGoodsAccordingTo", backGoodsAccordingToFile.getUriPath())
//		    	  .accumulate("expressSingle", expressSingleFile.getUriPath())
//		    	  .accumulate("goodsAccordingToPositive", goodsAccordingToPositiveFile.getUriPath())
//		    	  .accumulate("goodsInvoice", goodsInvoiceFile.getUriPath())
////		    	  .accumulate("productProfile", productProfileFile.getUriPath())
//		    	  ;
//		    	  goodsJson.add(goodObj);
//		     }
//		    orderObj.accumulate("state", state)
//		    .accumulate("goodsArray", goodsJson)
//		    ;
//		    ordersJson.add(orderObj);
//		}
//		return object.accumulate("success", true)
//				.accumulate("ordersJson", ordersJson)
//				.toString();
//	}
}
