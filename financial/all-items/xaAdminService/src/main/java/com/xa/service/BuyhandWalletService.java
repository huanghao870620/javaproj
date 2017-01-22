package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface BuyhandWalletService<T> extends BaseServiceInte<T> {
 
	public String getBalance(Long buyHandId,String sign);
}
