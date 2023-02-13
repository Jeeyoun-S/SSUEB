package com.ssafy.classroom.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.classroom.interceptor.RoomValidService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RoomValidFilter extends GenericFilterBean{
	
	
	@Autowired
	RoomValidService roomValidService;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	
//	log.info("-----------------------------------------------, roomValidFilter");
//	roomValidService.checkUser("ㅇㅇㅇ");
	chain.doFilter(request, response);
		
	}
		
	
	



}
