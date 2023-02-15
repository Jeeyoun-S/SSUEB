package com.ssafy.user.admin;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.common.util.BasicResponse;
import com.ssafy.common.util.CommonVariable;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.User;
import com.ssafy.user.admin.model.UserConsultantDto;
import com.ssafy.user.admin.response.UserAlertEmailResponse;
import com.ssafy.user.admin.response.UserAllListResponse;
import com.ssafy.user.admin.response.UserUncertifiedConsultantResponse;
import com.ssafy.user.admin.service.MailService;
import com.ssafy.user.admin.service.UserAdminService;
import com.ssafy.user.join.repository.JoinUserRepository;
import com.ssafy.user.logout.response.UserLogoutPostResponse;
import com.ssafy.user.phone.UserPhoneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	
	@Autowired
	UserPhoneService userPhoneService;
	
	@Autowired
	JoinUserRepository joinUserRepository;
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
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
	 * 전문가 자격증 검증 합격 알림 문자 발송
	 * @param String phone
	 * @return UserAlertEmailResponse
	 */
	@GetMapping("/alert/message/{id}")
	@ApiOperation(value = "자격증 검증 합격 문자 발송")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = BasicResponse.class, message = "자격증 검증 합격 이메일 발송 성공"),
			@ApiResponse(code = 401, response = BasicResponse.class, message = "자격증 검증 합격 이메일 발송 실패")
	})
	public ResponseEntity<BasicResponse> sendCertifiedPassMessage(@PathVariable("phone") String phone) {
		
		try {
			return userPhoneService.sendSMS(phone, "[SSUEB 반려동물 상담 서비스]\n전문가 인증 완료! 상담하러 가기 https://i8a801.p.ssafy.io/");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<BasicResponse>(new BasicResponse("failure", "인증번호를 전송에 실패했습니다. 전화번호를 다시 확인해 주세요."), HttpStatus.OK);
	}
	
	/** 
	 * 탈퇴하지 않은 모든 사용자 목록 조회
	 * @return UserAlertEmailResponse
	 */
	@GetMapping("/list")
	@ApiOperation(value = "탈퇴하지 않은 모든 사용자 목록 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserAllListResponse.class, message = "탈퇴하지 않은 모든 사용자 목록 조회 성공"),
	})
	public ResponseEntity<UserAllListResponse> getAllUserList() {
		List<User> userList = joinUserRepository.findByUserDeleteFlag(0);
		System.out.println(userList);
//		UserAllListResponse returnDto = new UserAllListResponse("success", "탈퇴하지 않은 사용자 전체 조회 성공", userList);
		return new ResponseEntity<UserAllListResponse>(new UserAllListResponse("success", "탈퇴하지 않은 사용자 전체 조회 성공", userList), HttpStatus.OK);
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
	
	@PostMapping("/alert")
	@ApiOperation(value = "사용자 알람 전송", notes = "사용자에게 알람을 보낸다.")
	@ApiResponse(code = 200, response = BasicResponse.class, message = "사용자 알람 전송 성공")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true),
		@ApiImplicitParam(name = "message", value = "알람 메세지 내용", required = true)
	})
	public ResponseEntity<BasicResponse> alertUser(String id, String message) {
		System.out.println(id+message);
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
			
			Optional<User> optionalUser = joinUserRepository.findById(id);
			
			if (optionalUser.isPresent()) {
				
				User user = optionalUser.get();
				int alertFlag = user.getUserAlertFlag();
				
				String result = "failure";
				if (alertFlag == 0) {
					// 카카오
					
				} else if (alertFlag == 1) {
					// 이메일
					result = mailService.customMail(id, message);
				} else {
					// 문자
					String phone = user.getUserPhone();
					try {
						result = userPhoneService.sendSMS(phone, "[SSUEB] "+message).getBody().getResponse();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				
				if (result == "success") {
					return ResponseEntity.status(200).body(new BasicResponse("success", null));
				}
			}
		}
		
		return ResponseEntity.status(200).body(new BasicResponse("failure", null));
	}
}
