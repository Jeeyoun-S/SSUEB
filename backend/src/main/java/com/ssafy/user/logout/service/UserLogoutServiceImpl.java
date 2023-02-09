package com.ssafy.user.logout.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Reservation;
import com.ssafy.user.logout.repository.UserLogoutRepository;

@Service("userLogoutService")
public class UserLogoutServiceImpl implements UserLogoutService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLogoutServiceImpl.class);

	@Autowired
	UserLogoutRepository userLogoutRepository;

	/** 
	 * id에 해당되는 금일 상담 건 수 조회
	 * @param id 조회할 유저 id
	 */
	@Override
	public List<Reservation> getTodayReservationCount(String id) {
		//logger.info("## [UserLogoutServiceImpl]: getTodayReservationCount - 금일 예약 건 수 조회 실행  / 조회할 id: {}", id);
		return userLogoutRepository.getTodayReservationCount(id); 
	}

}
