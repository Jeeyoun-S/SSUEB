package com.ssafy.reservation.matching;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Matching;

@Repository
public interface MatchingRepo extends JpaRepository<Matching,Integer>{
	List<Matching> findByConsultantId(String consultantId);
	List<Matching> findByReservationNo(int reservationNo);
	
	@Transactional
    @Modifying
    @Query(value = "delete from matching where reservation_no = ?1", nativeQuery = true)
    void deleteByReservationNo(int reservationNo);
}
