package com.ssafy.db.entity.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 각 사용자 권한을 관리할 Enum 클래스 Role 
 */
@Getter
@RequiredArgsConstructor
public enum Role {
	
	USER("ROLE_USER", "일반 회원"), 
	CONSULTANT("ROLE_CONSULTANT", "전문가"), 
	ADMIN("ROLE_ADMIN", "관리자");
	
	private final String key; 
	private final String title; 

}
