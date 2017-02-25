package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.Buyers;

public interface BuyersMapper extends BaseMapper<Buyers>{
	
	Buyers findBuyerByMobileAndPass(Buyers buyers);
	
	Buyers findBuyerByMobile(String mobile);
	
	List<Buyers> searchBuyer(Map<String,Object> map);
}