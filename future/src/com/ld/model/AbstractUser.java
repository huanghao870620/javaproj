package com.ld.model;

import java.math.BigDecimal;

public abstract class AbstractUser {

	/**
	 * ��ȡ�˻�
	 * @return
	 */
	public abstract String getAccount();
	
	
	/**
	 * ��ȡ�˻�id
	 * @return
	 */
	public abstract BigDecimal getAccountId();
	
	/**
	 * ��ȡ�ȼ�
	 */
	public abstract BigDecimal getLevelId();
	/**
	 * ��ȡ����
	 */
	public abstract String getName();
	/**
	 * ��ȡͼƬid
	 */
	public abstract BigDecimal getCFileId();
	
}
