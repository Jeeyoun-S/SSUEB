package com.ssafy.user.login;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.jwt.JwtAuthenticationFilter;
import com.ssafy.common.jwt.JwtTokenProvider;
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
	
	// for. JWT
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	public UserLoginController(
			JwtTokenProvider jwtTokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.jwtTokenProvider = jwtTokenProvider; 
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
	
	
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
//			logger.info("#21# 암호화 비밀번호: {}", passwordEncoder.encode(loginInfo.getPassword()));
			
			// i) 입력받은 loginInfo(id, pw)를 사용하여 Authentication 토큰 생성 
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(loginInfo.getId(), loginInfo.getPassword());
			logger.info("#21# i) 토큰생성: {}", authenticationToken);

			
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
	
}
