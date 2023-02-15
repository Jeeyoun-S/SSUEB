package com.ssafy.user.withdrawal.repository;

import com.ssafy.db.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWithdrawalRepository extends JpaRepository<User, Long> {

	/**
	 * 회원탈퇴 진행 (userDeleteFlag 1로 set)
	 * @param User
	 * @param User 사용자 정보
	 * **/
	User save(User user);
}