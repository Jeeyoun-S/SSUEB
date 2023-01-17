package com.ssafy.user.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user/join")
@Api(tags = {"User Join Controller"}, description = "사용자 회원가입")
public class UserJoinController {
	
	@Autowired
	UserJoinService userJoinService;
	
	@PostMapping("/partner")
	@ApiOperation(value = "반려인 회원가입")
	public ResponseEntity<?> joinPartner(JoinRequest joinRequest) {
		return userJoinService.joinPartner(joinRequest);
	}
	
	@PostMapping(value = "/consultant")
	@ApiOperation(value = "전문가 회원가입")
	public ResponseEntity<?> joinConsultant(JoinRequest joinRequest, ConsultantJoinRequest consultantJoinRequest) {
		return userJoinService.joinConsultant(joinRequest, consultantJoinRequest);
	}
	
	@GetMapping("/duplication/id")
	@ApiOperation(value = "사용자 아이디 중복 확인")
	@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true)
	public ResponseEntity<?> duplicateId(String id) {
		return userJoinService.duplicateId(id);
	}
	
	@GetMapping("/phone/auth")
	@ApiOperation(value = "휴대폰 인증 전송")
	public ResponseEntity<?> phoneAuth() {
		return userJoinService.phoneAuth();
	}
	
	@GetMapping("/phone/confirm")
	@ApiOperation(value = "휴대폰 인증 확인")
	public ResponseEntity<?> phoneConfirm() {
		return userJoinService.phoneConfirm();
	}
	
}
