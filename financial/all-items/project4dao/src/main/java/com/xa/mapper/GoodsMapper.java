package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.Goods;

public interface GoodsMapper extends BaseMapper<Goods>{
	
	List<Goods> selectTopTen();
	
	/**
	 * 获取商品根据分类
	 * @param classid
	 * @return
	 */
	List<Goods> getGoodsByClassId(Map<String, Object> map);
	
	
	/**
	 * 获取商品根据品牌id
	 * @param brandId
	 * @return
	 */
	List<Goods> getGoodsByBrandId(Map<String, Object> map);
	
	/**
	 * 根据国家获取商品
	 * @param countryId
	 * @return
	 */
	List<Goods> getGoodsByCountryId(Map<String, Object> map);
	
	/**
	 * 搜索商品
	 * @param map
	 * @return
	 */
	List<Goods> searchGoods(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<Goods> getGoodsByDeSession(Map<String, Object> map);
}