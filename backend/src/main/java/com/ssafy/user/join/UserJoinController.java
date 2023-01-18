package com.ssafy.user.join;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;
import com.ssafy.user.join.response.DuplicateId;
import com.ssafy.user.join.response.JoinResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/user/join")
@Api(tags = {"User Join"}, description = "사용자 회원가입 API")
public class UserJoinController {
	
	@Autowired
	UserJoinService userJoinService;
	
	@PostMapping("/partner")
	@ApiOperation(value = "반려인 회원가입", notes = "반려인의 정보를 입력받아 회원 정보에 추가하고 로그인한다.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, response = JoinResponse.class, message = "회원가입 성공")
	})
	public ResponseEntity<JoinResponse> joinPartner(JoinRequest joinRequest) {
		return userJoinService.joinPartner(joinRequest);
	}
	
	@PostMapping(value = "/consultant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "전문가 회원가입", notes = "전문가의 정보를 입력받아 회원 정보에 추가하고 로그인한다.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, response = JoinResponse.class, message = "회원가입 성공")
	})
	public ResponseEntity<JoinResponse> joinConsultant(JoinRequest joinRequest, ConsultantJoinRequest consultantJoinRequest) {
		return userJoinService.joinConsultant(joinRequest, consultantJoinRequest);
	}
	
	@GetMapping("/duplication/id")
	@ApiOperation(value = "사용자 아이디 중복 확인", notes = "사용자가 회원가입하기 전에 아이디 중복을 확인한다.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, response = DuplicateId.class, message = "회원가입 성공")
	})
	@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true)
	public ResponseEntity<DuplicateId> duplicateId(String id) {
		return userJoinService.duplicateId(id);
	}
	
	@GetMapping("/phone/auth")
	@ApiOperation(value = "휴대폰 인증 전송", notes = "사용자의 휴대폰 인증을 위해 인증 메세지를 전송한다.")
	@ApiImplicitParam(name = "userPhone", value = "휴대폰 번호", required = true)
	public ResponseEntity<?> phoneAuth(String userPhone) {
		return userJoinService.phoneAuth(userPhone);
	}
	
	@GetMapping("/phone/confirm")
	@ApiOperation(value = "휴대폰 인증 확인", notes = "사용자가 입력한 인증 번호가 옳은지 확인한다.")
	@ApiImplicitParam(name = "authNumber", value = "인증 번호", required = true)
	public ResponseEntity<?> phoneConfirm(String authNumber) {
		return userJoinService.phoneConfirm(authNumber);
	}
	
	@GetMapping("/valid/test")
	@ApiOperation(value = "유효성 검사 테스트", notes = "유효성 검사 메소드를 테스트한다.")
	@ApiImplicitParam(name = "value", value = "유효성 검사를 할 문자열", required = true)
	public Map<String, Boolean> checkValid(String value) {
		
		Map<String, Boolean> returnMap = new HashMap<>();
		ParameterCheck parameterCheck = new ParameterCheck();
		
		returnMap.put("isEmpty", parameterCheck.isEmpty(value));
		returnMap.put("isValidId", parameterCheck.isValidId(value));
		returnMap.put("isValidName", parameterCheck.isValidName(value));
		returnMap.put("isValidNickname", parameterCheck.isValidNickname(value));
		returnMap.put("isValidPassword", parameterCheck.isValidPassword(value));
		returnMap.put("isValidPhone", parameterCheck.isValidPhone(value));
		returnMap.put("isSpacing", parameterCheck.isSpacing(value));
		returnMap.put("isSpecialChar", parameterCheck.isSpecialChar(value));
		returnMap.put("isValidPetType", parameterCheck.isValidPetType(value));
		
		return returnMap;
	}
}
