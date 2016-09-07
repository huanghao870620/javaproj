package com.ld.dto;

import java.math.BigDecimal;

import com.ld.model.PrivateMessage;

public class PrivateMessageDto extends PrivateMessage{
	private static final long serialVersionUID = 421775865861050157L;
	
	private BigDecimal teacherId;// ½²Ê¦ID


	public BigDecimal getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(BigDecimal teacherId) {
		this.teacherId = teacherId;
	}

}
