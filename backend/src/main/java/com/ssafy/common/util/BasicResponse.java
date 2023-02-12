package com.ssafy.common.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
	
	@ApiModelProperty(value = "성공 또는 실패")
	String response;
	
	@ApiModelProperty(value = "메세지")
	String message;
}
