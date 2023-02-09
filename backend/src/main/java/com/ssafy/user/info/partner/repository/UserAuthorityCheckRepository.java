package com.ssafy.user.info.partner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.UserAuthority;

public interface UserAuthorityCheckRepository extends JpaRepository<UserAuthority, String> {
	
	/**
	 * 사용자 아이디로 사용자 권한 불러오기
	 * @param id 사용자 아이디
	 * **/
	Optional<UserAuthority> findById(String id);
}