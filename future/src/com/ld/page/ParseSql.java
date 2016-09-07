package com.ld.page;

import java.util.ArrayList;
import java.util.List;




/**
 * ½âÎösql
 * @author hao.huang
 *
 */
public class ParseSql {

	private String nativeSql;
	private String start;
	private String limit;
	
	public ParseSql(String nativeSql, String start, String limit){
		this.nativeSql = nativeSql;
		this.start = start;
		this.limit = limit;
	}
	
	
	public void parse(){
		String words[] = this.nativeSql.split("\\S+");
		
		List<String> wordList = new ArrayList<String>();
		for(int i=0; i<words.length; i++){
			 wordList.add(words[i]);
		}
		
		FindKeyWord fkw = new FindKeyWord(wordList, this.start, this.limit);
		fkw.insertRowNo();
		fkw.insertStart();
		
	}
}
