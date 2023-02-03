package com.ssafy.user.info.consultant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Consultant;

public interface UserInfoConsultantRepository extends JpaRepository<Consultant, String> {
	
	/**
	 * 전문가 정보 전체 불러오기
	 * @param id 사용자 아이디
	 * **/
	Optional<Consultant> findById(String id);
}
