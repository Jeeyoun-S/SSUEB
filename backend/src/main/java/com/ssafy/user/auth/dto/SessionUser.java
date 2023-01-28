package com.ssafy.user.auth.dto;

import java.io.Serializable;

import com.ssafy.db.entity.User;

import lombok.Getter;

/** 
 * 직렬화 기능을 가진 User 엔티티 클래스
 * - User 엔티티를 직렬화 한 클래스
 */
@Getter
public class SessionUser implements Serializable {
	
	private String name; 
	private String email; 
	
	public SessionUser(User user) {
		this.name = user.getUserName(); 
		this.email = user.getId(); 
	}

}
