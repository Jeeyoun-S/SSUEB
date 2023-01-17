package com.ssafy.user.join;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;
import com.ssafy.user.join.response.DuplicateId;
import com.ssafy.user.join.response.JoinResponse;

@Service
public class UserJoinService {
	
	@Autowired
	UserJoinRepository userJoinRepository;
	
	@Autowired
	private ServletContext servletContext;
	
	/**
	 * 회원정보를 Request DTO에서 User DTO로 옮기고 DB에 저장하기
	 * **/
	public void join(JoinRequest joinRequest) {
		
		// Request DTO에서 User DTO로
		User user = new User();
		user.setId(joinRequest.getId());
		
		// 비밀번호 암호화하기
		user.setUserPassword(joinRequest.getUserPassword());
		user.setUserNickname(joinRequest.getUserNickname());
		user.setUserName(joinRequest.getUserName());
		user.setUserPhone(joinRequest.getUserPhone());
		user.setUserRole(joinRequest.getUserRole());
		user.setUserAlertFlag(joinRequest.getUserAlertFlag());
		
		// DB에 전달 받은 회원정보 저장해 두기
		userJoinRepository.save(user);
		
		// 로그인하기
		
		return;
	}
	
	/**
	 * 반려인 회원가입
	 * **/
	public ResponseEntity<?> joinPartner(JoinRequest joinRequest) {
		
		// 역할을 반려인으로 설정
		joinRequest.setUserRole(0);
		
		// User Table에 넣기
		join(joinRequest);
		
		// response 값 생성 및 반환
		JoinResponse joinResponse = new JoinResponse("success", "회원가입에 성공했습니다.");
		return ResponseEntity.status(200).body(joinResponse);
	}
	
	/**
	 * 전문가 회원가입
	 * **/
	public ResponseEntity<?> joinConsultant(JoinRequest joinRequest, ConsultantJoinRequest consultantJoinRequest) {
		
		// 역할을 전문가로 설정
		joinRequest.setUserRole(1);
		
		// User Table에 넣기
		join(joinRequest);
		
		// 이미지 파일 가져오기
		MultipartFile licenseImage = consultantJoinRequest.getConsultantLicenseCopyImage();
		
		// 파일 이름 가져오기
		String fileName = licenseImage.getOriginalFilename();
		
		// 저장 경로 설정하기
		String uploadPath = servletContext.getRealPath("/licenseImage");
		
		// 1. 되긴 하나 legacy하다고 (파일을 복사해서 넣는 방법)
//		File target = new File(uploadPath, fileName);
//		try {
//			FileCopyUtils.copy(licenseImage.getBytes(), target);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// ! 이미지 및 동영상 파일을 저장할 폴더 경로 정하기
		// 2. 새로운 방법 (파일을 옮기는 방법)
		File target = new File(uploadPath + "/" + fileName);
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
		consultant.setConsultantLicenseCopyImage(fileName);
		consultant.setConsultPetType(consultantJoinRequest.getConsultPetType());
		consultant.setId(joinRequest.getId());
		
		// consultant를 DB에 넣어주기
		userJoinRepository.save(consultant);
		
		// response 값 생성 및 반환
		JoinResponse joinResponse = new JoinResponse("success", "회원가입에 성공했습니다.");
		return ResponseEntity.status(200).body(joinResponse);
	}

	public ResponseEntity<?> phoneAuth() {
		return null;
	}
	
	public ResponseEntity<?> phoneConfirm() {
		return null;
	}

	public ResponseEntity<?> duplicateId(String id) {
		
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
