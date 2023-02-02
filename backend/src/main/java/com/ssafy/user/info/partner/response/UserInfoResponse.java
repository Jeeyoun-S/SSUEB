package com.ssafy.user.info.partner.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResponse {
	
	@ApiModelProperty(value = "사용자 아이디")
	String id;
	
	@ApiModelProperty(value = "사용자 이름")
	String userName;
	
	@ApiModelProperty(value = "사용자 닉네임")
	String userNickname;
	
	@ApiModelProperty(value = "사용자 휴대폰 번호")
	String userPhone;
	
	@ApiModelProperty(value = "사용자 알람 수신 방법")
	int userAlertFlag;
}
