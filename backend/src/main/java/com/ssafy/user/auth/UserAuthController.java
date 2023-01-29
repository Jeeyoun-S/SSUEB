package com.ssafy.user.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.user.auth.response.UserAuthResponse;
import com.ssafy.user.auth.service.UserAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"User Auth Controller"}, description = "권한검증 API")
@RestController
@RequestMapping("/api/user/auth")
public class UserAuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
	
	@Autowired
	UserAuthService userAuthService;

	/** 
	 * 모든 권한 허용
	 * @param HttpServletRequest
	 * @return UserAuthResponse
	 */
	@GetMapping("/permit")
	@ApiOperation(value = "권한 검증 - 모든 권한 허용")
	@PreAuthorize("hasAnyRole('USER', 'CONSULTANT', 'ADMIN')")
	public ResponseEntity<UserAuthResponse> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(userAuthService.getMyUserWithAuthorities());
	}
	
	/** 
	 * 전문가, 관리자 권한만 허용
	 * @param userId
	 * @return UserAuthResponse
	 */
	@GetMapping("/permit/{id}")
	@ApiOperation(value = "권한 검증 - 전문가, 관리자 권한만 허용")
	@PreAuthorize("hasAnyRole('CONSULTANT', 'ADMIN')")
	public ResponseEntity<UserAuthResponse> getUserInfo(@PathVariable String id) {
		return ResponseEntity.ok(userAuthService.getUserWithAuthorities(id));
	}
	
	
}
