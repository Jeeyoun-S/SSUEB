package com.ssafy.reservation.basic;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.pet.response.ReservationPet;
import com.ssafy.reservation.pet.response.ReservationPetFinish;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer>{
	//전반적으로 상담 내용을 가져올 때 pet에 관한 정보도 같이 가져와야 할 것 같아서 전체적으로 join query를 사용.
	//select *을 하면 두 entity 모두에 있는 no에 대한 정보 때문에 실패 -> rno, pno을 사용한 response클래스를 만들어서 사용
	
	//consultantId가 not null -> 확정된 상담 -> 확정된 상담 중 끝나지 않은 상담 -> 곧 상담을 실시할 상담들을 가져온다 
	//끝나지 않고 곧 실시한 상담이 필요하므로 reservationFinish는 0으로 고정
	//해당 유저의 확정되어 곧 실시할 상담 내역들을 가져온다. 
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, r.reservation_cost as reservationCost, r.reservation_reason as reservationReason, "
			+ "p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo "
			+ "from reservation r, pet p "
			+ "where r.reservation_pet_no = p.no and "
			+ "r.consultant_id is not null and r.user_id = ?1 and r.reservation_finish = 0", nativeQuery = true)
	List<ReservationPet> findPartnerConfirmedReservation(String userId);
	
	//전문가 아이디로 찾는다 -> 상담은 확정이 됐다
	//reservationFinish가 0이다 -> 해당 전문가의 확정된 상담 내역 중 곧 실시할 상담을 불러온다
	//reservationFinish가 1이다 -> 해당 전문가가 완료한 상담 내역을 가져온다
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, r.reservation_cost as reservationCost, r.reservation_reason as reservationReason, "
			+ "p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo "
			+ "from reservation r, pet p "
			+ "where r.reservation_pet_no = p.no and "
			+ "r.consultant_id = ?1 and r.reservation_finish = 0", nativeQuery = true)
	List<ReservationPet> findConsultantConfirmedReservation(String consultantId);
	
	//전문가 아이디가 Null -> 확정되지 않은 상담
	//해당 유저의 신청한 상담 중 확정되지 않은 상담 내역을 가져온다. 
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo"
			+ " from reservation r inner join pet p on r.reservation_pet_no = p.no "
			+ "where r.consultant_id is null and r.user_id = ?1", nativeQuery = true)
	List<ReservationPet> findPartnerUnconfirmedReservation(String userId);
	
	//전문가 아이디가 Null -> 확정되지 않은 상담
	//를 모두 가져온다 -> 전문가가 제안할 수 있는 확정되지 않은 상담 목록 리스트를 전부 가져온다.
	//List<Reservation> findByConsultantIdIsNull(); //이전 상담기록만 가져오던 
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo"
			+ " from reservation r inner join pet p on r.reservation_pet_no = p.no where r.consultant_id is null", nativeQuery = true)
	List<ReservationPet> findAllUnconfirmedReservation();
	
	
	//아래 세 이미 완료된 상담들은 -> 진단기록이나 리뷰 내용들 전부 필요 -> 따로 하나 더 만들어야하나?
	
	//reservationFinish가 1이다 -> 해당 전문가가 완료한 상담 내역을 가져온다
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, r.reservation_dignosis_record as reservationDignosisRecord, "
			+ "r.review_grade as reviewGrade, r.review_comment as reviewComment, "
			+ "p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo "
			+ "from reservation r inner join pet p on r.reservation_pet_no = p.no "
			+ "where r.consultant_id = ?1 and r.reservation_finish = 1", nativeQuery = true)
	List<ReservationPetFinish> findConsultantPastReservation(String consultantId);
	
	
	//공개된 상담 기록들을 가져올 때 사용한다 -> 1을 넣고 사용 (0이면 공개되지 않은 상담기록)
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, r.reservation_dignosis_record as reservationDignosisRecord, "
			+ "r.review_grade as reviewGrade, r.review_comment as reviewComment, "
			+ "p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo "
			+ "from reservation r inner join pet p on r.reservation_pet_no = p.no "
			+ "where r.reservation_open = 1 and r.reservation_finish = 1", nativeQuery = true)
	List<ReservationPetFinish> findByReservationOpen(int reservationOpen);
	
	
	//해당 유저가 상담을 이미 받은 상담 내역을 가져온다.
	@Query(value = "select r.no as rno, r.user_id as userId, r.consultant_id as consultantId, r.reservation_date as reservationDate, "
			+ "r.reservation_consult_content as reservationConsultContent, r.reservation_dignosis_record as reservationDignosisRecord, "
			+ "r.review_grade as reviewGrade, r.review_comment as reviewComment, "
			+ "p.no as pno, p.pet_image as petImage, p.pet_name as petName, "
			+ "p.pet_type as petType, p.pet_variety as petVariety, p.pet_birth as petBirth, p.pet_info as petInfo "
			+ "from reservation r inner join pet p on r.reservation_pet_no = p.no "
			+ "where r.user_id = ?1 and r.reservation_finish = 1", nativeQuery = true)
	List<ReservationPetFinish> findByUserIdAndReservationFinish(String userId);
	
//	@Query(value ="select reservation_date from reservation where user_id = :userId", nativeQuery = true)
//	String readRoomDate(@Param("userId") String userId);
	
	//reservation_date만 뽑기 위해 직접 nativeQuery를 날람 -> 해당 유저의 상담 예정(확정 미확정 둘 다) 시간 대를 전부 가져온다
	@Query(value = "select reservation_date from reservation where user_id = ?1 and reservation_finish = 0", nativeQuery = true)
	List<String> readDateValidation(String userId);
	
	//시간이 지난 상담을 가져온다. openvidu객체 
	@Query(value = "SELECT no " + 
			"FROM reservation " + 
			"WHERE DATE_ADD(STR_TO_DATE(reservation_date, '%Y-%m-%d %H:%i:%S'), INTERVAL 40 MINUTE) < now() " +
			"and reservation_finish = 0", nativeQuery = true)
	Set<Integer> expiredRooms();
	
	
	//회의실 종료정보를 
	@Modifying
	@Query(value = "SET SQL_SAFE_UPDATES = 0", nativeQuery = true)
	void offSafeMode();
	
	
	@Modifying
	@Query(value = "UPDATE reservation " + 
			"SET reservation_finish = 1 " + 
			"WHERE DATE_ADD(STR_TO_DATE(reservation_date, '%Y-%m-%d %H:%i:%S'), INTERVAL 40 MINUTE) " +
			" < now() and reservation_finish = 0", nativeQuery = true)
	int updateRooms();
	
	
	@Modifying
	@Query(value = "SET SQL_SAFE_UPDATES = 1", nativeQuery = true)
	void onSafeMode();
	
	
}
