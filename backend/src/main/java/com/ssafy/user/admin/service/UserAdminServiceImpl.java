package com.ssafy.user.admin.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;
import com.ssafy.user.admin.model.UserConsultant;
import com.ssafy.user.admin.model.UserConsultantDto;
import com.ssafy.user.admin.repository.UserAdminRepository;
import com.ssafy.user.logout.repository.UserLogoutPetRepository;
import com.ssafy.user.logout.repository.UserLogoutRepository;

@Service("userAdminService")
public class UserAdminServiceImpl implements UserAdminService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAdminServiceImpl.class);
	
	@Autowired
	UserAdminRepository userAdminRepository;

	/** 
	 * 전문가 회원 중 자격증 검증이 되지 않은 회원 조회
	 */
	@Override
	public List<UserConsultantDto> getUncertifiedConsultantList() {
		return userAdminRepository.getUncertifiedConsultantList(); 
	}

}
