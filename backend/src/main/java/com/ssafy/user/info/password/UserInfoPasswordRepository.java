package com.ssafy.user.info.password;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.User;

public interface UserInfoPasswordRepository extends JpaRepository<User, Integer> {
	
	/**
	 * 사용자 아이디로 비밀번호 가져와서 확인하기
	 * @param id 사용자 아이디
	 * **/
	UserInfoPasswordMapping findById(String id);
}
