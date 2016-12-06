package com.ld.dto;

import java.math.BigDecimal;
import com.ld.model.RoomConfig;

public class RoomConfigDto extends RoomConfig{

	private BigDecimal  cfileId;
	/*private String fileFileName;
	private File file;
	
	  public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
  */
	public BigDecimal getCfileId() {
		return cfileId;
	}

	public void setCfileId(BigDecimal cfileId) {
		this.cfileId = cfileId;
	}
	
	
	
}
