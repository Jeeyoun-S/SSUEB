package com.ssafy.user.info.consultant.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Star {
	
	@ApiModelProperty(value = "5개 중 별점 개수")
	int starNum;
	
	@ApiModelProperty(value = "총 개수")
	int starCnt;
}
