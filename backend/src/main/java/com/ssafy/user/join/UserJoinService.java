package com.ssafy.user.join;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;
import com.ssafy.user.join.response.DuplicateId;
import com.ssafy.user.join.response.JoinResponse;

@Service
public class UserJoinService {
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	UserJoinRepository userJoinRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * 회원정보를 Request DTO에서 User DTO로 옮기고 DB에 저장하기
	 * @param joinRequest 사용자 정보
	 * **/
	public boolean join(JoinRequest joinRequest) {
		
		// 입력 받은 값 변수에 넣어주기
		String id = joinRequest.getId();
		String password = joinRequest.getUserPassword();
		String nickname = joinRequest.getUserNickname();
		String name = joinRequest.getUserName();
		String phone = joinRequest.getUserPhone();
		int role = joinRequest.getUserRole();
		int alertFlag = joinRequest.getUserAlertFlag();
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && userJoinRepository.findById(id) == null &&parameterCheck.isValidId(id)) {
			
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
								user.setUserRole(role);
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
	
	/**
	 * 반려인 회원가입
	 * @param joinRequest 사용자 정보
	 * **/
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<JoinResponse> joinPartner(JoinRequest joinRequest) {
		
		// 역할을 반려인으로 설정
		joinRequest.setUserRole(0);
		
		// User Table에 넣기
		boolean result = join(joinRequest);
		
		// DB에 넣었다면
		if (result) {
			
			// 로그인하기
			
			// response 값 생성 및 반환
			return ResponseEntity.status(200).body(new JoinResponse("success", "회원가입에 성공했습니다."));
		}
		
		return ResponseEntity.status(200).body(new JoinResponse("failure", "회원가입에 실패했습니다."));
	}
	
	/**
	 * 전문가 회원가입
	 * @param joinRequest 사용자 정보
	 * @param consultantJoinRequest 전문가 정보
	 * **/
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<JoinResponse> joinConsultant(JoinRequest joinRequest, ConsultantJoinRequest consultantJoinRequest) {
		
		// 상담 가능한 동물 종류 가져오기
		String petType = consultantJoinRequest.getConsultPetType();
		
		// 상담 가능한 동물 유효성 검사
		if (!parameterCheck.isEmpty(petType) && parameterCheck.isValidPetType(petType)) {
			
			// 역할을 전문가로 설정
			joinRequest.setUserRole(1);
			
			// User Table에 넣기
			boolean result = join(joinRequest);
			
			// DB에 정보 넣기 성공
			if (result) {
				
				// ! 이미지 및 동영상 파일을 저장할 폴더 경로 정하기
				// 이미지 파일 가져오기
				MultipartFile licenseImage = consultantJoinRequest.getConsultantLicenseCopyImage();
				
				// 파일 이름 가져오기
				String fileName = licenseImage.getOriginalFilename();
				
				// 파일 이름에서 확장자만 가져오기
				String extension = fileName.substring(fileName.lastIndexOf("."));
				
				// 파일명 생성하기
				String consultantLicenseCopyImage = UUID.randomUUID().toString() + extension;
				
				// 저장 경로 설정하기 : 현재는 빠르게 확인하기 위해 사용자 주소로 설정해 두었음.
				String path = "C:\\Users\\SSAFY";
				
//				1. 되긴 하나 legacy하다고 (파일을 복사해서 넣는 방법)
//				File target = new File(uploadPath, fileName);
//				try {
//					FileCopyUtils.copy(licenseImage.getBytes(), target);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
				// 2. 다른 방법 (파일을 옮기는 방법)
				File target = new File(path, consultantLicenseCopyImage);
				
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
				consultant.setConsultantLicenseCopyImage(consultantLicenseCopyImage);
				consultant.setConsultPetType(petType);
				consultant.setId(joinRequest.getId());
				consultant.setConsultantRate((double) 0);
				
				// consultant를 DB에 넣어주기
				userJoinRepository.save(consultant);
				
				// response 값 생성 및 반환
				return ResponseEntity.status(200).body(new JoinResponse("success", "회원가입에 성공했습니다."));
				
			}
		}
		
		return ResponseEntity.status(200).body(new JoinResponse("failure", "회원가입에 실패했습니다."));
	}

	public ResponseEntity<?> phoneAuth(String userPhone) {
		
		return null;
	}
	
	public ResponseEntity<?> phoneConfirm(String authNumber) {
		
		return null;
	}
	
	/**
	 * 사용자가 입력한 이메일이 DB에 있는지 확인
	 * @param id 사용자가 입력한 이메일
	 * **/
	public ResponseEntity<DuplicateId> duplicateId(String id) {
		
		// 입력 받은 id가 DB에 있는지 조회
		User user = userJoinRepository.findById(id);
		
		// DB에 입력받은 id가 없는 경우
		if (user == null) {
			return ResponseEntity.status(200).body(new DuplicateId("success"));
		}
		
		// DB에 입력받은 id가 있는 경우
		return ResponseEntity.status(200).body(new DuplicateId("failure"));
	}
	
}
