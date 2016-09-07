package com.ld.menu;

import java.util.List;

import com.ld.model.Menu;

/**
 * 生成单个子节点
 * @author hao.huang
 *
 */
public class GeneHtml4One {

	private MenuNode node;
	
	private StringBuilder sb ;
	
	private String basepath;
	
	public GeneHtml4One(MenuNode node, StringBuilder sb, String basepath){
		 this.basepath = basepath;
		 this.sb = sb;
		 this.node = node;
		 this.generateNode(this.node);
	}
	
	
	/**
	 * 生成节点html
	 */
	private void generateNode(MenuNode node){
		   Menu menu = node.getMenu();
		   List<MenuNode>  childs = node.getChilds();
		   
		   if(childs.size() > 0){
			   this.sb.append("<li><span>").append(menu.getMenuName()).append("</span>");
		   }else{
			   this.sb.append("<li><span><a href=\"").append(this.basepath).append(menu.getUrl()).append("\" >").append(menu.getMenuName()).append("</a></span>");
		   }
		   
		   
		   if(childs.size() > 0){
			   this.sb.append("<ul>");
			   for(int i=0; i<childs.size(); i++){
				   this.generateNode(childs.get(i));
			   }
			   this.sb.append("</ul>");
		   }
		   this.sb.append("</li>");
	}
	
	
	
}
