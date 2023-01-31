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
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE reservation r set r.reservation_finish = 1 where r.no = ?1", nativeQuery = true)
	void finishReservation(int no);//해당 no의 상담 테이블의 종료플래그를 1로 바꾼다 -> 상담 완료
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE reservation r set r.reservation_dignosis_record = ?2 where r.no = ?1", nativeQuery = true)
	void writeDignosis(int no, String reservationDignosisRecord);//해당 no의 상담 기록에 진단 기록을 넣는다
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE reservation r set r.review_comment = ?2, r.review_grade = ?3, r.reservation_open = ?4 where r.no = ?1", nativeQuery = true)
	void writeReview(int no, String reviewComment, int reviewGrade, int reservationOpen);//해당 no의 상담 기록에 리뷰를 작성한다

//	//매칭 확정 -> matchingserviceImple에서사용 -> reservation_date를 가져오는 작업 때문에 그냥 가져와서 save하는 업데이트 방식을 쓰기로 함
//	@Modifying
//	@Transactional
//	@Query(value = "UPDATE reservation r set r.consultant_id = ?2, r.reservation_cost = ?3 where r.no= ?1", nativeQuery = true)
//	void confirmMatching(int reservationNo, String consultantId, int matchingCost);//유저가 여러 견적 제안 중 하나를 고르면 그 정보가 해당 reservationNo의 상담에 들어간다.
	
	
	@Query(value = "select reservation_date from reservation where user_id = ?1 and reservation_finish = 0", nativeQuery = true)
	List<String> readDateValidation(String userId);
}
