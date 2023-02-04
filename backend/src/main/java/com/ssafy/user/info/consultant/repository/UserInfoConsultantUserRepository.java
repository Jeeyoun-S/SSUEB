package com.ssafy.user.info.consultant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;

public interface UserInfoConsultantUserRepository extends JpaRepository<User, String> {
	
	/**
	 * 회원정보 전체 불러오기
	 * @param id 사용자 아이디
	 * **/
	Optional<User> findById(String id);
	
	/**
	 * 회원 정보 저장하기
	 * @return 
	 * **/
	User save(User user);
}
