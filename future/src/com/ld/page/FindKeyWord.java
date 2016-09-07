package com.ld.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 元素查找
 * @author hao.huang
 *
 */
public class FindKeyWord {

	private static final SqlNode keywords[] = { new SqlNode(0,"select"), new SqlNode(0,"where")};
	private List<String> sqlNodeList;
	private List<String> rownoList = new ArrayList<String>();
	private static final String rows[] = {"ROWNUM","AS","rowno"};
	private String start; // 开始游标
	private String limit; // 长度
	
	/**
	 * 
	 * @param sqlNodeList
	 * @param start
	 * @param limit
	 */
	public FindKeyWord(List<String> sqlNodeList, String start, String limit){
		this.start = start;
		this.limit = limit;
		this.sqlNodeList = sqlNodeList;
		for(int i=0; i<rows.length;i++){
			this.rownoList.add(rows[i]);
		}
	}
	
	
	/**
	 * 插入单个
	 * @param position
	 * @param node
	 */
	public void insertNode(int position, String node){
		   this.sqlNodeList.add(position, node);
	}
	
	/**
	 * 插入集合
	 * @param position
	 * @param nodes
	 */
	public void insertNodes(int position, List<String> nodes){
		 this.sqlNodeList.addAll(position, nodes);
	}
	
	/**
	 * 插入前面部分
	 * @param position
	 */
	public void insertFront(int position){
		 this.insertNodes(position, this.rownoList);
	}
	
	/**
	 * 插入开始游标
	 * @param position
	 */
	public void insertStart(int position){
		this.insertNode(position, this.start);
	}
	
	/**
	 * 插入分页长度
	 * @param position
	 */
	public void insertLimit(int position){
		this.insertNode(position, limit);
	}
	
	/**
	 * 寻找select
	 * @return
	 */
	public int findSelect(){
		 return this.findPosition(keywords[0], 0);
	}
	
	/**
	 * 寻找where
	 * @return
	 */
	public int findWhere(){
		return this.findPosition(keywords[1], 0);
	}
	
	/**
	 * 寻找位置
	 * @param node
	 * @param dupCount
	 * @return
	 */
	private int findPosition(SqlNode node, int dupCount){
		
		 for(int i=0; i<this.sqlNodeList.size(); i++){
			    if(this.sqlNodeList.get(i).equals(node.getNode())){
			    	 if(dupCount == node.getDupCount()){
			    		 return i;
			    	 }
			    }
		 }
		  return 0;
	}
	
	/**
	 * 插入rowno
	 */
	public void insertRowNo(){
		this.insertFront(this.findSelect());
	}
	
	
	/**
	 * 插入start
	 */
	public void insertStart(){
		this.insertStart(this.findWhere());
	}
	
	
	
	
}
