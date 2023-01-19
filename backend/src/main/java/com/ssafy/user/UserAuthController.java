package com.ssafy.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.jwt.JwtAuthenticationFilter;
import com.ssafy.common.jwt.JwtTokenProvider;
import com.ssafy.user.login.request.UserLoginPostRequest;
import com.ssafy.user.login.response.UserToken;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	private UserCache userCache = new NullUserCache();
	
	public UserAuthController(
			JwtTokenProvider jwtTokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.jwtTokenProvider = jwtTokenProvider; 
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
	
	/** 
	 * id와 pw를 통해 로그인 실행, 성공 시 token 반환
	 * @param UserLoginPostReq
	 * @return UserToken
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<UserToken> authorize(@RequestBody UserLoginPostRequest loginInfo) {
		// 비밀번호 암호화 진행 -> !! 이렇게 해도 안됨 passwordEncoder.match?
		// !!! 토큰에 비밀번호 넣으면 안됨, passwordEncoder.match 써서 검증해야함
		// !!! usercontroller로 id, pw를 검증하고 그 다음에 jwt 토큰 발급?
		// 토큰 발급 시 비밀번호 확인은 어디서 하는 걸까 
		// 
		try {
			logger.info("#21# 로그인 인증 실행 : {}, {}", loginInfo.getId(), loginInfo.getPassword());
			
			// i) 입력받은 loginInfo(id, pw)를 사용하여 Authentication 토큰 생성 
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(loginInfo.getId(), loginInfo.getPassword());
			logger.info("#21# i) 토큰생성: {}", authenticationToken);

			
			// ii) i에서 만든 authenticationToken을 사용하여 Authentication 객체를 생성하기 위하여 authenticate 메소드가 실행될 때
			//     CustomUserDetailsService 에 loadUserByUsername 메소드가 실행됨
			logger.info("#21# getObject 확인: {}", authenticationManagerBuilder.getObject());
			Authentication authentication = 
					authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			logger.info("#21# ii) authentication 객체 생성: {}", authentication);
			
			logger.info("#21# 비밀번호 암호황: {}", passwordEncoder.encode("ssafy01#FFY"));
//			logger.info("#21# 비밀번호 절레: {}", passwordEncoder.matches("ssafy01#FFY", encodedPassword));
			
			// iii) ii에 생성한 Authentication 객체를 사용하여 
			//      - SecurityContext에 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			//      - token 생성
			String token = jwtTokenProvider.createToken(authentication);
			logger.info("#21# iii) token 생성: {}", token);
			
			// iv) token을 Response Header에 넣고
			HttpHeaders httpHeaders = new HttpHeaders(); 
			httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + token);
			
			// v) Response Body에도 넣어서 return 한다.
			return new ResponseEntity<>(new UserToken(token), httpHeaders, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new UserToken(""), null, HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
