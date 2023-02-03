package com.ssafy.user.join.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Consultant;

public interface JoinConsultantRepository extends JpaRepository<Consultant, String> {
	
	/**
	 * 전문가 정보를 Consultant 테이블에 추가
	 * @param consultant Consultant 테이블 정보
	 * **/
	Consultant save(Consultant consultant);
	
	/**
	 * 전문가 가입 수락을 위해 Consultant 정보 가져오기
	 * @param id 전문가 아아디
	 * **/
	Optional<Consultant> findById(String id);
}
