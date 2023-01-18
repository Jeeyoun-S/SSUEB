package com.ssafy.reservation.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ssafy.db.entity.Reservation;

public interface ReservationService {
	Reservation createReservation(Reservation reservation) throws SQLException;
	Reservation readReservation(int no) throws SQLException;
	void deleteReservation(int no) throws SQLException;
	void finishReservation(int no) throws SQLException;
	void writeDignosis(int no, String reservationDignosisRecord) throws SQLException;
	void writeReview(int no, String reviewComment, int reviewGrade, int reservationOpen) throws SQLException;
	
	
	List<Reservation> getPartnerConfirmedReservation(String userId) throws SQLException;
	List<Reservation> getConsultantConfirmedReservation(String consultantId) throws SQLException;
	List<Reservation> getPartnerUnconfirmedReservation(String userId) throws SQLException;
	List<Reservation> getAllUnconfirmedReservation() throws SQLException;
}
