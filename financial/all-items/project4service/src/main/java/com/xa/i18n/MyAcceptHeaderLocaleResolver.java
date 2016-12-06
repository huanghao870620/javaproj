package com.xa.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class MyAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver{

	private Locale myLocale;
	
	public Locale resolveLocale(HttpServletRequest request){
		return this.myLocale;
	}
	
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale){
		this.myLocale = locale;
	}
}
