package com.ssafy.classroom.interceptor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.ReservationRepo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RoomValidServiceImpl implements RoomValidService{

	@Autowired
	private ReservationRepo reservationRepo;

	final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public boolean checkTime(Reservation reservation) {
		//현재시간과 비교. 유효시간검증.
		LocalDateTime stdTime= LocalDateTime.parse(reservation.getReservationDate(),dateFormat);
		
		LocalDateTime startTime = stdTime.minusMinutes(10);
		LocalDateTime endTime = stdTime.plusMinutes(40);	
		LocalDateTime now = LocalDateTime.now();
		
		if(now.isAfter(startTime) && now.isBefore(endTime)) {
			return true;
		}else {
			return false;
		}
	}

	public boolean checkUser(Reservation reservation,String userId){
		log.info("checkUser : ", reservation.getUserId());
		return userId.equals(reservation.getUserId());

	}


	public boolean checkConsultant(Reservation reservation,String userId) {
		return userId.equals(reservation.getConsultantId());
	
	}
	
	public boolean checkFinish(Reservation reservation) {
		return reservation.getReservationFinish() == 1;
	}

	@Override
	public boolean checkValid(int no) throws SQLException{
		log.info("checkValid");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String userId = userDetails.getUsername();
		String auth = userDetails.getAuthorities().toString();
		
		Reservation reservation = reservationRepo.findById(no).get();
		
		//유효시간 검사
		//유효하지않은 시간이면 false 반환.
		if(!checkTime(reservation)) return false;
		
		//끝난상담인지 검사
		if(checkFinish(reservation)) return false;
		
		//알맞은 user, consultant인지 검사.
		System.out.println(auth);
		System.out.println(auth.equals("[ROLL_USER]"));
		if(auth.equals("[ROLE_USER]")) {
			return checkUser(reservation,userId);
		}else {
			return checkConsultant(reservation,userId);
		}
	}

	

	
}
