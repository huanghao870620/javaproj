package com.xa.compare;

public class CompareCountry implements Comparable<CompareCountry>{

	
	private Character headChar;
	private String name;
	private Long imgId;
	private Long countryId;
	
	public CompareCountry(Character headChar, String name,Long imgId,Long countryId){
		this.headChar = headChar;
		this.name = name;
		this.imgId=imgId;
		this.countryId=countryId;
	}

	@Override
	public int compareTo(CompareCountry o) {
		  return ((int)this.headChar-48)-((int)o.headChar-48);
	}

	public Character getHeadChar() {
		return headChar;
	}

	public void setHeadChar(Character headChar) {
		this.headChar = headChar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	
	
	
}
