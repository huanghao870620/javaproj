package com.xa.enumeration;
/**
 * 银行类型
 * @author burgess
 *
 */
public enum BankType {

	INDUSTRIAL_AND_COM(1L), /* 工商银行 */
	CONS_BANK(2L), /* 建设银行 */
	BANK_OF_CHINA(3L), /* 中国银行 */
	AGRIC_CHINA(4L), /* 中国农业银行 */
	BAN_COMM(5L), /* 交通银行 */
	MERCH_BANK(6L)/* 招商银行 */
	;

	private Long value;

	BankType(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
