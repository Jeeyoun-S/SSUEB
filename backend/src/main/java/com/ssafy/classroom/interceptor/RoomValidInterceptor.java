package com.ssafy.classroom.interceptor;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomValidInterceptor implements HandlerInterceptor{
	
	
	@Autowired
	private RoomValidServiceImpl roomValidService;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		
		//jwt 유효성/로그인된 사용자인지 유효성검사.
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			//로그인된 유저인지 확인.
			UserDetails userDetails = (UserDetails)principal;
			String userId = userDetails.getUsername();
			log.info(userDetails.getAuthorities().toString()); 

			
		} else {
			//로그인 안된 유저인경우.
			log.info("로그인 안된유저.");
	        response.getWriter().write("권한이 없는 사용자입니다.");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        return false;

		}
		
		
		
		return true;
	}
	
	

}
