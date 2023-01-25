package com.ssafy.user.join;

import org.springframework.data.repository.Repository;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserAuthority;

public interface UserJoinRepository extends Repository<User, String> {

	/**
	 * 사용자 정보를 User 테이블에 추가
	 * @param user User 테이블 정보
	 * **/
	void save(User user);
	
	/**
	 * 전문가 정보를 Consultant 테이블에 추가
	 * @param consultant Consultant 테이블 정보
	 * **/
	void save(Consultant consultant);
	
	/**
	 * User 테이블에서 id가 있는 정보 찾기
	 * @param id User 테이블에서 찾을 id
	 * @return id에 해당하는 User 정보
	 * **/
	User findById(String id);
	
	/**
	 * 사용자 권한 테이블에 권한 추가
	 * @param userAuthority 사용자 권한 정보
	 * **/
	void save(UserAuthority userAuthority);
}