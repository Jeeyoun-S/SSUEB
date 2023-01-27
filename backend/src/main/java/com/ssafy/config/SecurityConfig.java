package com.ssafy.config;

import com.ssafy.common.jwt.JwtAccessDeniedHandler;
import com.ssafy.common.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.common.jwt.JwtSecurityConfig;
import com.ssafy.common.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// for. JWT
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	public SecurityConfig(
			JwtTokenProvider jwtTokenProvider,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			JwtAccessDeniedHandler jwtAccessDeniedHandler) 
	{
		this.jwtTokenProvider = jwtTokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint; 
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler; 
	}
	// -----
    
	
    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                // token을 사용하는 방식이기 때문에 csrf 비활성화
                .csrf().disable()
                
                // JWT token 관련 exceptionHandling을 위한 code
                .exceptionHandling()
        		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        		.accessDeniedHandler(jwtAccessDeniedHandler)
        		
        		// session을 사용하지 않기 때문에 STATELESS로 설정
        		.and()
        		.sessionManagement()
        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        		
                .and()
                // 기존 있던 config 코드 
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
//                .authorizeRequests()
//                .antMatchers("/api/v1/users/me").authenticated()       //인증이 필요한 URL과 필요하지 않은 URL에 대하여 설정
//    	        	    .anyRequest().permitAll()
//                .and().cors();
                
                // 인증이 필요한 URL과 필요하지 않은 URL에 대하여 설정
        		.authorizeRequests()
        		// 일단 모두 all로 체크
        		.antMatchers("/**").permitAll()
        		.antMatchers("/api/auth/authenticate").permitAll()		
//        		.antMatchers("/api/user/join").permitAll()				// /api/user/join URL인 경우 모든 요청 허용
        		.anyRequest().authenticated()							// 그 외의 요청은 모두 인증 필요
                
        		.and()
        		.apply(new JwtSecurityConfig(jwtTokenProvider));
    }
    
}