package com.ssafy.reservation.matching;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Matching;
import com.ssafy.reservation.basic.ReservationRepo;

@Service
public class MatchingServiceImpl implements MatchingService {

	@Autowired
	MatchingRepo mRepo;
	
	@Autowired
	ReservationRepo rRepo;
	
	@Override
	public Matching createMatching(Matching matching) throws SQLException {
		//추가기능 -> consultant의 consultant_reservation_count + 1 해주기
		return mRepo.save(matching);
	}

	@Override
	public void deleteMatching(int no) throws SQLException {
		//추가기능 -> consultant의 consultant_reservation_count - 1 해주기
		mRepo.deleteById(no);
	}

	@Override
	public List<Matching> readSendMatching(String consultantId) throws SQLException {
		return mRepo.findByConsultantId(consultantId);
	}

	@Override
	public List<Matching> readReceiveMatching(int reservationNo) throws SQLException {
		return mRepo.findByReservationNo(reservationNo);
	}

	@Override
	public void confirmMatching(int reservationNo, String consultantId, int matchingCost) {
		rRepo.confirmMatching(reservationNo, consultantId, matchingCost);//중요내용 옮기고
		mRepo.deleteByReservationNo(reservationNo);//싸악 삭제
	}

	
	
}
