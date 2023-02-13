package com.ssafy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.classroom.interceptor.RoomValidInterceptor;

@Configuration
public class RoomValidConfig implements WebMvcConfigurer{
	
	//
	@Bean
	RoomValidInterceptor RoomValidInterceptor() {
		return new RoomValidInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(RoomValidInterceptor()).addPathPatterns("/api/room/*");
	}
	
	
	
	
	
}
