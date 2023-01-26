package com.ssafy.reservation.matching;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Matching;

@Repository
public interface MatchingRepo extends JpaRepository<Matching,Integer>{
	//해당 전문가가 제안한 견적 중 확정나거나 반려되지 않고 남은 견적들을 가져온다
	List<Matching> findByConsultantId(String consultantId);
	//해당 상담요청에 제시된 견적들을 가져온다 -> 유저가 자신의 특정 상담에 온 견적들을 보기 위함
	List<Matching> findByReservationNo(int reservationNo);
	
	@Transactional
    @Modifying
    @Query(value = "delete from matching where reservation_no = ?1", nativeQuery = true)
	//상담-견적의 매칭이 확립되면 남은 견적들을 모두 삭제한다.
    void deleteByReservationNo(int reservationNo);
}
