package com.ld.page;


/**
 * ��ҳ����
 * @author hao.huang
 *
 */
public class Page {  
	
	public static final int PAGE_SIZE =10;
	
	private int size = PAGE_SIZE; // ÿҳ��С
	private int index = 1; // ��ʼ����
	private int totalRecord; // ������
	

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