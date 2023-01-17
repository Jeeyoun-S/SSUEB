package com.ssafy.user.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.user.login.request.UserLoginPostRequest;
import com.ssafy.user.login.response.UserLoginPostResponse;
import com.ssafy.user.login.service.UserLoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * user 로그인 요청 처리를 위한 컨트롤러 정의
 */
@Api(value = "user_로그인 API", tags = {"User"})
@RestController
@RequestMapping("/api/user/login")
public class UserLoginController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	@Autowired
	UserLoginService userLoginService; 
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// # 반려인 로그인
	@PostMapping("/partner")
	@ApiOperation(value = "반려인 로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({
      @ApiResponse(code = 200, message = "성공", response = UserLoginPostRequest.class),
      @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
      @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
      @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostResponse> loginPartner(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(ID, PW)", required = true) UserLoginPostRequest loginInfo) {
		logger.info("## [Controller]: loginPartner - 반려인 로그인 실행 {}", loginInfo.toString());
		
		// id에 해당되는 회원정보 가져오기
		User user = userLoginService.getUserByUserId(loginInfo.getId());
		
		// 가져온 회원정보와 입력된 pw 일치 확인 
		if (passwordEncoder.matches(loginInfo.getPassword(), user.getUserPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공 (액세스 토큰을 포함하여 응답값 전달)
			String accessToken = JwtTokenUtil.getToken(loginInfo.getId());
			return ResponseEntity.ok(UserLoginPostResponse.of(200, "success", "로그인에 성공했습니다.", JwtTokenUtil.getToken(loginInfo.getId())));
		}
	
		return ResponseEntity.ok(UserLoginPostResponse.of(401, "failure", "id 또는 password를 다시 입력해 주세요.", null));
	}
}
