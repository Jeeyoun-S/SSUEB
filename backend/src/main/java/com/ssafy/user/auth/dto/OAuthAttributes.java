package com.ssafy.user.auth.dto;

import java.util.Map;

import com.ssafy.db.entity.User;
import com.ssafy.db.entity.auth.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	
	private Map<String, Object> attributes; 	// OAuth2가 반환하는 유저 정보 Map
	private String nameAttributeKey;
	private String name; 
	private String email; 
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes; 
		this.nameAttributeKey = nameAttributeKey; 
		this.name = name; 
		this.email = email;
	}
	
	/** 
	 * 카카오, 네이버 등 구분하여 다음 단계 실행
	 * @param 
	 * @return 
	 */
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> atributes) {
		return ofGoogle(userNameAttributeName, atributes); 
	}
	
	/** 
	 * OAuth를 통해 받아온 Google 유저 정보
	 * @param 
	 * @return 
	 */
	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	/** 
	 * User Entity를 return하는 메소드
	 * @param 
	 * @return 
	 */
	public User toEntity() {
		return User.builder()
				.name(name)
				.email(email)
				.role(Role.USER)
				.build();
	}
	
}
