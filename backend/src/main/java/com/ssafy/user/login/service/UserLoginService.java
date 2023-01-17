package com.ssafy.user.login.service;

import com.ssafy.db.entity.User;

/**	
 *  user 로그인 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의
 */
public interface UserLoginService {
	// [/partner] 해당 id의 회원정보 조회
	User getUserByUserId(String userId);
	
	// [/partner] 발급받은 accessToken 저장
	
}
