package com.xa.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.PropertiesEditor;

import com.meterware.httpunit.MessageBodyWebRequest;

public class DatePropertyEditor extends PropertiesEditor  {
	
	private String format;
	
	public DatePropertyEditor(String format){
		this.format = format;
	}

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		super.setAsText(arg0);
		
		SimpleDateFormat sdf = new SimpleDateFormat(this.format);
		Date date = null;
		try {
			date = sdf.parse(arg0);
			this.setValue(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setValue(Object arg0) {
		super.setValue(arg0);
	}
	
	
	
	
}
