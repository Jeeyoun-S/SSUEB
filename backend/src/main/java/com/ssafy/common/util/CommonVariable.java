package com.ssafy.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

// yml 파일 내 필요한 변수를 가져오는 class 정의
@Component
@Getter
public class CommonVariable {
	
	private String kakaoSecret; 
	
	public CommonVariable(@Value("${kakao.secret}") String kakaoSecret) {
		this.kakaoSecret = kakaoSecret;
	}

}
