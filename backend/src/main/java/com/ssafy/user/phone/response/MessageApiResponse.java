package com.ssafy.user.phone.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageApiResponse {
	
	@ApiModelProperty(value = "요청 아이디")
	String requestId;
	
	@ApiModelProperty(value = "요청 시간")
	String requestTime;
	
	@ApiModelProperty(value = "요청 상태 코드 (202 성공, 그 외)")
	String statusCode;
	
	@ApiModelProperty(value = "요청 상태명 (success: 성공, fail: 실패)")
	String statusName;
}
