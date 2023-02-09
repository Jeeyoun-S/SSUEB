package com.ssafy.user.login.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  user 로그인 API ([POST] /api/user/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("UserLoginPostRequest")
public class UserLoginPostRequest {
	
	@ApiModelProperty(name="유저 ID", example="ssafy@gmail.com")
	String id;
	
	@ApiModelProperty(name="유저 Password", example="ssafy01#FFY")
	String password;
	
	@ApiModelProperty(name="소셜 로그인 버튼을 통한 접근여부", example="소셜 로그인 버튼 접근 X(0) / 소셜 로그인 버튼 접근 O(1)")
	int socialButton;
}
