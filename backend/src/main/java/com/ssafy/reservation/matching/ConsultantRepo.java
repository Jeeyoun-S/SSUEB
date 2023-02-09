package com.ssafy.reservation.matching;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Matching;
import com.ssafy.reservation.matching.response.ConsultantInfo;

@Repository
public interface ConsultantRepo extends JpaRepository<Consultant,String>{
	@Query(value = "select c.*, u.user_name as consultant_name "
			+ "from consultant c, user u "
			+ "where c.id = u.id and c.id = ?1", nativeQuery = true)
	ConsultantInfo findConsultantInfo(String consultantId);
}
