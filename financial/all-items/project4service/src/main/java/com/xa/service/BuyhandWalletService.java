package com.xa.service;

public interface BuyhandWalletService<T> extends BaseServiceInte<T> {
 
	public String getBalance(Long buyHandId,String sign);
}
