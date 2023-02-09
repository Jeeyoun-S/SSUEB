package com.ssafy.user.login.service;

import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;

@Service
public interface UserLoginService {
	
	/** 
	 * id에 해당되는 유저정보 조회
	 * @param userId 조회할 유저 id
	 */
	User getUserByUserId(String userId);
	
	/** 
	 * 전문가 자격증 검증 값 조회
	 * @param userId 조회할 유저 id
	 */
	Consultant getConsultantById(String userId);
}
