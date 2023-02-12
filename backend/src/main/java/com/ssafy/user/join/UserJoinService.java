package com.ssafy.user.join;

import org.springframework.stereotype.Service;

import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;

@Service
public interface UserJoinService {
	
	/**
	 * 회원정보를 Request DTO에서 User DTO로 옮기고 DB에 저장하기
	 * @param joinRequest 사용자 정보
	 * **/
	public boolean joinUser(JoinRequest joinRequest, int role);
	
	/**
	 * 전문가 회원가입
	 * @param joinRequest 사용자 정보
	 * @param consultantJoinRequest 전문가 정보
	 * **/
	public boolean joinConsultant(String id, ConsultantJoinRequest consultantJoinRequest);
	
	/**
	 * 권한 부여
	 * @param userId 사용자 아이디
	 * @param authorityName 권한 이름 (ROLE_ADMIN, ROLE_CONSULTANT, ROLE_USER)
	 * **/
	public boolean grantAuthority(String id, String authorityName);
	
	/**
	 * 전달받은 User 정보 유효성 검사
	 * @param joinRequest 사용자 정보
	 * @param role 반려인 0, 전문가 1
	 * **/
	public boolean checkUserValid(JoinRequest joinRequest, int role);
	
	/**
	 * 반려인 회원가입 진행
	 * @param joinRequest 사용자 정보
	 * **/
	public boolean joinPartner(JoinRequest joinRequest);
}
