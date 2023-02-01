package com.ssafy.user.join.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.ssafy.db.entity.User;

public interface JoinUserRepository extends Repository<User, String> {

	/**
	 * 사용자 정보를 User 테이블에 추가
	 * @param user User 테이블 정보
	 * @return 
	 * **/
	User save(User user);
	
	/**
	 * User 테이블에서 id가 있는 정보 찾기
	 * @param id User 테이블에서 찾을 id
	 * @return id에 해당하는 User 정보
	 * **/
	Optional<User> findById(String id);
}