package com.ssafy.reservation.basic.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * [skeleton-code] 
 * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("ReservationDignosis")
public class ReservationDignosis {
	
	@ApiModelProperty(value = "해당 상담테이블 번호", dataType = "int", example="1")
	int no;
	
	@ApiModelProperty(value = "전문가가 작성한 진단서", example="까비는 아주 건강합니다")
	String reservationDignosisRecord;
}
