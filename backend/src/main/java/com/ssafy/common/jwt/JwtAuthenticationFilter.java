package com.ssafy.common.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

// JWT를 위한 커스텀 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private JwtTokenProvider jwtTokenProvider;
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider; 
	}
	
	/**
	 * Token의 인증정보를 SecurityContext에 저장
	 * @param servletRequest, servletResponse, filterChain
	 * @return void
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		// i) request에서 token 정보 받기
		String token = resolveToken(httpServletRequest); 
		String requestURI = httpServletRequest.getRequestURI();
		
		// ii) 토큰 유효성 검증(validateToken 함수) 진행
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			// success) 정상 토큰이라면 SecurityContext에 저장
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.debug("Security Context에 '{}' 인증 정보를 저장하였습니다, uri: {}", authentication.getName(), requestURI);
		} 
		else {
			logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * Request Header에서 Token 정보 꺼내기
	 * @param servletRequest
	 * @return String
	 */
	private String resolveToken(HttpServletRequest httpServletRequest) {
		String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	
	
	

}
