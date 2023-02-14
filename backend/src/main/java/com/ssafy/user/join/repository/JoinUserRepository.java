package com.ssafy.user.join.repository;

import java.util.List;
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
	
	/**
	 * 탈퇴하지 않은 전체 회원 목록 조회
	 * @param userDeleteFlag 탈퇴 여부
	 * @return 탈퇴하지 않은 회원 전체
	 * **/
	List<User> findByUserDeleteFlag(int userDeleteFlag);
}