package com.ssafy.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.user.login.repository.UserLoginRepositorySupport;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	@Autowired
	UserLoginRepositorySupport userLoginRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	/** 
	 * id에 해당되는 유저정보 조회
	 * @param userId 조회할 유저 id
	 */
	@Override
	public User getUserByUserId(String userId) {
		User user = userLoginRepositorySupport.findUserByUserId(userId).get();
		return user; 
	}
}
