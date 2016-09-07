package com.ld.service;



import com.opensymphony.xwork2.ActionContext;


public interface AdviceTypeService<T> extends BaseServiceInte<T> {

	public void getAllAdviceType(ActionContext ctx);
}
