package com.xa.service;

public interface AccountTransactionRecordsService<T> extends BaseServiceInte<T> {

	/**
	 * 获取钱包交易记录
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getRecordsByPaging(int pageNum, int pageSize, String sign);
}
