package com.ld.model;

import java.math.BigDecimal;

public abstract class AbstractUser {

	/**
	 * 获取账户
	 * @return
	 */
	public abstract String getAccount();
	
	
	/**
	 * 获取账户id
	 * @return
	 */
	public abstract BigDecimal getAccountId();
	
	/**
	 * 获取等级
	 */
	public abstract BigDecimal getLevelId();
	/**
	 * 获取姓名
	 */
	public abstract String getName();
	/**
	 * 获取图片id
	 */
	public abstract BigDecimal getCFileId();
	
}
