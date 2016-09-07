package com.ld.page;

import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.opensymphony.xwork2.ActionContext;

/**
 * 设置分页参数
 * @author hao.huang
 *
 */
public class SetPageParam {

	private int page;
	private int rows;
	private Map<String, Object> params = null;
	private boolean haveParam = false;
	
	public SetPageParam(){
		ActionContext ctx = ActionContext.getContext();
		params = ctx.getParameters();
	}
	
	public void execute(){
		 Object pageObj = this.params.get("page");
		 Object rowsObj = this.params.get("rows");
		 if(null != pageObj){
			 String[] pageArr = (String[]) pageObj;
			 if(pageArr.length > 0){
				   this.page = Integer.valueOf(pageArr[0]);
				   this.haveParam = true;
			 }
		 }
		 
		 if(null != rowsObj){
			 String[] rowsArr = (String[]) rowsObj;
			 if(rowsArr.length > 0){
				 this.rows = Integer.valueOf(rowsArr[0]);
			 }
		 }
		
		
		 this.set();
	}
	
	private void set(){
		if(this.haveParam){
		//	int pageInt = --this.page * this.rows + 1 ;
			PageHelper.startPage(this.page, this.rows,true);
		}else{
			PageHelper.startPage(1, this.rows,true);
		}
	}
}
