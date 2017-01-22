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
	List<Goods> getGoodsByClassId(Long classid);
	
	
	/**
	 * 获取商品根据品牌id
	 * @param brandId
	 * @return
	 */
	List<Goods> getGoodsByBrandId(Long brandId);
	
	/**
	 * 根据国家获取商品
	 * @param countryId
	 * @return
	 */
	List<Goods> getGoodsByCountryId(Long countryId);
	
	/**
	 * 搜索商品
	 * @param map
	 * @return
	 */
	List<Goods> searchGoods(Map<String, Object> map);
}