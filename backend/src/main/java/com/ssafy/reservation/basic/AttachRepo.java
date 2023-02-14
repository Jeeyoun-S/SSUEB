package com.ssafy.reservation.basic;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Attach;

@Repository
public interface AttachRepo extends JpaRepository<Attach,Integer>{
	List<Attach> findByReservationNo(int reservationNo);
	
	@Transactional
	@Modifying
    @Query(value = "delete from attach where reservation_no = ?1", nativeQuery = true)
	void deleteByReservationNo(int reservationNo);
}
