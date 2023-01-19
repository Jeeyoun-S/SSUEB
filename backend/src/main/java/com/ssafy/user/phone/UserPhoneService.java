package com.ssafy.user.phone;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.user.phone.response.MessageApiResponse;

@Service
public interface UserPhoneService {
	
	/**
	 * 문자메세지 보내는 API에 문자 전송 요청
	 * @param headers 요청 헤더
	 * @param userPhone 사용자 휴대폰 번호
	 * @param authNumber 인증 번호
	 * **/
	public ResponseEntity<MessageApiResponse> sendSMS(String userPhone, String authNumber)
			throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException,
			JsonProcessingException, URISyntaxException;
	
	/**
	 * 휴대폰 인증번호를 생성하고 저장
	 * @param userPhone 사용자 휴대폰번호
	 * @return 인증번호 6자리
	 * **/
	public String makeAuthNumber(String userPhone);
	
	/**
	 * 네이버 클라우드 플랫폼 요청 헤더 생성
	 * **/
	public HttpHeaders makeHeader(String method, String url) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException;	
	
	/**
	 * 네이버 클라우드 플랫폼 요청 헤더에 들어갈 signature 생성
	 * @param method 요청 방법
	 * @param timestamp 현재 시간의 밀리초
	 * @param url 요청을 보낼 주소
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalStateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * **/
	public String makeSignature(String method, String url, String timestamp) throws IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
}