package com.ld.page;

import java.util.List;

/**
 * ·µ»Ø²ÎÊı
 * @author hao.huang
 *
 */
public class PageOut {
	

	private Page page;
	
	private List<?> retList;
	
	public PageOut(Page page, List<?> retList){
		 this.page = page;
		 this.retList = retList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<?> getRetList() {
		return retList;
	}

	public void setRetList(List<?> retList) {
		this.retList = retList;
	}
	
	
}
