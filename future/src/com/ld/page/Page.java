package com.ld.page;


/**
 * 分页对象
 * @author hao.huang
 *
 */
public class Page {  
	
	public static final int PAGE_SIZE =10;
	
	private int size = PAGE_SIZE; // 每页大小
	private int index = 1; // 开始行数
	private int totalRecord; // 总条数
	

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	
	
	
}  