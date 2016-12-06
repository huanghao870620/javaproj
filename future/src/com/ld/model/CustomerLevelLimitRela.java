package com.ld.model;

import java.math.BigDecimal;

public class CustomerLevelLimitRela {
	private BigDecimal customerLevelId;
	private String viewPoint;
	private String inTransaction;
	private String aROOM;
	private String allCourseWareLevel;


	public BigDecimal getCustomerLevelId() {
		return customerLevelId;
	}

	public void setCustomerLevelId(BigDecimal customerLevelId) {
		this.customerLevelId = customerLevelId;
	}

	public String getAllCourseWareLevel() {
		return allCourseWareLevel;
	}

	public void setAllCourseWareLevel(String allCourseWareLevel) {
		this.allCourseWareLevel = allCourseWareLevel;
	}

	public String getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(String viewPoint) {
		this.viewPoint = viewPoint;
	}

	public String getInTransaction() {
		return inTransaction;
	}

	public void setInTransaction(String inTransaction) {
		this.inTransaction = inTransaction;
	}

	public String getaROOM() {
		return aROOM;
	}

	public void setaROOM(String aROOM) {
		this.aROOM = aROOM;
	}

	

}
