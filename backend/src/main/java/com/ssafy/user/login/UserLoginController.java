package com.ssafy.user.login;

import java.io.Console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.jwt.JwtAuthenticationFilter;
import com.ssafy.common.jwt.JwtTokenProvider;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.User;
import com.ssafy.user.join.UserJoinController;
import com.ssafy.user.join.UserJoinRepository;
import com.ssafy.user.join.response.BasicResponse;
import com.ssafy.user.login.request.UserKakaoUserPostRequest;
import com.ssafy.user.login.request.UserLoginPostRequest;
import com.ssafy.user.login.response.UserLoginPostResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"User Login and Auth"}, description = "로그인 및 Token 발급  API")
@RestController
@RequestMapping("/api/user/login")
public class UserLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// for. 입력값 검증 
	ParameterCheck parameterCheck = new ParameterCheck(); 
	
	// for. JWT
	@Autowired
	JwtTokenProvider jwtTokenProvider; 
	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;
//	private final JwtTokenProvider jwtTokenProvider;
//	private final AuthenticationManagerBuilder authenticationManagerBuilder;
//	public UserLoginController(
//			JwtTokenProvider jwtTokenProvider,
//			AuthenticationManagerBuilder authenticationManagerBuilder) {
//		this.jwtTokenProvider = jwtTokenProvider; 
//		this.authenticationManagerBuilder = authenticationManagerBuilder;
//	}
	
	@Autowired
	UserJoinController userJoinController;
	
	/** 
	 * id와 pw를 통해 로그인 실행, 성공 시 JWT token 반환
	 * @param UserLoginPostReq
	 * @return UserToken
	 */
	@PostMapping("/")
	@ApiOperation(value = "로그인 - JWT 토큰 발급")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "유저 email", required = true),
		@ApiImplicitParam(name = "password", value = "유저 password", required = true)
	})
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserLoginPostResponse.class, message = "로그인에 성공했습니다."),
			@ApiResponse(code = 401, response = UserLoginPostResponse.class, message = "id 또는 password를 다시 입력해 주세요.")
	})
	public ResponseEntity<UserLoginPostResponse> authorize(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(email(ID), PW)", required = true) UserLoginPostRequest loginInfo) {
		
		try {
			logger.info("## [Controller]: authorize - 로그인 실행 {}, {}", loginInfo.getId(), loginInfo.getPassword());
			logger.info("#21# 암호화 비밀번호: {}", passwordEncoder.encode(loginInfo.getPassword()));
			
			// # 입력값 검증
			// i) id - 비어 있지 않은지 && ID 규칙에 맞는지
			if(parameterCheck.isEmpty(loginInfo.getId()) || !parameterCheck.isValidId(loginInfo.getId())) {
				return ResponseEntity.ok(UserLoginPostResponse.of(401, "failure", "id 또는 password를 다시 입력해 주세요.", null));
			}
			// ii) pw - 비어 있지 않은지 && PW 규칙에 맞는지 
			if (parameterCheck.isEmpty(loginInfo.getPassword()) || !parameterCheck.isValidPassword(loginInfo.getPassword())) {
				return ResponseEntity.ok(UserLoginPostResponse.of(401, "failure", "id 또는 password를 다시 입력해 주세요.", null));
			}
			
			// # 로그인
			// i) 입력받은 loginInfo(id, pw)를 사용하여 Authentication 토큰 생성 
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(loginInfo.getId(), loginInfo.getPassword());
//			logger.info("#21# i) 토큰생성: {}", authenticationToken);

			
			// ii) i에서 만든 authenticationToken을 사용하여 Authentication 객체를 생성하기 위하여 authenticate 메소드가 실행될 때
			//     CustomUserDetailsService 에 loadUserByUsername 메소드가 실행됨
			Authentication authentication = 
					authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//			logger.info("#21# ii) authentication 객체 생성: {}", authentication);
			
			// iii) ii에 생성한 Authentication 객체를 사용하여 
			//      - SecurityContext에 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			//      - token 생성
			String token = jwtTokenProvider.createToken(authentication);
//			logger.info("#21# iii) token 생성: {}", token);
			
			// iv) token을 Response Header에 넣고
			HttpHeaders httpHeaders = new HttpHeaders(); 
			httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + token);
			
			// v) Response Body에도 넣어서 return 한다.
//			return new ResponseEntity<>(new UserToken(token), httpHeaders, HttpStatus.OK);
			return ResponseEntity.ok(UserLoginPostResponse.of(200, "success", "로그인에 성공했습니다.", token));
		
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(UserLoginPostResponse.of(401, "failure", "id 또는 password를 다시 입력해 주세요.", null));
		}
	}
	
	/** 
	 * OAuth2_Kakao 소셜 로그인
	 * @param String
	 * @return UserLoginPostResponse
	 */
	@PostMapping("/kakao")
	@ApiOperation(value = "소셜 로그인 - OAuth2 kakao")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "현재 로그인한 Kakao 사용자 정보", required = true)
	})
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserLoginPostResponse.class, message = "로그인에 성공했습니다."),
			@ApiResponse(code = 401, response = UserLoginPostResponse.class, message = "id 또는 password를 다시 입력해 주세요.")
	})
//	public ResponseEntity<UserLoginPostResponse> kakao(@RequestBody @ApiParam(value = "Kakao 사용자 정보", required = true) UserKakaoUserPostRequest info) {
	public String kakao(@RequestBody @ApiParam(value = "Kakao 사용자 정보", required = true) UserKakaoUserPostRequest info) {
		logger.info("#[Controller]: kakao# 현재 로그인한 Kakao 사용자 정보: {}", info);
		
		// i) token 복호화 -> 카카오로부터 전달받은 사용자 정보 얻기
		// ii) 얻은 id가 이미 회원가입 되어 있는지 확인
		// iii) 카카오로부터 얻은 정보(id, nickname) return
		// ------ 여기까지 Front에서 처리
		
		// * 만약 이 아이디로 회원가입한 사용자가 없다면
		ResponseEntity<BasicResponse> duplicateIdResult = userJoinController.duplicateId(info.getId());
		logger.info("#21# 반환 확인: {}", duplicateIdResult.getBody().getResponse());
		
		if (duplicateIdResult.getBody().getResponse().equals("success")) {
			return "redirect:http://localhost:8081/join";
		}
		// * 기존 사용자 정보가 있다면 user id, nickname 카카오 계정으로 변경..? 
		else {
			return "redirect:http://localhost:8081/";
		}
		// iv) 회원가입 페이지로 front 연결 
		
		// * 있다면..? update? 
		
		// v) 회원가입 페이지에서 추가 정보 받은 후 
		// vi) 회원가입 진행 
		// vii) 로그인 진행
		
//		return ResponseEntity.ok(UserLoginPostResponse.of(200, "success", "로그인에 성공했습니다.", null));
	}
		
}
