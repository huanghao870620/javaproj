package com.ld.service;



import com.opensymphony.xwork2.ActionContext;


public interface MineralService<T> extends BaseServiceInte<T> {

	public void getAllMineral(ActionContext ctx);
}
