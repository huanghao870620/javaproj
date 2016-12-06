package com.ld.page;

/**
 * ²éÑ¯²ÎÊý
 * @author hao.huang
 *
 * @param <T>
 */
public class PageIn {

	private Page page;
	
	private Object dto;
	
	public PageIn(Page page, Object dto){
		  this.page = page;
		  this.dto = dto;
	}
	
	public PageIn(){}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Object getDto() {
		return dto;
	}

	public void setDto(Object dto) {
		this.dto = dto;
	}
	
	
	
}
