package com.xa.enumeration;

public enum ReceWay {
   SINCE_THE_LIFT(1),  //自提
   ADDRESS(2)  //地址
   ;
   private Integer value;
   ReceWay(Integer value){
	 this.value=value;   
   }
public Integer getValue() {
	return value;
}
   
   
}
