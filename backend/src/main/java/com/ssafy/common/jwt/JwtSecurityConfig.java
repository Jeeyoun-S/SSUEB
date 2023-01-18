package com.ssafy.common.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	private JwtTokenProvider jwtTokenProvider;
	
	public JwtSecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider; 
	}
	
	/**
	 * JwtAuthenticationFilter를 Security 로직에 필터로 등록
	 * @param HttpSecurity
	 * @return void
	 */
	@Override
	public void configure(HttpSecurity http) {
		http.addFilterBefore(
			new JwtAuthenticationFilter(jwtTokenProvider), 
			UsernamePasswordAuthenticationFilter.class
		);
	}
}
