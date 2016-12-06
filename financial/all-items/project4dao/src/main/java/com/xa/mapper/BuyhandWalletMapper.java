package com.xa.mapper;

import com.xa.entity.BuyhandWallet;

public interface BuyhandWalletMapper extends BaseMapper<BuyhandWallet>{
	
	BuyhandWallet getWalletByBuyHandId(Long buyHandId);
}