package com.ssafy.user.withdrawal.service;

import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;

@Service
public interface UserWithdrawalService {
	
	/** 
	 * 회원탈퇴 확인
	 * @param id 조회할 유저 id
	 */
	User getUserWithdrawal(String id);
}
