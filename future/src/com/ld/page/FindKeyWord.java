package com.ld.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Ԫ�ز���
 * @author hao.huang
 *
 */
public class FindKeyWord {

	private static final SqlNode keywords[] = { new SqlNode(0,"select"), new SqlNode(0,"where")};
	private List<String> sqlNodeList;
	private List<String> rownoList = new ArrayList<String>();
	private static final String rows[] = {"ROWNUM","AS","rowno"};
	private String start; // ��ʼ�α�
	private String limit; // ����
	
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
	 * ���뵥��
	 * @param position
	 * @param node
	 */
	public void insertNode(int position, String node){
		   this.sqlNodeList.add(position, node);
	}
	
	/**
	 * ���뼯��
	 * @param position
	 * @param nodes
	 */
	public void insertNodes(int position, List<String> nodes){
		 this.sqlNodeList.addAll(position, nodes);
	}
	
	/**
	 * ����ǰ�沿��
	 * @param position
	 */
	public void insertFront(int position){
		 this.insertNodes(position, this.rownoList);
	}
	
	/**
	 * ���뿪ʼ�α�
	 * @param position
	 */
	public void insertStart(int position){
		this.insertNode(position, this.start);
	}
	
	/**
	 * �����ҳ����
	 * @param position
	 */
	public void insertLimit(int position){
		this.insertNode(position, limit);
	}
	
	/**
	 * Ѱ��select
	 * @return
	 */
	public int findSelect(){
		 return this.findPosition(keywords[0], 0);
	}
	
	/**
	 * Ѱ��where
	 * @return
	 */
	public int findWhere(){
		return this.findPosition(keywords[1], 0);
	}
	
	/**
	 * Ѱ��λ��
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
	 * ����rowno
	 */
	public void insertRowNo(){
		this.insertFront(this.findSelect());
	}
	
	
	/**
	 * ����start
	 */
	public void insertStart(){
		this.insertStart(this.findWhere());
	}
	
	
	
	
}
