package com.ssafy.user.logout.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;
import com.ssafy.user.logout.repository.UserLogoutPetRepository;
import com.ssafy.user.logout.repository.UserLogoutRepository;

@Service("userLogoutService")
public class UserLogoutServiceImpl implements UserLogoutService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLogoutServiceImpl.class);

	@Autowired
	UserLogoutRepository userLogoutRepository;
	
	@Autowired
	UserLogoutPetRepository userLogoutPetRepository;

	/** 
	 * id에 해당되는 금일 상담 건 수 조회
	 * @param id 조회할 유저 id
	 */
	@Override
	public List<Reservation> getTodayReservationCount(String id) {
		//logger.info("## [UserLogoutServiceImpl]: getTodayReservationCount - 금일 예약 건 수 조회 실행  / 조회할 id: {}", id);
		return userLogoutRepository.getTodayReservationCount(id); 
	}

	/** 
	 * id에 해당되는 가장 최근 예약 내용 조회
	 * @param id 조회할 유저 id
	 */
	@Override
	public Reservation getRecentlyReservation(String id) {
		return userLogoutRepository.getRecentlyReservation(id);
	}
	/** 
	 * id에 해당되는 가장 최근 예약의 전문가 정보 조회
	 * @param consultantId 조회할 전문가 id 
	 */
	@Override
	public String getRecentlyReservationConsultant(String consultantId) {
		return userLogoutRepository.getRecentlyReservationConsultant(consultantId);
	}
	/** 
	 * id에 해당되는 가장 최근 예약의 반려동물 정보 조회
	 * @param reservationPetNo 조회할 반려동물 no
	 */
	@Override
	public Pet getRecentlyReservationPet(int reservationPetNo) {
		return userLogoutPetRepository.findByNo(reservationPetNo);
	}

}
