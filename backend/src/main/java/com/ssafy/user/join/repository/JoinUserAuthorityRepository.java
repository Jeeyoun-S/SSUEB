package com.ssafy.user.join.repository;

import org.springframework.data.repository.Repository;

import com.ssafy.db.entity.UserAuthority;

public interface JoinUserAuthorityRepository extends Repository<UserAuthority, String> {
	
	/**
	 * 사용자 권한 테이블에 권한 추가
	 * @param userAuthority 사용자 권한 정보
	 * **/
	UserAuthority save(UserAuthority userAuthority);
}
