package com.ssafy.user.info.consultant;

import org.springframework.stereotype.Service;

import com.ssafy.user.info.consultant.request.ConsultantInfoRequest;

@Service
public class UserInfoConsultantService {
	
	/**
	 * 전문가 회원정보 확인
	 * @param consultantInfoRequest 전문가 정보
	 * **/
	public boolean isValidConsultantInfo(ConsultantInfoRequest consultantInfoRequest) {
		return true;
	}
}