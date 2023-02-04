package com.ssafy.user.pet;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Pet;

public interface UserPetRepository extends JpaRepository<Pet, Integer> {
	
	/**
	 * 반려동물 정보 저장하기
	 * @param pet 반려동물 정보 DTO
	 * @return 
	 * **/
	@SuppressWarnings("unchecked")
	Pet save(Pet pet);

	
	/**
	 * 반려동물 삭제
	 * @param no 반려동물 번호
	 * **/
	@Transactional
	int deleteByNo(int no);
	
	/**
	 * no으로 반려동물 조회하기
	 * @param no 반려동물 번호
	 * **/
	Pet findByNo(int no);
}
