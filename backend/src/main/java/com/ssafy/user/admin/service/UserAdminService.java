package com.ssafy.user.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;
import com.ssafy.user.admin.model.UserConsultant;
import com.ssafy.user.admin.model.UserConsultantDto;

@Service
public interface UserAdminService {
	
	/** 
	 * 전문가 회원 중 자격증 검증이 되지 않은 회원 조회
	 */
	List<UserConsultantDto> getUncertifiedConsultantList();
	
}
