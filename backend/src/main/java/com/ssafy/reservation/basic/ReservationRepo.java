package com.ssafy.reservation.basic;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Reservation;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer>{
	//consultantId가 not null -> 확정된 않은 상담 -> 확정된 상담 중 끝나지 않은 상담 -> 곧 상담을 실시할 상담들을 가져온다 
	//끝나지 않고 곧 실시한 상담이 필요하므로 reservationFinish는 0으로 고정
	//해당 유저의 확정되어 곧 실시할 상담 내역들을 가져온다. 
	List<Reservation> findByUserIdAndConsultantIdIsNotNullAndReservationFinish(String userId, int reservationFinish);
	
	//전문가 아이디로 찾는다 -> 상담은 확정이 됐다
	//reservationFinish가 0이다 -> 해당 전문가의 확정된 상담 내역 중 곧 실시할 상담을 불러온다
	//reservationFinish가 1이다 -> 해당 전문가가 완료한 상담 내역을 가져온다
	List<Reservation> findByConsultantIdAndReservationFinish(String consultantId, int reservationFinish);
	
	//전문가 아이디가 Null -> 확정되지 않은 상담
	//해당 유저의 신청한 상담 중 확정되지 않은 상담 내역을 가져온다. 
	List<Reservation> findByUserIdAndConsultantIdIsNull(String userId);
	
	//전문가 아이디가 Null -> 확정되지 않은 상담
	//를 모두 가져온다 -> 전문가가 제안할 수 있는 확정되지 않은 상담 목록 리스트를 전부 가져온다.
	List<Reservation> findByConsultantIdIsNull(); 
	
	//공개된 상담 기록들을 가져올 때 사용한다 -> 1을 넣고 사용
	List<Reservation> findByReservationOpen(int reservationOpen);
	
	//해당 유저가 상담을 이미 받은 상담 내역을 가져온다.
	List<Reservation> findByUserIdAndReservationFinish(String userId, int reservationFinish);
	
	@Query(value ="select reservation_date from reservation where user_id = :userId", nativeQuery = true)
	String readRoomDate(@Param("userId") String userId);
	
	//reservation_date만 뽑기 위해 직접 nativeQuery를 날람 -> 해당 유저의 상담 예정(확정 미확정 둘 다) 시간 대를 전부 가져온다
	@Query(value = "select reservation_date from reservation where user_id = ?1 and reservation_finish = 0", nativeQuery = true)
	List<String> readDateValidation(String userId);
}
