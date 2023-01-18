package com.ssafy.reservation.matching;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Reservation;

@Repository
public interface MatchingRepo extends JpaRepository<Reservation,Integer>{
	
}
