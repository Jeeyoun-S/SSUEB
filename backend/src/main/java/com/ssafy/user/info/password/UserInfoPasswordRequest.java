package com.ssafy.user.info.password;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoPasswordRequest {
	
	@ApiModelProperty(value = "사용자 아이디", required = true)
	String id;
	
	@ApiModelProperty(value = "사용자 비밀번호", required = true)
	String password;
}
