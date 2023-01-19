package com.ssafy.user.join.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse {
	
	@ApiModelProperty(value = "성공 또는 실패")
	String response;
}
