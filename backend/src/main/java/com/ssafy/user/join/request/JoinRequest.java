package com.ssafy.user.join.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JoinRequest {
	
	@ApiModelProperty(value = "이메일", required = true)
	String id;
	
	@ApiModelProperty(value = "비밀번호", required = true)
	String userPassword;
	
	@ApiModelProperty(value = "닉네임")
	String userNickname;
	
	@ApiModelProperty(value = "이름", required = true)
	String userName;
	
	@ApiModelProperty(value = "휴대폰 번호", required = true)
	String userPhone;
	
	@ApiModelProperty(value = "알람방식: 카카오(0), 이메일(1), 문자(2)", required = true, example = "0")
	int userAlertFlag;

	@ApiModelProperty(value = "소셜 로그인 ID: 소셜 ID 아님(0), 소셜 ID 맞음(1)", required = true, example = "0")
	int userIsSocialId;
}
