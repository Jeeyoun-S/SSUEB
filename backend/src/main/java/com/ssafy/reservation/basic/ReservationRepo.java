package com.ssafy.reservation.basic;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Reservation;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer>{
	List<Reservation> findByUserIdAndConsultantIdIsNotNullAndReservationFinish(String userId, int reservationFinish);
	List<Reservation> findByConsultantIdAndReservationFinish(String consultantId, int reservationFinish);
	List<Reservation> findByUserIdAndConsultantIdIsNull(String userId);
	List<Reservation> findByConsultantIdIsNull();
	List<Reservation> findByReservationOpen(int reservationOpen);
	List<Reservation> findByUserIdAndReservationFinish(String userId, int reservationFinish);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE reservation r set r.reservation_finish = 1 where r.no = ?1", nativeQuery = true)
	void finishReservation(int no);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE reservation r set r.reservation_dignosis_record = ?2 where r.no = ?1", nativeQuery = true)
	void writeDignosis(int no, String reservationDignosisRecord);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE reservation r set r.review_comment = ?2, r.review_grade = ?3, r.reservation_open = ?4 where r.no = ?1", nativeQuery = true)
	void writeReview(int no, String reviewComment, int reviewGrade, int reservationOpen);
}
