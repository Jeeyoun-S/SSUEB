package com.ssafy.reservation.matching;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Matching;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.ReservationRepo;
import com.ssafy.reservation.matching.response.MatchingConsultant;
import com.ssafy.reservation.matching.response.SendMatching;

@Service
public class MatchingServiceImpl implements MatchingService {

	@Autowired
	MatchingRepo mRepo;
	
	@Autowired
	ReservationRepo rRepo;
	
	@Autowired
	ConsultantRepo cRepo;
	
	@Override
	public Matching createMatching(Matching matching) throws SQLException {
		//추가기능 -> consultant의 consultant_reservation_count + 1 해주기
		Consultant consultant = cRepo.findById(matching.getConsultantId()).get();
		consultant.changeCount(1);
		cRepo.save(consultant);
		return mRepo.save(matching);
	}

	@Override
	public void deleteMatching(int no) throws SQLException {
		//추가기능 -> consultant의 consultant_reservation_count - 1 해주기
		Consultant consultant = cRepo.findById(mRepo.findById(no).get().getConsultantId()).get();
		consultant.changeCount(-1);
		cRepo.save(consultant);
		mRepo.deleteById(no);
	}

	@Override
	public List<SendMatching> readSendMatching(String consultantId) throws SQLException {
		return mRepo.findSendMatching(consultantId);
	}

	
	
	@Override
	public List<MatchingConsultant> readReceiveMatching(int reservationNo) throws SQLException {
		return mRepo.findReceiveMatching(reservationNo);
	}

	
	
	@Override
	public void confirmMatching(int reservationNo, String consultantId, int matchingCost) throws ParseException,SQLException{	
		//중요내용(컨설턴트 아이디, 가격)을 예약 테이블로 옮기고
		//원래는 Repo에서 쿼리문으로 업데이트 했는데 바꾼 이유
		Reservation res = rRepo.findById(reservationNo).get();
		res.setConsultantId(consultantId);
		res.setReservationCost(matchingCost);
		rRepo.save(res);
		
		//해당 상담에 제안된 견적들을 모두 삭제
		mRepo.deleteByReservationNo(reservationNo);
		
		//이 매칭의 전문가의 +- 20분 내의 견적들을 싹 삭제해둔다
		//해당 예약의 시간대 가져오기 -> 그 시간대 근처를 삭제하기 순으로 진행
		String resTime = res.getReservationDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date resDate = sdf.parse(resTime);//캘린더에 적용하기 위한 Date
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(resDate);//캘린더에 예약 시간을 적용
	    
	    //before< time < after 부분을 싸악 삭제할 것이다
	    cal.add(Calendar.MINUTE, 30);
	    String after = sdf.format(cal.getTime());//30분 전 시간
	    cal.add(Calendar.MINUTE, -60);
	    String before = sdf.format(cal.getTime());//30분 후 시간
	    
	    Consultant consultant = cRepo.findById(consultantId).get();
	    consultant.changeCount(-1 * mRepo.getCountByDateTime(before, after, consultantId));
	    cRepo.save(consultant);
	    
	    //이 시간 내의 전문가의 견적 삭제
	    mRepo.deleteByDateTime(before, after, consultantId);
	}

}
