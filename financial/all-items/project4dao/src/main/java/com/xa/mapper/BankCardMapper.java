package com.xa.mapper;

import java.util.List;

import com.xa.entity.BankCard;

public interface BankCardMapper extends BaseMapper<BankCard>{
	
	List<BankCard> getCardByCustomer(Long buyHandId);
}