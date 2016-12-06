package com.xa.mapper;

import java.util.List;

import com.xa.entity.BuyHand;

public interface BuyHandMapper extends BaseMapper<BuyHand>{
   
	List<BuyHand> selectCustomerByCustomerAndPass(BuyHand buyHand);
	
	List<BuyHand> selectCustomerByMobile(BuyHand buyHand);
	
	List<BuyHand> selectCustomerByEmailAndInputIdAndPassport(BuyHand buyHand);
}