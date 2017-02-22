package com.xa.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.dto.ActivityDto;
import com.xa.entity.Activity;
import com.xa.entity.DebrisSession;
import com.xa.entity.DsGood;
import com.xa.entity.FastBuySession;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.Orders;
import com.xa.entity.ScgDg;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.PhotoType;
import com.xa.enumeration.ScgState;
import com.xa.mapper.ActivityMapper;
import com.xa.mapper.DebrisSessionMapper;
import com.xa.mapper.DsGoodMapper;
import com.xa.mapper.FastBuySessionMapper;
import com.xa.mapper.FbsDsMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.mapper.ScgDgMapper;
import com.xa.mapper.ScgOrderReleaseMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.ShoppingCartGoodsService;
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
	
	@Autowired
	private ScgOrderReleaseMapper scgOrderReleaseMapper;
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private DsGoodMapper dsGoodMapper;
	
	@Autowired
	private ScgDgMapper scgDgMapper;
	
	@Autowired
	private FbsDsMapper fbsDsMapper;
	
	@Autowired
	private FastBuySessionMapper fastBuySessionMapper;
	
	@Autowired
	private DebrisSessionMapper debrisSessionMapper;
	
	
	/**
	 * 添加商品到购物车
	 */
	public String addGoodsToCart(ShoppingCartGoods scg,Long dgId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"goodsId","shoppingCartId","count","id","dgId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
//		DsGood dsGood= this.dsGoodMapper.selectByPrimaryKey(dgId);
//		Long dsId= dsGood.getDsId();
//		Integer inventory= dsGood.getInventory();
		
		
		if(scg.getId() == null){
			
			Map<String, Object> scgcMap = new HashMap<String,Object>();
			scgcMap.put("goodId", scg.getGoodsId());
			scgcMap.put("cartId", scg.getShoppingCartId());
			List<ShoppingCartGoods> scgList= this.m.getSCGByGoodIdAndShoppingCartId(scgcMap );
			if(null != scgList && scgList.size() > 0){
				
			}else {
				//添加
				scg.setState(ScgState.NOT_IN_ORDER.getValue()); //不在订单中
				this.m.insert(scg);
			}
			
		}else {
			//修改
			this.m.updateByPrimaryKey(scg);
		}
		
		// 秒杀活动
		if(null != dgId){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("dgId", dgId);
			map.put("scgId", scg.getId());
			List<ScgDg> sdList= this.scgDgMapper.getSDBySCGIDAndDgId(map );
			if(null != sdList && sdList.size() == 0){
				ScgDg sd = new ScgDg();
				sd.setDgId(dgId);
				sd.setScgId(scg.getId());
				scgDgMapper.insert(sd);
			}
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
//		orders.setState(OrdersState.HAS_BEEN_SHIPPED.getValue()); // 已发货
		this.ordersMapper.insert(orders);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 获取所有购物车中的商品信息
	 * @return
	 */
	public String getAllCartGoods(Long cartId,Integer pageNum,Integer pageSize, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"cartId","pageNum","pageSize"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		PageHelper.startPage(pageNum, pageSize);
		Page<ShoppingCartGoods> scgPage=(Page<ShoppingCartGoods>)this.m.getSCGByCartId(cartId);
		List<ShoppingCartGoods> scgs=scgPage.getResult();
		
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
//				List<ActivityDto> activityDtos = this.activityMapper.selectActivityByGoodId(goodId);
//				boolean flag=false;
//				ActivityDto activityDto = null;
//				if(activityDtos.size()>0){
//					flag=true;
//					activityDto= activityDtos.get(0);
//				}else {
//					flag=false;
//				}
				
				Goods good= this.goodsMapper.selectByPrimaryKey(goodId);
				String goodName= good.getName();
				Float price = good.getPrice();
				
				
				DsGood dsGood= this.dsGoodMapper.getDGBySCGID(scgId);
				Long dgId=null;
				if(null != dsGood){
					dgId= dsGood.getId();
				}
				
				Long fbsId= this.fbsDsMapper.getFBSIDByGoodId(goodId);
				List<DsGood> dgList= this.dsGoodMapper.getDsGoodByGoodId(goodId);
				DsGood dGood = null;
				if(null != dgList && dgList.size() > 0){
					dGood= dgList.get(0);
					Map<String, Object> dsMap = new HashMap<String,Object>();
					dsMap.put("fbsId", fbsId);
					dsMap.put("dsId", dGood.getDsId());
					List<DebrisSession> dsList= debrisSessionMapper.getDsByFbsIdAndDsId(dsMap);
					if(null != dsList&&dsList.size() > 0){
//					   Date startDate = dsList.get(0).getCronTime();
						DebrisSession ds= debrisSessionMapper.selectByPrimaryKey(dGood.getDsId());
					   Date startDate = ds.getCronTime();
					   Date endDate = dsList.get(0).getCronTime();
					   Date now =new Date();
					   if(now.after(startDate) && now.before(endDate)){
						   if(null != fbsId){
								FastBuySession fastBuySession= this.fastBuySessionMapper.selectByPrimaryKey(fbsId);
								Float discount= fastBuySession.getDiscount();
								price=price*discount;
							}
					   }
					}
				}
				
				scgObj
				.accumulate("count", count)
				.accumulate("filePath", thumbFile.getUriPath())
				.accumulate("price", price)
				.accumulate("goodName", goodName)
				.accumulate("scgId", scgId)
//				.accumulate("flag", flag)
				.accumulate("goodId", good.getId())
				.accumulate("dgId", dgId ==null ? "" : dgId)
				;
				
				
//				if(null != activityDto){
//					Long activityId= activityDto.getActivityId();
//					 Activity activity = this.activityMapper.selectByPrimaryKey(activityId);
//					 Float activityPrice= activity.getPrice();
//					 Long activityCount= activity.getCount();
//					 Float sill= activity.getSill();
//					 Date addTime= activity.getAddTime();
//					 Date upTime= activity.getUpTime();
//					 scgObj.accumulate("sill", sill)
//					 .accumulate("activityCount", activityCount)
//					 .accumulate("activityPrice", activityPrice)
//					 ;
//				}
				
				array.add(scgObj);
			}
			object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		}else {
			object.accumulate(Constants.SUCCESS, true);
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
