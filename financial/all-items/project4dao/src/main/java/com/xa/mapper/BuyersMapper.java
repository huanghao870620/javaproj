package com.xa.mapper;

import com.xa.entity.Buyers;

public interface BuyersMapper extends BaseMapper<Buyers>{
	
	Buyers findBuyerByMobileAndPass(Buyers buyers);
	
	Buyers findBuyerByMobile(String mobile);
}