package com.ssafy.reservation.matching;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Matching;
import com.ssafy.reservation.matching.response.MatchingConsultant;
import com.ssafy.reservation.matching.response.SendMatching;

@Repository
public interface MatchingRepo extends JpaRepository<Matching,Integer>{
	//해당 전문가가 제안한 견적 중 확정나거나 반려되지 않고 남은 견적들을 가져온다
	List<Matching> findByConsultantId(String consultantId);
	
	
	//해당 상담요청에 제시된 견적들을 가져온다 -> 유저가 자신의 특정 상담에 온 견적들을 보기 위함
	@Query(value = "select m.*, u.user_name as consultant_name, c.* "
			+ "from matching m, user u, consultant c "
			+ "where m.consultant_id = u.id and u.id = c.id and reservation_no = ?1", nativeQuery = true)
	List<MatchingConsultant> findReceiveMatching(int reservationNo);
	
	@Query(value = "select m.no as matching_no, m.matching_cost, m.matching_comment, "
			+ "r.reservation_date, r.reservation_consult_content, r.no as reservation_no, p.* "
			+ "from matching m, reservation r, pet p "
			+ "where m.reservation_no = r.no and r.reservation_pet_no = p.no and m.consultant_id = ?1", nativeQuery = true)
	List<SendMatching> findSendMatching(String consultantId);
	
	@Transactional
    @Modifying
    @Query(value = "delete from matching where reservation_no = ?1", nativeQuery = true)
	//상담-견적의 매칭이 확립되면 해당 상담예약에 남은 견적들을 모두 삭제한다.
    void deleteByReservationNo(int reservationNo);
	
	@Query(value = "select count(*) from matching where ?1 < reservation_date and reservation_date < ?2 and consultant_id = ?3", nativeQuery = true)
	int getCountByDateTime(String before, String after, String consultantId);
	
	@Transactional
    @Modifying
    @Query(value = "delete from matching where ?1 < reservation_date and reservation_date < ?2 and consultant_id = ?3", nativeQuery = true)
	//상담-견적의 매칭이 확립되면 그 전문가의 이 상담예정 시간 근처의 견적들을 삭제한다.
    void deleteByDateTime(String before, String after, String consultantId);
	
	
	
}
