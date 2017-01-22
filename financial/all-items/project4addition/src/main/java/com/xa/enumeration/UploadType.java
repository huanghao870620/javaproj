package com.xa.enumeration;
/**
 * 
 * @author burgess
 *
 */
public enum UploadType {
	
	BUYHAND(1L), /*买手*/
	XUNAN(2L),  /*寻安*/
	MERCHANTS(3L) /*商家*/
	;

	private Long value;
	
	UploadType(Long value){
		this.value = value;
	}
}
