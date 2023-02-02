package com.ssafy.user.info.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Pet;

public interface InfoPetRepository extends JpaRepository<Pet, Integer> {
	
	/**
	 * 사용자 아이디로 Pet 정보 가져오기
	 * @userId 사용자 아이디
	 * **/
	List<Pet> findByUserId(String userId);
}
