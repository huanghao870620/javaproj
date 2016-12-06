package com.ld.menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author hao.huang
 * 生成菜单字符串
 */
public class GenerateMenuString {

	private Map<BigDecimal, MenuNode> nodeMap;
	private StringBuilder sb;
	private String basepath;
	private List<MenuNode> topMenu = new ArrayList<MenuNode>(); // 顶层菜单
	
	public GenerateMenuString(Map<BigDecimal, MenuNode> nodeMap,StringBuilder sb, String basepath){
		this.basepath = basepath;
		this.sb = sb;
		this.nodeMap = nodeMap;
		this.dealGenerate();
	}
	
	
	/**
	 *  处理生成
	 */
	private void dealGenerate(){
		Collection<MenuNode> nodes = this.nodeMap.values();
		for(MenuNode node : nodes){
			  Integer level = node.getLevel();
			  if(level == 0){
				  this.topMenu.add(node);
			  }
		}
		
		
		sb.append("<ul id=\"red\"  >");
		for(int i=0; i<this.topMenu.size(); i++){
			new GeneHtml4One(this.topMenu.get(i),sb,this.basepath);
		}
		sb.append("</ul>");
	}
}
