package com.ssafy.reservation.service;

import java.sql.SQLException;
import com.ssafy.db.entity.Reservation;

public interface ReservationService {
	int createReservation(Reservation reservation) throws SQLException;
}
