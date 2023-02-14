package com.ssafy.user.logout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;

@Service
public interface UserLogoutService {
	
	/** 
	 * id에 해당되는 금일 상담 건 수 조회
	 * @param id 조회할 유저 id
	 */
	List<Reservation> getTodayReservationCount(String id);
	
	/** 
	 * id에 해당되는 가장 최근 예약 내용 조회
	 * @param id 조회할 유저 id
	 */
	Reservation getRecentlyReservation(String id);
	/** 
	 * id에 해당되는 가장 최근 예약의 전문가 정보 조회
	 * @param consultantId 조회할 전문가 id 
	 */
	String getRecentlyReservationConsultant(String consultantId); 
	/** 
	 * id에 해당되는 가장 최근 예약의 반려동물 정보 조회
	 * @param reservationPetNo 조회할 반려동물 no
	 */
	Pet getRecentlyReservationPet(int reservationPetNo); 
}
