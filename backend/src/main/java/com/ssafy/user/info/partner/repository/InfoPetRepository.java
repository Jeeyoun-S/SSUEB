package com.ssafy.user.info.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Pet;

public interface InfoPetRepository extends JpaRepository<Pet, Integer> {
	
	/****/
	List<Pet> findByUserId(String userId);
}
