package com.ssafy.user.join.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Consultant;

public interface JoinConsultantRepository extends JpaRepository<Consultant, String> {
	
	/**
	 * 전문가 정보를 Consultant 테이블에 추가
	 * @param consultant Consultant 테이블 정보
	 * **/
	Consultant save(Consultant consultant);
}
