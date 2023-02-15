package com.ssafy.user.info.partner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.User;

public interface PartnerUserRepository extends JpaRepository<User, String> {
	
	/**
	 * ID로 회원정보 불러오기
	 * @param id 사용자 아이디
	 * **/
	Optional<User> findById(String id);
	
	/**
	 * 사용자 정보 update
	 * @param user 사용자 정보
	 * **/
	@SuppressWarnings("unchecked")
	User save(User user);
	
}
