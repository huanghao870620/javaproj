package com.xa.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
/**
 * 
 * @author buregess
 *
 */
@Component
public class DateConvert implements Converter<String, Date> {

	/**
	 * 
	 */
	@Override
	public Date convert(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
	}

}
