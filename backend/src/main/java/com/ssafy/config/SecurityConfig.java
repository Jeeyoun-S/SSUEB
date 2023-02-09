package com.ssafy.config;

import com.ssafy.common.jwt.JwtAccessDeniedHandler;
import com.ssafy.common.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.common.jwt.JwtAuthenticationFilter;
import com.ssafy.common.jwt.JwtSecurityConfig;
import com.ssafy.common.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity													// Spring Security 설정 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)					// @PreAuthorize 어노테이션 사용을 위해 선언
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// for. JWT
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; 
	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    
    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 규칙 설정
     * - HttpSecurity 객체를 이용해 각 요청을 먼저 intercept하여 URL 별 인증 여부, login 처리, logout 처리 등 다양한 처리 가능
     * @param http
     * @throws Exception
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                // token을 사용하는 방식이기 때문에 csrf 비활성화
                .csrf().disable()
        		
        		// session을 사용하지 않기 때문에 STATELESS로 설정
        		.sessionManagement()
        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        		
        		 // 기존 있던 config 코드 
//              .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) // HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
        		
                
        		// # JWT 토큰
        		.and()
        		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()									// JWT token 관련 exceptionHandling을 위한 code
        		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        		.accessDeniedHandler(jwtAccessDeniedHandler)
        		
        		// # URL 별 인증 관리 - 인증이 필요한 URL과 필요하지 않은 URL에 대하여 설정
                .and()
                .authorizeRequests()						// URL 별 인증 관리
//        			.antMatchers("/api/user/login/**", "/api/user/join/**", "/api/user/auth/**").permitAll()	// 로그인, 회원가입, 권한검증 경로는 인증없이 호출 가능
        			.antMatchers("/**").permitAll()		// 모두 허용
        			//.antMatchers("/api/user/pet/**").hasAnyRole("USER")
        			.anyRequest().authenticated()			// 그 외의 요청은 모두 JWT 인증 필요
        		
        		.and()
				.apply(new JwtSecurityConfig(jwtTokenProvider));		// JWTSecurityConfig 적용
        		//.apply(new JwtSecurityConfig(jwtTokenProvider));
        		
        		// # OAuth 관련 설정
//        		.and()
//        		.oauth2Login()								// oauth2 기반의 로그인인 경우
//        		.userInfoEndpoint();						// 로그인 성공 후 사용자 정보 가져오기
//        		.userService(customOAuth2UserService);	    // 소셜로그인 성공 시 후속 조치를 진행할 UserSerivce 인터페이스 구현체 등록 - 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
        
        //super.configure(http);
    }
}