package com.ssafy.user.info.consultant;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.user.info.consultant.request.ConsultantInfoRequest;

@Service
public class UserInfoConsultantService {
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	/**
	 * 전문가 회원정보 확인
	 * @param consultantInfoRequest 전문가 정보
	 * **/
	public boolean isValidConsultantInfo(ConsultantInfoRequest consultantInfoRequest) {
		
		String id = consultantInfoRequest.getId();
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
			
			String userName = consultantInfoRequest.getUserName();
			if (!parameterCheck.isEmpty(userName) && parameterCheck.isValidName(userName)) {
				
				String userPassword = consultantInfoRequest.getUserPassword();
				if (parameterCheck.isEmpty(userPassword) || (!parameterCheck.isEmpty(userPassword) && parameterCheck.isValidPassword(userPassword))) {
					
					int userAlertFlag = consultantInfoRequest.getUserAlertFlag();
					if (parameterCheck.isValidAlertFlag(userAlertFlag)) {
						
						String userPhone = consultantInfoRequest.getUserPhone();
						if (!parameterCheck.isEmpty(userPhone) && parameterCheck.isValidPhone(userPhone)) {
							
							
							String consultantPetType = consultantInfoRequest.getConsultantPetType();
							if (!parameterCheck.isEmpty(consultantPetType) && parameterCheck.isValidPossiblePetType(consultantPetType)) {
								
								MultipartFile consultantProfile = consultantInfoRequest.getConsultantProfile();
								if (consultantProfile == null || (parameterCheck.isValidFileSize(5000000, consultantProfile) && parameterCheck.isValidImage(consultantProfile, false))) {
									
									String consultantIntro = consultantInfoRequest.getConsultantIntro();
									if (consultantIntro == null || (consultantIntro != null && consultantIntro.length() < 250)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}