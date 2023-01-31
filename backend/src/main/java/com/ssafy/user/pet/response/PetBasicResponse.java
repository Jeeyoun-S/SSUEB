package com.ssafy.user.pet.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetBasicResponse {
	
	@ApiModelProperty(value = "성공 또는 실패")
	String response;
	
	@ApiModelProperty(value = "응답 메세지")
	String message;
}
