package com.ssafy.user.login.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  user 로그인 API ([POST] /api/user/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("UserLoginPostRequest")
public class UserLoginPostRequest {
	
	@ApiModelProperty(name="유저 ID", example="ssafy@gmail.com")
	String id;
	
	@ApiModelProperty(name="유저 Password", example="ssafy01#FFY")
	String password;
}
