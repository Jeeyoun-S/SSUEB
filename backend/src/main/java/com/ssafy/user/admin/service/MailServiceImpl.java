package com.ssafy.user.admin.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	// ! 필요 사전작업 
	//   i) dependency 추가 
	//   ii) application.yml 설정 추가 (보낼 이메일 setting)
	//   iii) SMTP 서버 사용
	
	@Autowired
	JavaMailSender javaMailSender;
	
	/** 
	 * HTML 메일 내용 생성
	 * @param  
	 * @return 
	 */
	@Override
	public String makeHtml() {
		String html = "<h2> SSUEB 자격증 검증 결과 </h2> <br> 회원님의 자격증 검증이 확인되었습니다. 감사합니다! <br>"; 
		
		return html;
	}

	/** 
	 * 메일 전송
	 * @param  email 전송할 메일 내용 
	 * @return 
	 */
	@Override
	public String sendMail(String email) {
		logger.info("#MailServiceImpl - sendMail# 메일 전송 동작");
		
		// i) 메일 생성
		String html = makeHtml();
		String subject = "SSEUB 자격증 검증 결과";
		
		// ii) 메일 전송 
		MimeMessage mail = javaMailSender.createMimeMessage(); 
		try {
			mail.setSubject(subject, "utf-8");
			mail.setText(html, "utf-8", "html");
			mail.addRecipient(RecipientType.TO, new InternetAddress(email));
			logger.info("#MailServiceImpl - sendMail# 메일 전송 확인 - {}", mail);
			javaMailSender.send(mail);
			return "success";
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return "failure";
		}
	}

}
