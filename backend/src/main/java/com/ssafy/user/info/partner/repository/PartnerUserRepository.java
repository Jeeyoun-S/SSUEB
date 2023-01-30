package com.ssafy.user.info.partner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.User;

public interface PartnerUserRepository extends JpaRepository<User, String> {
	
	/****/
	Optional<User> findById(String id);
	
}
