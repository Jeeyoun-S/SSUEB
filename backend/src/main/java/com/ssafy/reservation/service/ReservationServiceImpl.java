package com.ssafy.reservation.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.ReservationRepo;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	ReservationRepo repo;

	@Override
	public int createReservation(Reservation reservation) throws SQLException {
		repo.save(reservation);
		return 1;
	}
	
}
