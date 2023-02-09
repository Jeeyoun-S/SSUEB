package com.ssafy.user.info.consultant.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Graph {
	
	@ApiModelProperty(value = "예약 년도와 월 YY-MM")
	String reservationDate;
	
	@ApiModelProperty(value = "예약 확정 횟수")
	int total;
	
	@ApiModelProperty(value = "별점 평균")
	double grade;
}
