package com.ssafy.user.withdrawal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.user.withdrawal.repository.UserWithdrawalRepositorySupport;

@Service("userWithdrawalService")
public class UserWithdrawalServiceImpl implements UserWithdrawalService {

	@Autowired
	UserWithdrawalRepositorySupport userWithdrawalRepositorySupport;
	
	/** 
	 * 회원탈퇴 확인
	 * @param id 조회할 유저 id
	 */
	@Override
	public User getUserWithdrawal(String id) {
		User user = userWithdrawalRepositorySupport.getUserWithdrawal(id).get();
		return user;
	}
	
}
