package com.xa.service;

public interface GoodsSearchRecordService<T> extends BaseServiceInte<T>{

	/**
	 * 删除GSR通过买家id
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String delGSR(Long buyerId, String sign);
}
