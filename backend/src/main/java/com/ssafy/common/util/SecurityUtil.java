package com.ssafy.common.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	/** 
	 * Security Context의 Authentication 객체를 사용하여 userId를 반환
	 * @param void
	 * @return Optional<String>
	 */
	public static Optional<String> getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null) {
			logger.debug("#SecuriyUtil - getCurrentUser# Security Context에 인증 정보가 없습니다.");
			return Optional.empty(); 
		}
		
		String userId = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
			userId = springSecurityUser.getUsername(); 
		}
		else if (authentication.getPrincipal() instanceof String) {
			userId = (String) authentication.getPrincipal(); 
		}
		
		return Optional.ofNullable(userId);
	}
}
