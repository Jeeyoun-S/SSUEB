package com.ssafy.classroom.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.reservation.basic.ReservationRepo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RoomValidServiceImpl implements RoomValidService{

	@Autowired
	private ReservationRepo reservationRepo;
	
	//reservation에 유효한게 있는지 검사.
	@Override
	public boolean checkUser(String userId) {
		
		log.info(reservationRepo.readRoomDate(userId));
		
		return false;
	}

	@Override
	public boolean checkConsultant(String consultId) {
		
		return false;
	}

	

	
}
