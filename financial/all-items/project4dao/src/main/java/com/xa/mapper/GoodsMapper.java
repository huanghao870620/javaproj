package com.xa.mapper;

import java.util.List;

import com.xa.entity.Goods;

public interface GoodsMapper extends BaseMapper<Goods>{
	
	List<Goods> selectTopTen();
	
	/**
	 * 获取商品根据分类
	 * @param classid
	 * @return
	 */
	List<Goods> getGoodsByClassId(Long classid);
}