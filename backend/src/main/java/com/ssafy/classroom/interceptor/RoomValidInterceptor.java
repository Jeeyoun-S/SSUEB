package com.ssafy.classroom.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
	
		//시간 체크, jwt 토큰 체크.
		
		//securityContext통해 userId, authorization 검사
		log.info(SecurityContextHolder.getContext().toString());
		
		//jwt 유효성/로그인된 사용자인지 유효성검사.
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		UserDetails userDetails = (UserDetails)principal;
//		
//		String userId = userDetails.getUsername();
//		log.info(userDetails.getAuthorities().toString());
		
		//시간유효성체크. 유효성 관련 예외 던지기.
//		roomValidService.checkUser(userId);
		log.info("Roomvalid interceptor good -------------------------------------");
		
		
		
		return true;
	}
	
	

}
