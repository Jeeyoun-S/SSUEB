package com.ssafy.user.logout.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Reservation;

@Repository
public interface UserLogoutRepository extends JpaRepository<Reservation, Integer>{
	
	/** 
	 * id에 해당되는 금일 상담 건 수 조회
	 * @param id 조회할 유저 id
	 */
	@Transactional
    @Modifying
    @Query(value = "select * from reservation where user_id = ?1 and date(reservation_date) = current_date()", nativeQuery = true)
	List<Reservation> getTodayReservationCount(String id);
	
}
