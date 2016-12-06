package com.ld.page;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;


/**
 * 生成分页对象
 * @author hao.huang
 *
 */
public class GeneratePage {

	private Map<String,Object> params;
	
	private Page page = new Page();
	
	public GeneratePage(Map<String,Object> params){
		this.params = params;
	}
	
	
	/**
	 * 生成
	 */
	public void generate(){
		String size[] =(String[]) params.get("size");
		String index[] =(String[]) params.get("index");
		
		if(null != size){
			this.page.setSize(Integer.valueOf(size[0].toString()));
		}else{
			
		}
		
		if(null != index){
			this.page.setIndex(Integer.valueOf(index[0].toString()));
		}else{
			
		}
		
		ActionContext ctx = ActionContext.getContext();
		ctx.put("page", page);
	}
}
