package com.ld.menu;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ld.model.Menu;

/**
 * 生成菜单
 * @author hao.huang
 *
 */
public class GenerateMenu {
	
	private List<Menu> menus;
	
	private Integer topLevel;  // 顶部临时变量
	
	private Map<BigDecimal, MenuNode> nodeMap = new HashMap<BigDecimal, MenuNode> ();
	
	public GenerateMenu(List<Menu> menus){
		 this.menus = menus;
		 
		 this.initMenuPool();
	}
	
	
	/**
	 * 初始化菜单池
	 */
	private void initMenuPool(){
		 for(int i=0; i < this.menus.size(); i++){
			  MenuNode node = new MenuNode();
			  node.setMenu(this.menus.get(i));
			  this.nodeMap.put(this.menus.get(i).getId(), node);
		 }
		 
		 Set<BigDecimal> keys = this.nodeMap.keySet();
		 for(BigDecimal bd : keys){
			 this.recuFilling(this.nodeMap.get(bd), 0);
		 }
		 
	}
	
	/**
	 * 递归填充
	 */
	private void recuFilling(MenuNode node, Integer level){
		 BigDecimal parentId = node.getMenu().getParentId();
		 level++;
		 if(parentId.compareTo(new BigDecimal("-1")) == 0){
			  this.topLevel = level;
			  node.setLevel(this.topLevel-level);			  
			  return ;
		 }else{
			 MenuNode parentNode = this.nodeMap.get(node.getMenu().getParentId());
			 if(! this.childIsExist(parentNode, node)){
				 parentNode.getChilds().add(node);
			 }
			 this.recuFilling(parentNode, level);
			 node.setLevel(this.topLevel-level);
		 }
	}
	
	
	/**
	 * 是否已经存在这个孩子
	 * @return
	 */
	private boolean childIsExist(MenuNode parentNode, MenuNode toAddNode){
		List<MenuNode> childs = parentNode.getChilds();
		for(int i=0; i < childs.size(); i++){
			MenuNode node = childs.get(i);
			BigDecimal id = node.getMenu().getId();
			if(id.compareTo(toAddNode.getMenu().getId()) == 0){
				return true;
			}
		}
		return false;
	}


	public Map<BigDecimal, MenuNode> getNodeMap() {
		return nodeMap;
	}
	
	

}
