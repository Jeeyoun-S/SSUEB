package com.ssafy.user.info.partner.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoPartnerRequest {
	
	@ApiModelProperty(value = "사용자 아이디", required = true)
	String id;
	
	@ApiModelProperty(value = "사용자 비밀번호")
	String userPassword;
	
	@ApiModelProperty(value = "사용자 이름")
	String userName;
	
	@ApiModelProperty(value = "사용자 닉네임")
	String userNickname;
	
	@ApiModelProperty(value = "사용자 휴대폰 번호")
	String userPhone;
	
	@ApiModelProperty(value = "사용자 알람 수신 방법", dataType = "int", example = "0")
	int userAlertFlag;
}
