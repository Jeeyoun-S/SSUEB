package com.ssafy.user.login.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  user 카카오 사용자 정보 API ([POST] /api/user/kakao) 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("UserKakaoUserPostRequest")
public class UserKakaoUserPostRequest {

	@ApiModelProperty(name = "유저 ID(kakao email)", example="ssafy@naver.com")
	String id; 
	
	@ApiModelProperty(name = "유저 Nickname(kakao nickname)", example = "김싸피")
	String nickname; 
	
}
