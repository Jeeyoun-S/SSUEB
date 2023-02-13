package com.ssafy.user.admin.service;

public interface MailService {
	// HTML String 생성(메일 내용)
	String makeHtml();
	
	// 메일 전송
	String sendMail(String email);

}
