package com.ssafy.user.join;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserAuthority;
import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;

@Service("UserJoinService")
public class UserJoinServiceImpl implements UserJoinService {
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	UserJoinRepository userJoinRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public boolean joinUser(JoinRequest joinRequest) {
		
		// 입력 받은 값 변수에 넣어주기
		String id = joinRequest.getId();
		String password = joinRequest.getUserPassword();
		String nickname = joinRequest.getUserNickname();
		String name = joinRequest.getUserName();
		String phone = joinRequest.getUserPhone();
		int alertFlag = joinRequest.getUserAlertFlag();
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && userJoinRepository.findById(id) == null && parameterCheck.isValidId(id)) {
			
			// 비밀번호 유효성 검사
			if (!parameterCheck.isEmpty(password) && parameterCheck.isValidPassword(password)) {
				
				// 닉네임 유효성 검사
				if (!parameterCheck.isEmpty(nickname) && parameterCheck.isValidNickname(nickname)) {
					
					// 이름 유효성 검사
					if (!parameterCheck.isEmpty(name) && parameterCheck.isValidName(name)) {
						
						// 휴대폰 번호 유효성 검사
						if (!parameterCheck.isEmpty(phone) && parameterCheck.isValidPhone(phone)) {
							
							// 알림 방식 유효성 검사
							if (parameterCheck.isValueAlertFlag(alertFlag)) {
								
								// 비밀번호 암호화하기
								password = passwordEncoder.encode(joinRequest.getUserPassword());
								
								// Request DTO에서 User DTO로
								User user = new User();
								user.setId(id);
								user.setUserPassword(password);
								user.setUserNickname(nickname);
								user.setUserName(name);
								user.setUserPhone(phone);
								user.setUserAlertFlag(alertFlag);
								
								// DB에 전달 받은 회원정보 저장해 두기
								userJoinRepository.save(user);
								
								return true;
							}
						}
					}
				}
			}
			
		} return false;
	}
	
	@Override
	public boolean joinConsultant(String id, ConsultantJoinRequest consultantJoinRequest) {
		
		// ! 이미지 및 동영상 파일을 저장할 폴더 경로 및 최대 크기 정하기
		// 이미지 파일 가져오기
		MultipartFile licenseImage = consultantJoinRequest.getConsultantLicenseCopyImage();
		
		// 파일 이름 가져오기
		String fileName = licenseImage.getOriginalFilename();
		
		// 파일 이름에서 확장자만 가져오기
		String extension = fileName.substring(fileName.lastIndexOf("."));
		
		// 파일명 생성하기
		String licenseName = UUID.randomUUID().toString() + extension;
		
		// 저장 경로 설정하기 : 현재는 빠르게 확인하기 위해 사용자 주소로 설정해 두었음.
		String path = "C:\\Users\\SSAFY";
		
		// File 객체 생성
		File target = new File(path, licenseName);
		
		// 폴더로 옮겨주기
		try {
			licenseImage.transferTo(target);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Consultant DTO 생성
		Consultant consultant = new Consultant();
		consultant.setConsultantLicenseNumber(consultantJoinRequest.getConsultantLicenseNumber());
		System.out.println(consultantJoinRequest.getConsultPetType());
		consultant.setConsultantLicenseCopyImage(licenseName);
		consultant.setConsultPetType(consultantJoinRequest.getConsultPetType());
		consultant.setId(id);
		consultant.setConsultantRate((double) 0);
		
		// consultant를 DB에 넣어주기
		userJoinRepository.save(consultant);
		
		return true;
	}

	@Override
	public boolean grantAuthority(String userId, String authorityName) {
		
		// 사용자 권한 저장하기
		userJoinRepository.save(new UserAuthority(userId, authorityName));
		
		return true;
	}
}
