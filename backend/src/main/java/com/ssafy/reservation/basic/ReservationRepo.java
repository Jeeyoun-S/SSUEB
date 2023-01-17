package com.ssafy.reservation.basic;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Reservation;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Long>{}
