package com.ssafy.reservation.matching;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.db.entity.Matching;

public interface MatchingService {
	Matching createMatching(Matching matching) throws SQLException;
	void deleteMatching(int no) throws SQLException;
	List<Matching> readSendMatching(String consultantId) throws SQLException;
	List<Matching> readReceiveMatching(int reservationNo) throws SQLException;
	void confirmMatching(int reservationNo, String consultantId, int matchingCost);//매칭 확정 -> 해당 견적에 대한 일부정보를 해당 no에 해당하는 예약 테이블로 옮긴 후 해당 no에 대한 matching 싹 삭제
}



