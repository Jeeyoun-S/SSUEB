package com.ssafy.user.login.service;

import com.ssafy.db.entity.User;

public interface UserLoginService {
	
	/** 
	 * id에 해당되는 유저정보 조회
	 * @param userId 조회할 유저 id
	 */
	User getUserByUserId(String userId);
}
