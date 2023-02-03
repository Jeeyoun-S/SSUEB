package com.ssafy.user.info.consultant.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoResponse {

	@ApiModelProperty(value = "성공 또는 실패")
	String response;
	
	@ApiModelProperty(value = "응답 메세지")
	String message;
	
	@ApiModelProperty(value = "전문가 회원정보")
	UserInfoResponseData data;
	
}
