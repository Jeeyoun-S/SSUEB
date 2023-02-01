package com.ssafy.reservation.matching.request;

import javax.persistence.Column;

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
public class MatchingConfirm {
	@ApiModelProperty(value = "매칭이 확정될 상담 테이블 번호")
	int reservationNo;
	@ApiModelProperty(value = "해당 상담을 수행할 전문가 아이디", example="kk@k")
    String consultantId;
	@ApiModelProperty(value = "예약금액", example="22222")
    int matchingCost;
}

