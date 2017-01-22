package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.BuyHand;
import com.xa.service.BaseServiceInte;

public interface BuyHandService<T> extends BaseServiceInte<T> {

	
	
	
	public String getBuyhands(Integer pageNum,Integer pageSize);

}
