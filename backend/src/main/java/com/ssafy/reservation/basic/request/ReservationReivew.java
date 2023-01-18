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
@ApiModel("ReservationReivew")
public class ReservationReivew {
	//@ApiModelProperty(name="리뷰 평가", example="전문가 분이 친절해요")
	String reviewComment;
	//@ApiModelProperty(name="리뷰 점수", example="3")
	int reviewGrade;
	
	int reservationOpen;
}
