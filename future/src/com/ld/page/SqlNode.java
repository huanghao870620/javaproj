package com.ld.page;

public class SqlNode {
	
	private int dupCount;
	private String node;
	
	public SqlNode(){}
	
	public SqlNode(int dupCount, String node){
		this.dupCount = dupCount;
		this.node = node;
	}

	public int getDupCount() {
		return dupCount;
	}

	public void setDupCount(int dupCount) {
		this.dupCount = dupCount;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
	
	
	

}
