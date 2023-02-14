package com.ssafy.user.admin.service;

public interface MailService {
	// HTML String 생성(메일 내용)
	String makeHtml();
	
	// 메일 전송
	String sendMail(String email);
	
	// 입력한 메세지로 이메일 전송
	String customMail(String email, String message);
	
	// 이메일 전송 로직
	String mailSender(String email, String html, String subject);
}
