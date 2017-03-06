package com.xa.service;

import com.xa.entity.BuyerFocusOn;

public interface BuyerFocusOnService<T> extends BaseServiceInte<T> {

	
	public String addBuyerFocusOn(BuyerFocusOn buyerFocusOn,String sign);
}
