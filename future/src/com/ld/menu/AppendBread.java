package com.ld.menu;

import java.util.List;

import com.ld.model.Menu;

/**
 *Æ´½ÓÃæ°üÐ¼ 
 * @author hao.huang
 *
 */
public class AppendBread {

	private List<Menu> breadList; //
	private String contextPath;
	private StringBuilder sb = new StringBuilder();
	
	public AppendBread(List<Menu> breadList,String contextPath){
		this.breadList = breadList;
		this.contextPath = contextPath;
	}
	
	
	/**
	 * 
	 */
	public void execute(){
		this.sb.append("<h1 class=\"map\">");
		 for(int i=this.breadList.size()-1; i>=0; i--){
			     Menu menu = this.breadList.get(i);
			    sb.append("<a href=\"").append(this.contextPath).append(menu.getUrl()).append("\">");
			    sb.append(menu.getMenuName()).append("</a>");
			    if(i != 0){
			    	sb.append("&gt;");
			    }
		 }
		 this.sb.append("</h1>");
	}
	
	/**
	 * 
	 * @return
	 */
	public StringBuilder getBufStr(){
		this.execute();
		return this.sb;
	}
}
