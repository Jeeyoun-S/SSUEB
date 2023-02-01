package com.ssafy.reservation.matching;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.ssafy.db.entity.Matching;

public interface MatchingService {
	//견적을 생성한다.
	Matching createMatching(Matching matching) throws SQLException;
	
	//해당no의 견적을 삭제한다 -> 전문가가 견적을 보내고 취소하고 싶은 경우
	void deleteMatching(int no) throws SQLException;
	
	//해당 전문가가 보낸 견적 제안 목록을 가져온다 -> 확인, 취소 등 관리를 위함
	List<Matching> readSendMatching(String consultantId) throws SQLException;
	
	//유저(파트너)가 자신의 특정 상담에 온 견적들을 확인한다
	List<Matching> readReceiveMatching(int reservationNo) throws SQLException;
	
	//매칭 확정 -> 해당 견적에 대한 일부정보를 해당 no에 해당하는 예약 테이블로 옮긴 후 해당 no에 대한 matching 싹 삭제
	void confirmMatching(int reservationNo, String consultantId, int matchingCost) throws ParseException,SQLException;
}



