package com.ssafy.user.pet.response;

import com.ssafy.db.entity.Pet;

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
	
	@ApiModelProperty(value = "전달 데이터")
	Pet data;
}
