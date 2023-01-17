package com.ssafy.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.user.login.repository.UserLoginRepositorySupport;

/**
 *  user 로그인 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	//@Autowired
	//UserRepository userRepository;
	
	@Autowired
	UserLoginRepositorySupport userLoginRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	// [/partner] 해당 id의 회원정보 조회
	@Override
	public User getUserByUserId(String userId) {
		User user = userLoginRepositorySupport.findUserByUserId(userId).get();
		
		return user; 
	}
}
