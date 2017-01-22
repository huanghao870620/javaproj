package com.xa.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Coupons;
import com.xa.entity.CouponsBuyer;
import com.xa.entity.DeliveryAddress;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.OrderGood;
import com.xa.entity.Orders;
import com.xa.entity.ScgOrderRelease;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.CouponsState;
import com.xa.enumeration.OrdersState;
import com.xa.enumeration.PhotoType;
import com.xa.enumeration.ScgState;
import com.xa.mapper.CouponsBuyerMapper;
import com.xa.mapper.CouponsMapper;
import com.xa.mapper.DeliveryAddressMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.OrderGoodMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.mapper.ScgOrderReleaseMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.OrdersService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.PayCommonUtil;
import com.xa.util.Security;
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
	private CouponsMapper couponsMapper;
	
	@Autowired
	private CouponsBuyerMapper couponsBuyerMapper;

	/**
	 * 获取订单
	 * @return
	 */
	public String getOrders() {
		return null;
	}

	/**
	 * 买手接单
	 * @return
	 */
	public String orderReceiving(Long orderId, String sign) {
		JSONObject object = new JSONObject();
		
		return null;
	}
	
	/**
	 * 生成订单
	 * @return
	 */
	public String addOrder(String scgids,Long buyerId,  String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		   "scgids","buyerId"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}

		Map<String, Object> couMap = new HashMap<String,Object>();
		couMap.put("buyerId", buyerId);
		couMap.put("state", CouponsState.NOT_USE.getValue());
		
		PageHelper.startPage(1, 10000,true);
		Page<CouponsBuyer> cbPage=(Page<CouponsBuyer>) this.couponsBuyerMapper.findCouponsByState(couMap);
		this.logger.debug("buyerId : " + buyerId + " , 优惠券总数 : " + cbPage.getTotal());
		
		String scgidArr[]= scgids.split(",");
		List<Long> scgIds = new ArrayList<Long>();
		float sum=0;
		for(int i=0; i<scgidArr.length; i++){
			Long scgId= Long.valueOf(scgidArr[i]);
			scgIds.add(scgId);
			ShoppingCartGoods scg= this.shoppingCartGoodsMapper.selectByPrimaryKey(scgId);
			scg.setState(ScgState.IN_ORDER.getValue()); //已经在订单中
			this.shoppingCartGoodsMapper.updateByPrimaryKeySelective(scg);
			Long goodId= scg.getGoodsId();
			Long count= scg.getCount();
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("buyerId", buyerId);
			map.put("goodId", goodId);
			Long countSum4GoodId= this.m.getOrderCountByGoodId(map );
			Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
			String goodName = good.getName();
			if(countSum4GoodId >= good.getLimitCount()){
				return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, goodName+"已经超过了限购次数!").toString();
			}
			
			
			float price = good.getPrice();
			sum+=price*count;
		}
		
		Orders order = new Orders();
		order.setState(OrdersState.FOR_PAYMENT.getValue());
		order.setSumPrice(sum);
		order.setGenerateTime(new Date());
		order.setBuyerId(buyerId);
		
		this.m.insert(order);
		
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr= sdf.format(new Date());
		Long id=  order.getId();
		sb.append(timeStr).append(this.getOrderId(id));
		order.setOrderNo(sb.toString());
		this.m.updateByPrimaryKeySelective(order);
		
		for(int i=0;i<scgIds.size();i++){
			ScgOrderRelease sor = new ScgOrderRelease();
			sor.setOrderId(order.getId());
			sor.setScgId(scgIds.get(i));
			this.scgOrderReleaseMapper.insert(sor);
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("id", order.getId())
		.accumulate("orderNo", order.getOrderNo())
		.accumulate("couponsCount", cbPage.getTotal())
		;
		return object.toString();
	}
	
	/**
	 * 修改订单价格
	 *  使用优惠券
	 * @param cbId
	 * @param orderId
	 * @param sign
	 * @return
	 */
	public String updateOrderPrice(Long cbId, Long orderId, String sign){
		 JSONObject object = new JSONObject();
		 if(!sign.equals(Security.getSign(new String[]{
				 "cbId","orderId"
		 }))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		 }
		 Orders order= this.m.selectByPrimaryKey(orderId);
		 CouponsBuyer cBuyer= this.couponsBuyerMapper.selectByPrimaryKey(cbId);
		 if(cBuyer.getState() == CouponsState.USED.getValue()){
			  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "此优惠券已经使用过!").toString();
		 }
		 
		 Coupons coupon= this.couponsMapper.selectByPrimaryKey(cBuyer.getCouponsId());
		 float price= coupon.getPrice();
		 if(order.getSumPrice()<coupon.getSill()){
			  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "此订单未达到使用门槛，无法使用!").toString();
		 }
		 
		 order.setSumPrice(order.getSumPrice()-price);
		 cBuyer.setState(CouponsState.USED.getValue());
		 this.couponsBuyerMapper.updateByPrimaryKeySelective(cBuyer);
		 this.m.updateByPrimaryKeySelective(order);
		 object.accumulate(Constants.SUCCESS, true)
		 .accumulate("sumPrice", order.getSumPrice())
		 ;
		 return object.toString();
	}
	
	
	
	/**
	 * 立即购买 生成订单
	 * @param goodId
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String addOrder(Long goodId,Long buyerId,  String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"goodId","buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		Map<String, Object> couMap = new HashMap<String,Object>();
		couMap.put("buyerId", buyerId);
		couMap.put("state", CouponsState.NOT_USE.getValue());
		
		PageHelper.startPage(1, 10000,true);
		Page<CouponsBuyer> cbPage=(Page<CouponsBuyer>) this.couponsBuyerMapper.findCouponsByState(couMap);
		this.logger.debug("buyerId : " + buyerId + " , 优惠券总数 : " + cbPage.getTotal());
		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("buyerId", buyerId);
		map.put("goodId", goodId);
		Long count= this.m.getOrderCountByGoodId(map );
		Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
		if(count >= good.getLimitCount()){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "您已经超过了限购次数!").toString();
		}
		
		Orders order = new Orders();
		order.setState(OrdersState.FOR_PAYMENT.getValue());
		order.setSumPrice(good.getPrice());
		order.setGenerateTime(new Date());
		order.setBuyerId(buyerId);
		
		this.m.insert(order);
		
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr= sdf.format(new Date());
		Long id=  order.getId();
		sb.append(timeStr).append(this.getOrderId(id));
		order.setOrderNo(sb.toString());
		this.m.updateByPrimaryKeySelective(order);
		
		OrderGood orderGood = new OrderGood();
		orderGood.setGoodId(goodId);
		orderGood.setOrderId(id);
		this.orderGoodMapper.insert(orderGood );
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("id", order.getId())
		.accumulate("orderNo", order.getOrderNo())
		.accumulate("couponsCount", cbPage.getTotal())
		;
		return object.toString();
	}
	
	
	/**
	 * 获取order id
	 * @param id
	 * @return
	 */
	private String getOrderId(Long id){
		StringBuilder sb = new StringBuilder();
		 String idstr= id.toString();
		 for(int i=0; i<12-idstr.length();i++){
			  sb.append("0");
		 }
		 sb.append(idstr);
		 return sb.toString();
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
	public String getSCGByOrderId(Long orderId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"orderId"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		List<ScgOrderRelease> list= this.scgOrderReleaseMapper.getSCGByOrderId(orderId);
		if(list.size() > 0){
			JSONArray array = new JSONArray();
			for(int i=0;i<list.size();i++){
				ScgOrderRelease sor= list.get(i);
				JSONObject scgObj = new JSONObject();
				Long scgId= sor.getScgId();
				ShoppingCartGoods scg= this.shoppingCartGoodsMapper.selectByPrimaryKey(scgId);
				Long goodId= scg.getGoodsId();
				Long count=scg.getCount();
				Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
				float price = good.getPrice();
				String goodName=good.getName();
				Long advPicId= good.getAdvPic();
				File file= this.fileMapper.selectByPrimaryKey(advPicId);
				Long capacity= good.getCapacity();
				
				
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("goodId", good.getId());
				map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
				
				List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map );
				File thumbFile= fileList.get(0);
				
				scgObj.accumulate("goodId", goodId).accumulate("goodName", goodName).accumulate("price", price)
				.accumulate("count", count).accumulate("thumbPath", thumbFile.getUriPath()).accumulate("capacity", capacity);
				array.add(scgObj);
			}
			
			object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		}else{
			JSONArray array = new JSONArray();
			JSONObject scgObj = new JSONObject();
			Goods good= this.orderGoodMapper.getDetailByOrderId(orderId);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("goodId", good.getId());
			map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
			
			List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map );
			File thumbFile= fileList.get(0);
			scgObj.accumulate("goodId", good.getId()).accumulate("goodName", good.getName()).accumulate("price", good.getPrice())
			.accumulate("count", 1).accumulate("thumbPath", thumbFile.getUriPath()).accumulate("capacity", good.getCapacity());
			array.add(scgObj);
			object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		}
		return object.toString();
	}
	
	
	
	/**
	 * 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
	 * @return
	 */
	public String callUnifiedorder(String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				""
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		return null;
	}
	
	/**
	 * @throws IOException 
	 * @throws DocumentException 
	 * 微信支付完成回调
	 */
	public String wxNotify(HttpServletRequest request) throws IOException, DocumentException{
		 this.logger.debug("=================微信回调=========================");
		 String out_trade_no= request.getParameter("out_trade_no");
		 System.out.println("out_trade_no : " + out_trade_no);
		 this.logger.debug(out_trade_no);
		 InputStream inputStream = request.getInputStream();
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		 StringBuilder xml = new StringBuilder();
		 System.out.println("xml : " + xml);
		 String s = null;
		 while ((s = in.readLine()) != null) {
			 xml.append(s);
		 }
		 inputStream.close();
		 in.close();
		 
		 String xmlDoc = xml.toString();
	 	Document document= DocumentHelper.parseText(xmlDoc);
		Element rootEle= document.getRootElement();
		
//		String appid= rootEle.element("appid").getText(); //应用id
//		String bankType= rootEle.element("bank_type").getText(); //付款银行
//		String cashFee= rootEle.element("cash_fee").getText(); //现金支付金额
//		String feeType= rootEle.element("fee_type").getText(); //货币类型
//		String isSubscribe= rootEle.element("is_subscribe").getText(); //是否关注公众账号
// 		String mchId= rootEle.element("mch_id").getText(); //商户号
//		String nonceStr= rootEle.element("nonce_str").getText(); //随机字符串
//		String openid= rootEle.element("openid").getText();  //用户标识
		String outTradeNo=rootEle.element("out_trade_no").getText();  //商户订单号
		this.logger.debug("================订单号: " + outTradeNo + "===========================");
		System.out.println("========"+outTradeNo+"==============");
//		String resultCode= rootEle.element("result_code").getText(); //返回状态码
//		String sign= rootEle.element("sign").getText();  //签名
//		String timeEnd= rootEle.element("time_end").getText();  //支付完成时间
//		String totalFee= rootEle.element("total_fee").getText(); //总金额
//		String tradeType= rootEle.element("trade_type").getText(); //交易类型
//		String transactionId= rootEle.element("transaction_id").getText();
		
//		String outTradeNo=request.getParameter("out_trade_no");
		
		this.logger.debug("微信验证: " + this.checkSign(xmlDoc));
//		if (checkSign(xmlDoc)) {
			Orders order=this.m.findOrderByOrderNo(outTradeNo);
			order.setState(OrdersState.WAIT_ORDER.getValue()); //待接单
			this.logger.debug("=======================修改订单状态完成=================================");
			this.m.updateByPrimaryKeySelective(order);
			this.logger.debug("=====================微信回调完成=================================");
			return Constants.SUCCESS;
//		}else {
//			return Constants.FAIL;
//		}
		
	}
	
	
	/**
	 * 支付宝回调
	 * @return
	 * @throws AlipayApiException 
	 */
	public String alipayNotify(HttpServletRequest request) throws AlipayApiException{
		this.logger.debug("=================支付宝回调开始================================");
		String out_trade_no2 = request.getParameter("out_trade_no");// 商户订单号
		this.logger.debug("========订单号: " +   out_trade_no2 +"===========================================");
//    	this.logger.error("==============支付宝回调2=====================");
//    	this.logger.error("out_trade_no2 : " + out_trade_no2);
//    	System.out.println("out_trade_no2 : " + out_trade_no2);
		Map<String, String> params = new HashMap<String, String>();  
        Map<?,?> requestParams = request.getParameterMap();  
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {  
            String name = (String)iter.next();  
            String[] values = (String[])requestParams.get(name);  
            String valueStr = "";  
            for (int i = 0; i < values.length; i++) {  
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";  
            }  
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化  
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");  
            params.put(name, valueStr);  
        }  
        	String out_trade_no = request.getParameter("out_trade_no");// 商户订单号
        	this.logger.error("==============支付宝回调=====================");
        	this.logger.error("out_trade_no : " + out_trade_no);
			boolean signVerified = AlipaySignature.rsaCheckV1(params, Constants.ALIPAY_PUBLIC_KEY, "utf-8"); //调用SDK验证签名
			this.logger.debug("支付宝验证 : " + signVerified);
//			if(signVerified){
			   // TODO 验签成功后
			   //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
				Orders order=this.m.findOrderByOrderNo(out_trade_no);
				order.setState(OrdersState.WAIT_ORDER.getValue()); //待接单
				this.m.updateByPrimaryKeySelective(order);
				this.logger.debug("=====================支付宝回调完成================================");
				return Constants.SUCCESS;
//			}else{
//			    // TODO 验签失败则记录异常日志，并在response中返回failure.
//				return Constants.FAILURE;
//			}
	}
	
	
	
	
	
	
	  private boolean checkSign(String xmlString) {
	        Map<String, String> map = null;
	        try {
	            map = XMLUtil.doXMLParse(xmlString);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String signFromAPIResponse = map.get("sign").toString();
	        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
	            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
	            return false;

	        }
	        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
	        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
	        map.put("sign", "");
	        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
	        String signForAPIResponse = getSign(map);
	        if (!signForAPIResponse.equals(signFromAPIResponse)) {
	            //签名验不过，表示这个API返回的数据有可能已经被篡改了
	       //     System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);
	            return false;

	        }
	      //  System.out.println("恭喜，API返回的数据签名验证通过!!!");
	        return true;
	    }
	  
	  /**
	   * 
	   * @param map
	   * @return
	   */
	  private String getSign(Map<String, String> map) {
	        SortedMap<String, String> signParams = new TreeMap<String, String>();
	        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
	            signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
	        }
	        signParams.remove("sign");
	        String sign = PayCommonUtil.createSign("UTF-8", signParams);
	        return sign;
	    }
	
	
	/**
	 * 获取订单中的信息通过状态
	 * @return
	 */
	public String getOrdersByState(Integer state, Long buyerId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"state","buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", state);
		map.put("buyerId", buyerId);
		List<Orders> orderList= this.m.getOrdersByState(map);
		
		if(orderList.size()>0){
			JSONArray array = new JSONArray();
			for(int i=0;i<orderList.size();i++){
				Orders order= orderList.get(i);
				JSONObject orderObj = new JSONObject();
//			 Long buyerId= order.getBuyerId();
				String orderNo= order.getOrderNo();
				Integer orderState= order.getState();
				Float sumPrice= order.getSumPrice();
				Long orderId=order.getId();
				Date gt= order.getGenerateTime();
				List<ScgOrderRelease> sorList= this.scgOrderReleaseMapper.getSCGByOrderId(orderId);
				
				if(sorList.size()>0){
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
						Float lp= good.getLowestPrice();
						
						Map<String, Object> mapPic = new HashMap<String,Object>();
						mapPic.put("goodId", good.getId());
						mapPic.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
						
						List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapPic);
						com.xa.entity.File thumbFile = null;
						String uriPath= null;
						if(null != fileList && fileList.size() > 0){
							thumbFile=fileList.get(0);
							uriPath=thumbFile.getUriPath();
						}
						
						DeliveryAddress address= this.deliveryAddressMapper.getDefaultAddressByBuyerId(buyerId);
						
						goodObj.accumulate("goodName", good.getName()).accumulate("price", good.getPrice())
						.accumulate("capacity", capacity)
						.accumulate("count", count)
						.accumulate("goodId", goodId)
						.accumulate("info", info)
						.accumulate("hp", hp)
						.accumulate("lp", lp)
						.accumulate("address",null == address ? "": address.getAddress())
						.accumulate("filePath", uriPath==null ? "" : uriPath)
						;
						
						goodArray.add(goodObj);
					}
					
					orderObj.accumulate("buyerId", buyerId).accumulate("orderNo", orderNo).accumulate("orderState", orderState)
					.accumulate("gt", gt)
					.accumulate("orderId", orderId)
					.accumulate("sumPrice", sumPrice).accumulate(Constants.DATA, goodArray);
					array.add(orderObj);
				}else {
					JSONArray goodArray = new JSONArray();
					Goods good= this.orderGoodMapper.getDetailByOrderId(orderId);
					Map<String, Object> mapChild = new HashMap<String,Object>();
					mapChild.put("goodId", good.getId());
					mapChild.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
					
					List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapChild);
					File thumbFile = null;
					if(null != fileList && fileList.size() > 0){
						thumbFile = fileList.get(0);
					}
//					goodObj.accumulate("goodId", good.getId()).accumulate("goodName", good.getName()).accumulate("price", good.getPrice())
//					.accumulate("count", 1).accumulate("thumbPath", thumbFile.getUriPath()).accumulate("capacity", good.getCapacity());
					DeliveryAddress address= this.deliveryAddressMapper.getDefaultAddressByBuyerId(buyerId);
					JSONObject goodObj = new JSONObject();
					goodObj.accumulate("goodName", good.getName()).accumulate("price", good.getPrice())
					.accumulate("capacity", good.getCapacity())
					.accumulate("count", 1)
					.accumulate("goodId", good.getId())
					.accumulate("info", good.getInfo())
					.accumulate("hp", good.getHighestPrice())
					.accumulate("lp", good.getLowestPrice())
					.accumulate("address",null == address ? "": address.getAddress())
					.accumulate("filePath", null == thumbFile ? "" : thumbFile.getUriPath())
					;
					
					goodArray.add(goodObj);
					orderObj.accumulate("buyerId", buyerId).accumulate("orderNo", orderNo).accumulate("orderState", orderState)
					.accumulate("gt", gt)
					.accumulate("orderId", orderId)
					.accumulate("sumPrice", sumPrice).accumulate(Constants.DATA, goodArray);
					
					array.add(orderObj);
				}
				
			}
			object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		}else{

		}
		
		return object.toString();
	}
	
	
	/**
	 * 取消订单
	 * @return
	 */
	public String cancelOrder(Long orderId,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		    "orderId"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Orders order= this.m.selectByPrimaryKey(orderId);
		order.setState(OrdersState.HAS_BEEN_CANCELLED.getValue());
		this.m.updateByPrimaryKeySelective(order);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 接单
	 * @return
	 */
	public String orderRecev(Long buyHandId, Long orderId, String sign){
		
		return null;
	}
	
	
	/**
	 * 选择收货方式
	 * @return
	 */
	public String chooseWayGoods(Long orderId,Integer receWay, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"orderId","receWay"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Orders order= this.m.selectByPrimaryKey(orderId);
		order.setReceWay(receWay);
		this.m.updateByPrimaryKeySelective(order);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 添加收货地址
	 * @return
	 */
	public String addTakeAddress(Long orderId, Long addressId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		  		"orderId","addressId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Orders order= this.m.selectByPrimaryKey(orderId);
		order.setAddressId(addressId);
		this.m.updateByPrimaryKeySelective(order);
		object.accumulate(Constants.SUCCESS, true)
		;
		return object.toString();
	}
	 
}
