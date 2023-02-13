package com.ssafy.user.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.util.CommonVariable;
import com.ssafy.db.entity.Consultant;
import com.ssafy.user.admin.model.UserConsultant;
import com.ssafy.user.admin.model.UserConsultantDto;
import com.ssafy.user.admin.response.UserAlertEmailResponse;
import com.ssafy.user.admin.response.UserUncertifiedConsultantResponse;
import com.ssafy.user.admin.service.MailService;
import com.ssafy.user.admin.service.UserAdminService;
import com.ssafy.user.login.response.UserLoginPostResponse;
import com.ssafy.user.logout.response.UserLogoutPostResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"User/Admin"}, description = "관리자 관련  API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/user/admin")
public class UserAdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);
	
	@Autowired
	CommonVariable commonVariable;
	
	// for. email 전송
	@Autowired
	MailService mailService;
	
	@Autowired
	UserAdminService userAdminService;
	
	/** 
	 * 전문가 자격증 검증 합격 알림 이메일 발송
	 * @param String id
	 * @return UserAlertEmailResponse
	 */
	@GetMapping("/alert/email/{id}")
	@ApiOperation(value = "자격증 검증 합격 이메일 발송")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserLogoutPostResponse.class, message = "자격증 검증 합격 이메일 발송 성공"),
			@ApiResponse(code = 401, response = UserLogoutPostResponse.class, message = "자격증 검증 합격 이메일 발송 실패")
	})
	public ResponseEntity<UserAlertEmailResponse> sendCertifiedPassEmail(@PathVariable("id") String id) {
		
		try {
			logger.info("## [Controller]: sendCertifiedPassEmail - 자격증 검증 합격 이메일 발송 실행  / id: {}", id);
			
			// # 자격증 합격 이메일 발송 
			String sendMainResult = mailService.sendMail(id); 
			if (sendMainResult.equals("success")) {
				return ResponseEntity.ok(UserAlertEmailResponse.of(200, "자격증 검증 합격 이메일 발송 성공", "success"));
			}
			else {
				return ResponseEntity.ok(UserAlertEmailResponse.of(200, "자격증 검증 합격 이메일 발송 실패", "failure"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(UserAlertEmailResponse.of(401, "자격증 검증 합격 이메일 발송 실패", "failure"));
		}
	}
	
	/** 
	 * 전문가 회원 중 자격증 검증이 되지 않은 회원 조회 
	 * @param 
	 * @return 
	 */
	@GetMapping("/consultant/list")
	@ApiOperation(value = "전문가 회원 중 자격증 검증이 되지 않은 회원 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserUncertifiedConsultantResponse.class, message = "자격증 미인증 전문가 조회 성공"),
			@ApiResponse(code = 401, response = UserUncertifiedConsultantResponse.class, message = "자격증 미인증 전문가 조회 실패")
	})
	public ResponseEntity<UserUncertifiedConsultantResponse> getUncertifiedConsultantList() {
		
		try {
			logger.info("## [Controller]: getUncertifiedConsultantList - 자격증 미인증 전문가 조회 실행");
			
			// # 자격증 미인증 전문가 List 조회
			List<UserConsultantDto> consultants = userAdminService.getUncertifiedConsultantList(); 
//			logger.info("#21# 자격증 미인증 전문가 list: {}", consultant);
			
			return ResponseEntity.ok(UserUncertifiedConsultantResponse.of(200, "success", "자격증 미인증 전문가 조회 성공", consultants));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(UserUncertifiedConsultantResponse.of(401, "failure", "자격증 미인증 전문가 조회 실패", null));
		}
	}
}
