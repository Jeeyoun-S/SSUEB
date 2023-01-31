package com.ssafy.common.util;

import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class ParameterCheck {
	
	/**
	 * 값이 비어있는지 확인
	 * @param value 확인할 값
	 * @return 비어있다면 true, 아니라면 false
	 * **/
	public boolean isEmpty(String value) {
		
		// 비어있거나 null 값인 경우
		if (value == null || value.equals("")) {
			return true;
		} return false;
	}
	
	/**
	 * id가 유효한지 확인
	 * @param id 아이디
	 * @return 유효하다면 true, 유효하지 않다면 false
	 * **/
	public boolean isValidId(String id) {
		
		// 중복 확인, 이메일 형식, 30자리 이하
		if (Pattern.matches("^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{2,4}$", id)) {
			if (id.length() <= 30) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * password가 유효한지 확인
	 * @param userPassword 비밀번호
	 * @return 유효하다면 true, 유효하지 않다면 false
	 * **/
	public boolean isValidPassword(String userPassword) {
		
		// 10자리 이상 20자리 이하, 영어/숫자/특수문자 필수 (가능한 특수문자 : ~!@$%*#^?&)
		if (Pattern.matches("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$", userPassword)) {
			return true;
		} return false;
	}
	
	/**
	 * nickname이 유효한지 확인
	 * @param userNickname 닉네임
	 * @return 유효하다면 true, 유효하지 않다면 false
	 * **/
	public boolean isValidNickname(String userNickname) {
		
		// 최대 22자, 특수문자 불가
		if (Pattern.matches("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]{1,22}$", userNickname)) {
			return true;
		} return false;
	}
	
	/**
	 * name이 유효한지 확인
	 * @param userName 이름
	 * @return 유효하다면 true, 유효하지 않다면 false
	 * **/
	public boolean isValidName(String userName) {
		
		// 최대 22자, 숫자 및 특수문자 불가
		if (Pattern.matches("^[가-힣|a-z|A-Z]{1,22}$", userName)) {
			return true;
		} return false;
	}
	
	/**
	 * phone number가 유효한지 확인
	 * @param userPhone 휴대폰 번호
	 * @return 유효하다면 true, 유효하지 않다면 false
	 * **/
	public boolean isValidPhone(String userPhone) {
		
		// 숫자 11자리
		if (Pattern.matches("^[0-9]{11}$", userPhone)) {
			return true;
		} return false;
	}
	
	/**
	 * userAlertFlag가 유효한지 확인
	 * @param userAlertFlag 알람방식 : 카카오(0), 이메일(1), 문자(2)
	 * @return 유효하다면 true, 유효하지 않다면 false
	 * **/
	public boolean isValueAlertFlag(int userAlertFlag) {
		
		// 0, 1, 2 중 하나
		if (userAlertFlag == 0 || userAlertFlag == 1 || userAlertFlag == 2) {
			return true;
		} return false;
	}
	
	/**
	 * 문자열 내에 띄어쓰기가 있는지 확인
	 * @param value 확인할 문자열
	 * @return 띄어쓰기가 있다면 true, 없다면 false
	 * **/
	public boolean isSpacing(String value) {
		
		// 공백이 없는지 확인
		if (Pattern.matches("[^\\s]+", value)) {
			return false;
		} return true;
	}
	
	/**
	 * 문자열 내에 특수문자가 있는지 확인
	 * @param value 확인할 문자열
	 * @return 특수문자가 있다면 true, 없다면 false
	 * **/
	public boolean isSpecialChar(String value) {
		
		// 특수문자가 없는지 확인
		if (Pattern.matches("[^`~!@#$%^&*|\\\\\\'\\\";:\\/?]+", value)) {
			return false;
		} return true;
	}
	
	/**
	 * 상담 가능한 반려동물 정보 확인
	 * @param petType 확인할  값
	 * @return 2진법 6자리가 맞다면 true, 그 외는 false
	 * **/
	public boolean isValidPetType(String petType) {
		
		// 0 또는 1로 구성된 6자리인지
		if (Pattern.matches("^[01]{6}$", petType)) {
			return true;
		} return false;
		
	}
	
	/**
	 * 파일이 byte 크기 이하인지 확인
	 * @param size size 이하인지 확인
	 * @param file 확인할 파일
	 * @return 2진법 6자리가 맞다면 true, 그 외는 false
	 * **/
	public boolean isValidFileSize(int size, MultipartFile file) {
		
		if (file.getSize() <= size) {
			return true;
		}
		return false;
	}
	
	/**
	 * 파일의 확장자가 png, jpg, pdf인지 확인
	 * @param file 확인할 파일
	 * @return 파일 확장자가  png, jpg, pdf라면 true, 그 외는 false
	 * **/
	public boolean isValidImage(MultipartFile file, boolean pdfValid) {
		
		// 파일명 전체
		String fileName = file.getOriginalFilename();
		
		// 확장자만 추출
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if (extension.equals("png") || extension.equals("jpg") || extension.equals("jfif") || extension.equals("pjpeg") || extension.equals("jpeg") || extension.equals("pjp")) {
			return true;
		} else if (pdfValid && extension.equals("pdf")) {
			return true;
		} return false;
	}
}