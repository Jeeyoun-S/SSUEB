package com.ssafy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.openvidu.java.client.OpenVidu;

@Configuration
public class openviduConfig {
	@Value("${OPENVIDU_URL}")
	private String OPENVIDU_URL;

	@Value("${OPENVIDU_SECRET}")
	private String OPENVIDU_SECRET;
	
	@Bean
	public OpenVidu openvidu() {
		System.out.println(OPENVIDU_URL + "  " + OPENVIDU_SECRET);
		return new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
	}
}
