package com.xa.service;

import com.xa.entity.BankCard;

public interface BankCardService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param buyHandId
	 * @param sign
	 * @return
	 */
	public String getBankCardList(Long buyHandId, String sign);
	
	/**
	 * 添加银行卡
	 * @param bankCard
	 * @param sign
	 * @return
	 */
	public String addBankCard(BankCard bankCard, String sign);
	
	/**
	 * 删除银行卡
	 * @param cardId
	 * @param sign
	 * @return
	 */
	public String removeBankCard(Long cardId,String sign);
}
