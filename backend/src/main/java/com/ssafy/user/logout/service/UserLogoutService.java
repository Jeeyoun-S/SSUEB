package com.ssafy.user.logout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Reservation;
import com.ssafy.db.entity.User;

@Service
public interface UserLogoutService {
	
	/** 
	 * id에 해당되는 금일 상담 건 수 조회
	 * @param id 조회할 유저 id
	 */
	List<Reservation> getTodayReservationCount(String id);
}
