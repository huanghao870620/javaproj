package com.xa.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.dto.OrderDto;
import com.xa.entity.Area;
import com.xa.entity.DeliveryAddress;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.Orders;
import com.xa.entity.ScgOrderRelease;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.OrdersState;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BuyersMapper;
import com.xa.mapper.DeliveryAddressMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.OrderGoodMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.mapper.ScgOrderReleaseMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.AreaService;
import com.xa.service.OrdersService;
import com.xa.util.Constants;
import com.xa.util.PayCommonUtil;
import com.xa.util.XMLUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class OrdersServiceImpl extends BaseServiceImpl<Orders, OrdersMapper> implements OrdersService<Orders> {
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private ShoppingCartGoodsMapper shoppingCartGoodsMapper;
	
	@Autowired
	private ScgOrderReleaseMapper scgOrderReleaseMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private DeliveryAddressMapper deliveryAddressMapper;
	
	@Autowired
	private OrderGoodMapper orderGoodMapper;
	
	@Autowired
	private BuyersMapper buyersMapper;

	/**
	 * 获取订单
	 * @return
	 */
	public String getOrders(Integer stateS,
			Integer receWayS, 
			String nameS,
			Date startTime,
			Date endTime,
			Integer pageNum, 
			Integer pageSize,
			AreaService<Area> areaService) {
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(null != startTime){
			String startTimeStr= DateFormatUtils.format(startTime, Constants.COMMON_DATE_FORMAT);
			map.put("startTime", startTimeStr);
		}
		
		if(null != endTime){
			String endTimeStr= DateFormatUtils.format(endTime, Constants.COMMON_DATE_FORMAT);
			map.put("endTime", endTimeStr);
		}
		
		
		if(!StringUtils.isBlank(nameS)){
			map.put("nameS", nameS.trim());
		}
		
		if(stateS !=null){
			map.put("stateS", stateS);
		}
		
		if(receWayS !=null){
			map.put("receWayS", receWayS);
		}
		
		Page<OrderDto> orderPage=(Page<OrderDto>) this.m.searchOrder(map );
		List<OrderDto> orderList= orderPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<orderList.size(); i++){
			  JSONObject orderObj = new JSONObject();
			  OrderDto order= orderList.get(i);
			  String orderNo= order.getOrderNo();
			  Integer receWay= order.getReceWay();
			  String receWayStr="";
			  if(receWay.intValue()==1){
				  receWayStr="自提";
			  }else if (receWay.intValue()==2) {
				  receWayStr="地址";
			  }
			  Integer state= order.getState();
			  
			  String stateStr = "";
			  if(1==state.intValue()){
				  stateStr="待付款";
			  }else if(2==state.intValue()){
				  stateStr="待接单";
			  }else if(3==state.intValue()){
				  stateStr="待采购";
			  }else if(4==state.intValue()){
				  stateStr="待发货";
			  }else if(5==state.intValue()){
				  stateStr="待收货";
			  }else if(6==state.intValue()){
				  stateStr="完成订单";
			  }else if(7==state.intValue()){
				  stateStr="取消订单";
			  }
			  
			  
			
			  Float sumPrice= order.getSumPrice();
			  Long addressId= order.getAddressId();
			  Date generateTime= order.getGenerateTime();
			  
			  String generateTimeStr= "";
			  if(null !=generateTime){
				  generateTimeStr=DateFormatUtils.format(generateTime, Constants.COMMON_DATE_FORMAT);
				  orderObj.accumulate("generateTime", generateTimeStr);
			  }
			  
			  
			  if(null != addressId){
				  DeliveryAddress deliveryAddress= this.deliveryAddressMapper.selectByPrimaryKey(addressId);
				  Long areaId= deliveryAddress.getAreaId();
				  StringBuilder addressSB = new StringBuilder();
				  String address= deliveryAddress.getAddress();
				  String fullAddress= areaService.getFullAddress(areaId);
				  addressSB.append(fullAddress).append(address);
				  orderObj.accumulate("address", addressSB.toString());
			  }
			  
			  String mobile= order.getMobile();
			  orderObj.accumulate("mobile", mobile)
			  .accumulate("orderNo", orderNo)
			  .accumulate("sumPrice", sumPrice)
			  .accumulate("state", stateStr)
			  .accumulate("receWay", receWayStr)
			  .accumulate("id", order.getOrderId())
			  ;
			  array.add(orderObj);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, orderPage.getTotal());
		return object.toString();
	}

	
	/**
	 * 等待发货的订单列表
	 * @return
	 */
	public String getOrdersByWaitDelivery(){
		return null;
	}
	
	/**
	 * 获取订单中的商品
	 * @return
	 */
	public String getGoodsForOrderId(Long orderId){
		JSONObject object = new JSONObject();
		List<ScgOrderRelease> sorList= this.scgOrderReleaseMapper.getSCGByOrderId(orderId);
		JSONArray array = new JSONArray();
		if(sorList.size()>0){
			for(int i=0;i<sorList.size();i++){
				JSONObject goodObj = new JSONObject();
				ScgOrderRelease sOrderRelease= sorList.get(i);
				Long scgId= sOrderRelease.getScgId();
				ShoppingCartGoods scg= this.shoppingCartGoodsMapper.selectByPrimaryKey(scgId);
				
				Long goodId= scg.getGoodsId();
				Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
				String goodName=good.getName();
				Long count= scg.getCount();
				
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("goodId", good.getId());
				map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
				
				List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map);
				File file= null;
				if(null != fileList && fileList.size() > 0){
					file=fileList.get(0);//缩略图
					goodObj.accumulate("thumbFilePath", file.getUriPath());
				}
				
				goodObj.accumulate("goodId", goodId)
				.accumulate("goodName", goodName)
				.accumulate("price", good.getPrice())
				.accumulate("count", count)
				;
				array.add(goodObj);
			}
		}else {
			JSONObject goodObj = new JSONObject();
			Goods good= this.orderGoodMapper.getDetailByOrderId(orderId);
			Long goodId = good.getId();
			String goodName= good.getName();
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("goodId", good.getId());
			map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
			
			List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map);
			File file= null;
			if(null != fileList && fileList.size() > 0){
				file=fileList.get(0);//缩略图
				goodObj.accumulate("thumbFilePath", file.getUriPath());
			}
			goodObj.accumulate("goodId", goodId)
			.accumulate("goodName", goodName)
			.accumulate("price", good.getPrice())
			.accumulate("count", 1)
			;
			array.add(goodObj);
		}
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, array.size())
		;
		return object.toString();
	}
	

	/**
	 * 获取订单详情
	 * @param orderId
	 * @param modelAndView
	 */
	public void getOrderById(Long orderId,
			ModelAndView modelAndView,
			AreaService<Area> areaService){
	    OrderDto orderDto= this.m.getOrderDtoByOrderId(orderId);
	    
	    Long addressId= orderDto.getAddressId();
	    if(null != addressId){
	    	DeliveryAddress address= this.deliveryAddressMapper.selectByPrimaryKey(addressId);
	    	modelAndView.addObject("address", address);
	    	Long areaId= address.getAreaId();
	    	String area= areaService.getFullAddress(areaId);
	    	orderDto.setArea(area);
	    	
	    	Long cardIdBackFileId= address.getCardIdBackFile();
	    	Long cardIdFrontFileId= address.getCardIdFrontFile();
	    	
	    	File cardIdBackFile= this.fileMapper.selectByPrimaryKey(cardIdBackFileId);
	    	File cardIdFrontFile= this.fileMapper.selectByPrimaryKey(cardIdFrontFileId);
	    	
	    	modelAndView.addObject("cardIdBackFile",cardIdBackFile);
	    	modelAndView.addObject("cardIdFrontFile",cardIdFrontFile);
	    }
	    
	    modelAndView.addObject("orderDto", orderDto);
	}
}
